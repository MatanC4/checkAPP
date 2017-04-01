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
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
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
import bl.entities.DateComparator;
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
    private HashMap<CategoryName, ArrayList<Event>> suggestion;
    private HashMap<CategoryName, Category> categories;
    private HashMap<String, Bitmap> images;
    private UserEvents userEvents;
    private UserInfo info;
    private FireBaseHandler fbHandler;

    private AppManager(Context context){
        initMaps();
        userEvents = SharedPreferencesHandler.getData(context);
        this.fbHandler =  new FireBaseHandler(this);
        UserInfo info = getUserInformation(context);
        if(!info.isAnonymous())
            readFromFireBase(info);
        setCategories();
        setKeys();
        getData(context);
    }


    public Event changeEventStatus(Context context, Event event, EventStatus status){
        Log.v("changeEventStatus", event.toString() + " " +event.getCategory().getName());
        Event modifiedEvent = sortedEvents.get(categories.get(event.getCategory().getName())).get(event.getId());
        if(event.getStatus()!=status){
            modifiedEvent.setStatus(status);
            if(status==EventStatus.DONE)
                writeToFireBase(context, event);
            saveData(context);
        }
        return modifiedEvent;
    }

    public ArrayList<Event> getEventsByStatus(CategoryName cName, EventStatus status){
        Log.v("CategoryBeforeCarsh", cName.toString());
        HashMap<Long, Event> eventsInCategory = sortedEvents.get(categories.get(cName));
        ArrayList<Event> eventsToReturn = new ArrayList<>();
        for(Event e : eventsInCategory.values()){
            if(e.getStatus()==status){
                eventsToReturn.add(e);
            }
        }
        return eventsToReturn;
    }

    public ArrayList<Event> getNext5Events(){

        ArrayList<Event> sortedByDate = new ArrayList<>(userEvents.getEvents());
        Log.d("EVENTS", sortedByDate.toString());
        Collections.sort(sortedByDate, new DateComparator());
        ArrayList<Event> nextFive = new ArrayList<>();
        for(int i=0 ; i<Math.min(5,sortedByDate.size()) ; i++){
            if(sortedByDate.get(i).getStatus()==EventStatus.TODO)
                nextFive.add(sortedByDate.get(i));
            else{
                break;
/*                sortedByDate.get(i).setStatus(EventStatus.TODO);
                nextFive.add(sortedByDate.get(i));*/

            }
        }
        Log.d("next5", nextFive.toString());
        return nextFive;
    }


    public Bitmap getImageByKey(String key) throws Exception{
        if(!images.containsKey(key))
            throw new Exception("Invalid Key");
        return images.get(key);
    }
    
    public void temporarilyStoreImage(String key, Bitmap image){
        images.put(key, image);
    }

    public void removeImageFromMap(String key){
        images.remove(key);
    }

    public ArrayList<Event> getEventsInCategory(CategoryName cName){
        HashMap<Long,Event> eventsMap = sortedEvents.get(categories.get(cName));
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

    public ArrayList<Event> getSuggestionsByProfile(CategoryName categoryName, Context context) throws Exception{
        if(info==null)
            info = getUserInformation(context);
        if(info.isAnonymous())
            throw new Exception("Sorry! this feature is not available for anonymous users");
        return suggestion.get(categoryName);
    }

    private void readFromFireBase(UserInfo info) {
        for(CategoryName cn : CategoryName.values()) {
            try {
                fbHandler.readFromFireBase(cn, info);
            }
            catch(Exception e){
                Log.v("FIRE_BASE","Reading failed, Category: " +cn.toString());
                suggestion.put(cn,new ArrayList<Event>());
            }
        }
    }



    public boolean isEventAlreadyExist(long id, CategoryName cName){
        try {
            return sortedEvents.get(categories.get(cName)).containsKey(id);
        }
        catch(Exception e){return false;}
    }

    private void initMaps(){
        sortedEvents = new HashMap<>();
        categoriesAPIKeys = new HashMap<>();
        suggestion = new HashMap<>();
        images = new HashMap<>();
        categories = new HashMap<>();

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
        Log.v("image_path", event.getImagePath());
        return BitmapFactory.decodeFile(event.getImagePath());
    }


    public Event addEvent(Context context, Event event, Bitmap image) {

        Calendar calendar = Calendar.getInstance();
        long eventID = event.getCategory().getName()==CategoryName.GENERAL?
                SharedPreferencesHandler.getNextGeneralID(context):event.getId();
        event.setCreationDate(calendar);
        event.setId(eventID);
        event.setStatus(EventStatus.TODO);
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
            event.setImagePath(myDir.getPath());
        } catch(Exception e){
            Log.d("IMAGE", "Saving the image failed");
            e.printStackTrace();
        }

    }
    public Event addEventByDetails(Context context, long id, CategoryName categoryName, String name, String imageURL, String description, Calendar dueDate,
                                   AmendmentType amendmentType, String amendmentDescription, Bitmap image){
        Event event = new Event(id, name, imageURL, description, categories.get(categoryName),
                EventStatus.TODO, dueDate);
        Amendment amendment = new Amendment(amendmentType, amendmentDescription);
        event.setAmendment(amendment);
        return addEvent(context, event, image);
    }

    private void saveAndStoreEvent(Context context, Event event){
        userEvents.getEvents().add(event);
        sortedEvents.get(categories.get(event.getCategory().getName())).put(event.getId(),event);
        saveData(context);
        setEventNotification(context, event);
    }

    public void setEventNotification(Context context, Event event) {
        AlarmManager alarms = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        EventNotification receiver = new EventNotification();
        IntentFilter filter = new IntentFilter(BroadcastTags.ACTION);
        context.registerReceiver(receiver, filter);

        Intent intent = new Intent(BroadcastTags.ACTION);
        Gson gson = new Gson();
        String json =  gson.toJson(event);
        intent.putExtra(BroadcastTags.EVENT_OBJ, json);
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
            categories.put(cn, category);
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
            e.setCategory(categories.get(e.getCategory().getName()));
            sortedEvents.get(e.getCategory()).put(e.getId(),e);
        }
    }

    @Override
    public void onDataReceived(CategoryName categoryName, ArrayList<DBRecord> records) {
        ArrayList<Event> approved = new ArrayList();
        for(DBRecord record : records){
            if(!sortedEvents.get(categories.get(categoryName)).containsKey(record.getId()) && record.getRating()>=MINIMUM_RATING){
                Event event = new Event(record);
                event.setStatus(EventStatus.VIEW);
                event.setImagePath(null);
                event.setAmendment(null);
                event.setCreationDate(null);
                event.setDueDate(null);
                approved.add(event);
            }
        }

        suggestion.put(categoryName,approved);
    }
}