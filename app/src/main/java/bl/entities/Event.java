package bl.entities;

import java.util.Calendar;

import bl.data.DBRecord;

/**
 * Created by Daniel_m on 11/03/2017.
 */

public class Event implements Comparable<Event>{

    private long id;
    private String name;
    private String imageURL;
    private String imagePath;
    private String description;
    private Category category;
    private EventStatus status;
    private Calendar creationDate;
    private Calendar dueDate;
    private Amendment amendment;
    private int rating;


    public Event() {

    }
    public Event(long id) {
        this.id = id;
    }

    public Event(long id,String name, String imageURL, String description, Category category, EventStatus status, Calendar dueDate) {
        this(id);
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.category = category;
        this.status = status;
        this.dueDate = dueDate;
        this.creationDate = Calendar.getInstance();
    }

    public Event(DBRecord record){
        this.id = record.getId();
        this.name = record.getName();
        this.imageURL = record.getImageURL();
        this.description = record.getDescription();
        this.category = record.getCategory();
        this.rating = record.getRating();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
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

    public void addToDescription(String addedText , AdditionToDescription type){

        this.description += "\n" + type.toString() + "  " +  addedText + ".";
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public Amendment getAmendment() {
        return amendment;
    }

    public void setAmendment(Amendment amendment) {
        this.amendment = amendment;
    }

    @Override
    public String toString(){
      return this.name + "\n" + this.description;
    }

    @Override
    public int compareTo(Event event) {
        if(this.category.equals(event.getCategory())){
            if(this.getStatus()==this.getStatus()) {
                return (this.getCreationDate().compareTo(event.creationDate));
            }
            return this.getStatus().ordinal()-event.getStatus().ordinal();
        }
        return category.compareTo(event.getCategory());
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
