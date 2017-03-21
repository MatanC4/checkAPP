package bl.controlers;

import java.util.HashMap;

import bl.entities.Category;
import bl.entities.CategoryName;
import bl.entities.Event;

/**
 * Created by Daniel_m on 14/03/2017.
 */

public class AppManager {

    private static AppManager singleton;
    private HashMap<CategoryName, Category> categories;
    private HashMap<Integer, Event> events;

    private AppManager(){
        categories = new HashMap<>();
        events = new HashMap<>();
        setCategories();
        getData();
    }

    public AppManager getInstance() throws Exception{
        if(singleton==null){
            singleton = new AppManager();
            return singleton;
        }
        throw new Exception("Instance of this object Already created");
    }

    private void setCategories(){

    }

    private void getData(){

    }
}