package bl.data;


import java.util.Calendar;

import bl.entities.Amendment;
import bl.entities.Category;
import bl.entities.Event;
import bl.entities.EventStatus;

/**
 * Created by Daniel_m on 26/03/2017.
 */

public class DBRecord implements Comparable<DBRecord>{
    private long id;
    private String name;
    private String imageURL;
    private String description;
    private Category category;
    private int rating;

    public DBRecord(){

    }

    public DBRecord(long id, String name, String imageURL, String description, Category category, int rating) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.category = category;
        this.rating = rating;
    }

    public DBRecord(Event event){
        this(event.getId(), event.getName(), event.getImageURL(), event.getDescription(),
                event.getCategory(),event.getRating());
    }


    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "DBRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", rating=" + rating +
                '}';
    }

    @Override
    public int compareTo(DBRecord dbRecord) {
        return dbRecord.getRating()-this.getRating();
    }
}
