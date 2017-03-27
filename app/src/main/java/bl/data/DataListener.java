package bl.data;

import java.util.ArrayList;

import bl.entities.CategoryName;

/**
 * Created by Daniel_m on 26/03/2017.
 */

public interface DataListener {
    void onDataReceived(CategoryName categoryName, ArrayList<DBRecord> records);
}
