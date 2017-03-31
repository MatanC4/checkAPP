package bl.data;


import bl.entities.Event;

/**
 * Created by Daniel_m on 26/03/2017.
 */

public class DBRecord implements Comparable<DBRecord>{
    private Event event;
    private int rating;

    public DBRecord(){

    }

    public DBRecord(Event event, int rating) {
        this.event = event;
        this.rating = rating;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(DBRecord dbRecord) {
        return dbRecord.getRating()-this.getRating();
    }
}
