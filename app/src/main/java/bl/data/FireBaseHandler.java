package bl.data;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import bl.entities.CategoryName;
import bl.entities.Event;
import bl.entities.UserInfo;

/**
 * Created by Daniel_m on 26/03/2017.
 */

public class FireBaseHandler {

    private static final int NUMBER_OF_RECORDS = 10;
    private static final int AGE_GROUP = 9;
    private DatabaseReference mDatabase;
    private DataListener listener;

    public FireBaseHandler(DataListener listener){
        this.listener = listener;
    }

    public void writeToFireBase(Event event,UserInfo info){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String reference = event.getCategory().getName().toString() + "/" + info.getCountry() +"/"+ (int)(info.getAge()/AGE_GROUP);
        mDatabase = database.getReference(reference);
        DatabaseReference postsRef = mDatabase;
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(new DBRecord(event));
    }

    public void readFromFireBase(final CategoryName cName, UserInfo info){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String reference = cName.toString() + "/" + info.getCountry().toString() + "/" + (int)(info.getAge()/AGE_GROUP);
        Query q = database.getReference(reference).limitToLast(NUMBER_OF_RECORDS);

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<DBRecord> records = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    records.add(child.getValue(DBRecord.class));
                }
                listener.onDataReceived(cName, records);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }



}
