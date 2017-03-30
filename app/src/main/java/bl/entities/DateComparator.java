package bl.entities;

import java.util.Comparator;

/**
 * Created by Daniel_m on 29/03/2017.
 */

public class DateComparator implements Comparator<Event> {
    @Override
    public int compare(Event event1, Event event2) {
        if(event1.getStatus()!=event2.getStatus()) {
            if (event1.getStatus() == EventStatus.TODO)
                return -1;
            if(event2.getStatus() == EventStatus.TODO )
                return 1;
        }
        return (int)(event2.getDueDate().getTimeInMillis()-event2.getDueDate().getTimeInMillis());
    }
}
