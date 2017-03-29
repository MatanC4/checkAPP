package bl.controlers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import bl.data.DBRecord;
import bl.data.DataListener;
import bl.data.FireBaseHandler;
import bl.data.SharedPreferencesHandler;
import bl.data.UserEvents;
import bl.entities.Amendment;
import bl.entities.AmendmentType;
import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;
import bl.entities.EventStatus;
import bl.entities.UserInfo;
import bl.notifications.BroadcastTags;
import bl.notifications.EventNotification;

/**
 * Created by Daniel_m on 14/03/2017.
 */

public class AppManager implements DataListener {

    private static final int MINIMUM_RATING = 3;
    private static AppManager singleton;
    private HashMap<Category, HashMap<Long, Event>> sortedEvents;
    private HashMap<CategoryName, String> categoriesAPIKeys;
    private HashMap<CategoryName, ArrayList<DBRecord>> suggestion;
    private HashMap<String, ImageView> images;
    private UserEvents userEvents;
    private UserInfo info;
    private FireBaseHandler fbHandler;

    public Event changeEventStatus(Context context, Event event, EventStatus status){
        Event modifiedEvent = sortedEvents.get(event.getCategory()).get(event.getId());
        if(event.getStatus()!=status){
            modifiedEvent.setStatus(status);
            if(status==EventStatus.DONE)
                writeToFireBase(context, event);
            saveData(context);
        }
        return modifiedEvent;
    }


    public ImageView getImageByKey(String key) throws Exception{
        if(!images.containsKey(key))
            throw new Exception("Invalid Key");
        return images.get(key);
    }
    
    public void temporarilyStoreImage(String key, ImageView image){
        images.put(key, image);
    }

    public void removeImageFromMap(String key){
        images.remove(key);
    }

    public ArrayList<Event> getEventsInCategory(CategoryName cName){
        HashMap<Long,Event> eventsMap = sortedEvents.get(new Category(cName,null));
        ArrayList<Event> events = new ArrayList<>();
        for(Event e : eventsMap.values()){
            events.add(e);
        }
        return events;
    }

    private void writeToFireBase(Context context, Event event){
        if(info==null) {
            info = getUserInformation(context);
        }
        if(!info.isAnonymous()) {
            fbHandler.writeToFireBase(event,info);
        }
    }

    public ArrayList<DBRecord> getSuggestionsByProfile(CategoryName categoryName, Context context) throws Exception{
        if(info==null)
            info = getUserInformation(context);
        if(info.isAnonymous())
            throw new Exception("Sorry! this service is not available to anonymous users");
        return suggestion.get(categoryName);
    }

    private void readFromFireBase(UserInfo info) {
        for(CategoryName cn : CategoryName.values()) {
            try {
                fbHandler.readFromFireBase(cn, info);
            }
            catch(Exception e){
                Log.v("FIRE_BASE","Reading failed, Category: " +cn.toString());
            }
        }
    }


    private AppManager(Context context){
        initMaps();
/*      userEvents = SharedPreferencesHandler.getData(context);
        FireBaseHandler fbHandler =  new FireBaseHandler(this);
        UserInfo info = getUserInformation(context);
        if(!info.isAnonymous())
            readFromFireBase(info);*/
        setCategories();
        setKeys();
        //getData(context);
    }

    public boolean isEventAlreadyExist(long id, CategoryName cName){
        return sortedEvents.get(new Category(cName, null)).containsKey(id);
    }

    private void initMaps(){
        sortedEvents = new HashMap<>();
        categoriesAPIKeys = new HashMap<>();
        suggestion = new HashMap<>();
        images = new HashMap<>();
    }

    public static AppManager getInstance(Context context){
        if(singleton==null){
            singleton = new AppManager(context);
        }
        return singleton;

    }

    public void removeEvent(Context context, Event event){
        final boolean remove = userEvents.getEvents().remove(event);
        if(remove){
            sortedEvents.get(event.getCategory()).remove(event.getId());
            saveData(context);
        }
    }

    public void saveLoggedUserInformation(Context context, String name, int age, String city, String country){
        SharedPreferencesHandler.saveUserInfo(context, new UserInfo(name, age, country, city));
    }

    public void saveAnonymousUserInformation(Context context, String country){
        SharedPreferencesHandler.saveUserInfo(context, new UserInfo(country));
    }

