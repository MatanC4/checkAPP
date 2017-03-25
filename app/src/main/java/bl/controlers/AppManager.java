package bl.controlers;

import android.content.Context;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import bl.data.SharedPreferencesHandler;
import bl.data.UserEvents;
import bl.entities.Amendment;
import bl.entities.AmendmentType;
import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;
import bl.entities.EventStatus;
import bl.entities.UserInfo;

/**
 * Created by Daniel_m on 14/03/2017.
 */

public class AppManager {

    private static AppManager singleton;
    private HashMap<Category, HashMap<Long, Event>> sortedEvents;
    private HashMap<CategoryName, String> categoriesAPIKeys;
    private UserEvents userEvents;

    private AppManager(Context context){
        sortedEvents = new HashMap<>();
        categoriesAPIKeys = new HashMap<>();
        userEvents = SharedPreferencesHandler.getData(context);
        setCategories();
        setKeys();
        getData(context);
    }

    public AppManager getInstance(Context context){
        if(singleton==null){
            singleton = new AppManager(context);
        }
        return singleton;
    }

    public void removeEvent(Context context, Event event){
        final boolean remove = userEvents.getEvents().remove(event);
        if(remove){
            sortedEvents.get(event.getCategory()).remove(event.getId());
            SharedPreferencesHandler.saveData(context,userEvents);
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

    public Event addEvent(Context context, long id, CategoryName categoryName, String name, String imageURL, String description, Calendar dueDate,
                          AmendmentType amendmentType, String amendmentDescription){
        Calendar calendar = Calendar.getInstance();
        long eventID = categoryName==CategoryName.GENERAL? id: SharedPreferencesHandler.getNextGeneralID(context);
        Event event = new Event(eventID, name, imageURL, description, new Category(categoryName,categoriesAPIKeys.get(categoryName)),
                EventStatus.TODO, dueDate);
        Amendment amendment = new Amendment(amendmentType, amendmentDescription);
        event.setAmendment(amendment);
        userEvents.getEvents().add(event);
        sortedEvents.get(event.getCategory()).put(event.getId(),event);
        SharedPreferencesHandler.saveData(context,userEvents);
        return event;
    }


    private void setKeys(){
        categoriesAPIKeys.put(CategoryName.BOOKS, APIKey.BOOKS);
        categoriesAPIKeys.put(CategoryName.GENERAL, APIKey.GENERAL);
        categoriesAPIKeys.put(CategoryName.LOCATIONS, APIKey.LOCATIONS);
        categoriesAPIKeys.put(CategoryName.MOVIES_AND_SHOWS, APIKey.MOVIES);
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

    public void saveData(Context context){
        SharedPreferencesHandler.saveData(context,userEvents);
    }

    private void getData(Context context){
        userEvents = SharedPreferencesHandler.getData(context);
        for(Event e : userEvents.getEvents()){
            sortedEvents.get(e.getCategory().getName()).put(e.getId(),e);
        }
    }
}