package com.example.matka.check.APIs;

/**
 * Created by matka on 25/03/17.
 */
public class RowItem {

    private String title;
    private int eventImageId;
    private int addButtonImage;

    public RowItem(String title, int eventImageId,
                   int addButtonImage ) {

        this.title = title;
        this.eventImageId = eventImageId;
        this.addButtonImage = addButtonImage;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEventImageId() {
        return eventImageId;
    }

    public void setEventImageId(int eventImageId) {
        this.eventImageId = eventImageId;
    }

    public int getAddButtonImage() {
        return addButtonImage;
    }

    public void setAddButtonImage(int addButtonImage) {
        this.addButtonImage = addButtonImage;
    }

    public String getTitle() {

        return title;
    }
}