    public UserInfo getUserInformation(Context context){
        return SharedPreferencesHandler.getUserInfo(context);
    }

    public Bitmap getImageFromStorage(Event event){
        return BitmapFactory.decodeFile(event.getImageURL());
    }


    public Event addEvent(Context context, Event event, Bitmap image) {

        Calendar calendar = Calendar.getInstance();
        long eventID = event.getCategory().getName()==CategoryName.GENERAL?
                SharedPreferencesHandler.getNextGeneralID(context):event.getId();
        event.setCreationDate(calendar);
        event.setId(eventID);
        storeImage(event, image);
        saveAndStoreEvent(context, event);
        return event;
    }

    private void storeImage(Event event, Bitmap image){
        try {
            String root = Environment.getExternalStorageDirectory().toString();
            File myDir = new File(root + "/Check");
            if (!myDir.exists()) {
                myDir.mkdirs();
            }
            String name = event.getCategory().getName().toString() + event.getId();
            myDir = new File(myDir, name);
            FileOutputStream out = new FileOutputStream(myDir);
            image.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            event.setImageURL(myDir.getPath());
        } catch(Exception e){
            Log.d("IMAGE", "Saving the image failed");
        }

    }
    public Event addEventByDetails(Context context, long id, CategoryName categoryName, String name, String imageURL, String description, Calendar dueDate,
                                   AmendmentType amendmentType, String amendmentDescription, Bitmap image){
        Event event = new Event(id, name, imageURL, description, new Category(categoryName,categoriesAPIKeys.get(categoryName)),
                EventStatus.TODO, dueDate);
        Amendment amendment = new Amendment(amendmentType, amendmentDescription);
        event.setAmendment(amendment);
        return addEvent(context, event, image);
    }

    private void saveAndStoreEvent(Context context, Event event){
        userEvents.getEvents().add(event);
        sortedEvents.get(event.getCategory()).put(event.getId(),event);
        saveData(context);
        setEventNotification(context, event);
    }

    public void setEventNotification(Context context, Event event) {
        AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        EventNotification receiver = new EventNotification();
        IntentFilter filter = new IntentFilter(BroadcastTags.ACTION);
        context.registerReceiver(receiver, filter);

        Intent intent = new Intent(BroadcastTags.ACTION);
        intent.putExtra(BroadcastTags.EVENT_ID, event.getId());
        intent.putExtra(BroadcastTags.CATEGORY_NAME,event.getCategory().getName());
        intent.putExtra(BroadcastTags.EVENT_TITLE,event.getName());
        PendingIntent operation = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarms.set(AlarmManager.RTC_WAKEUP, event.getDueDate().getTimeInMillis(), operation) ;
    }


    private void setKeys(){
        categoriesAPIKeys.put(CategoryName.BOOKS, APIKey.BOOKS);
        categoriesAPIKeys.put(CategoryName.GENERAL, APIKey.GENERAL);
        categoriesAPIKeys.put(CategoryName.PLACES, APIKey.LOCATIONS);
        categoriesAPIKeys.put(CategoryName.MOVIES, APIKey.MOVIES);
        categoriesAPIKeys.put(CategoryName.MUSIC, APIKey.MUSIC);
        categoriesAPIKeys.put(CategoryName.RESTAURANTS, APIKey.RESTAURANTS);
    }

    private void setCategories(){
        for(CategoryName cn : CategoryName.values()){
            Category category = new Category(cn,categoriesAPIKeys.get(cn));
            HashMap<Long,Event> events = new HashMap<>();
            sortedEvents.put(category,events);
        }
    }

    private void saveData(Context context){
        SharedPreferencesHandler.saveData(context,userEvents);
    }

    private void getData(Context context){
        userEvents = SharedPreferencesHandler.getData(context);
        for(Event e : userEvents.getEvents()){
            sortedEvents.get(e.getCategory()).put(e.getId(),e);
        }
    }

    @Override
    public void onDataReceived(CategoryName categoryName, ArrayList<DBRecord> records) {
        ArrayList<DBRecord> approved = new ArrayList();
        for(DBRecord record : records){
            if(!sortedEvents.get(new Category(categoryName,null)).containsKey(record.getId()) && record.getRating()>=MINIMUM_RATING){
                approved.add(record);
            }
        }
        suggestion.put(categoryName,approved);
    }
}