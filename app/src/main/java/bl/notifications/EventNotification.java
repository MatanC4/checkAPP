package bl.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.matka.check.Category.CategoryActivity;

/**
 * Created by Daniel_m on 28/03/2017.
 */

public class EventNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context,CategoryActivity.class);
        Toast.makeText(context, intent.getStringExtra("param"),Toast.LENGTH_SHORT).show();
        Log.d("ALARM","On");
        context.startActivity(newIntent);
    }
}
