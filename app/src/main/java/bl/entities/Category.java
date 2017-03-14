package bl.entities;

import java.util.ArrayList;

/**
 * Created by Daniel_m on 11/03/2017.
 */

public class Category {

    private CategoryName name;
    private String apiURI;
    private ArrayList<Event> events;

    public Category(CategoryName name, String apiURI) {
        this.name = name;
        this.apiURI = apiURI;
        events = new ArrayList<>();
    }

    public void addEvent(Event event) throws Exception{
        if(event.getCategory().equals(this))
        events.add(event);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Category category = (Category) o;
        return name.equals(category.name);
    }
}
