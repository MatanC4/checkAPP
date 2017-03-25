package bl.data;

import java.util.ArrayList;
import java.util.Collections;

import bl.entities.Event;

/**
 * Created by Daniel_m on 25/03/2017.
 */

public class UserEvents {
    public ArrayList<Event> events;

    public UserEvents (){
        this.events = new ArrayList<>();
    }

    public UserEvents(ArrayList<Event> events){
        this.events = events;
    }

    public ArrayList<Event> getEvents(){
        Collections.sort(events);
        return events;
    }
}
