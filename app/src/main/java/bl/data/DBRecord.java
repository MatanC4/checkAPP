package bl.data;


/**
 * Created by Daniel_m on 26/03/2017.
 */

public class DBRecord implements Comparable<DBRecord>{
    private long id;
    private int rating;

    public DBRecord(long id, int rating) {
        this.id = id;
        this.rating = rating;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
