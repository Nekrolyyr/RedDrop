package ch.versusvirus.reddrop.logic;

import android.app.Activity;
import android.content.Intent;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.ui.MyCardActivity;
import ch.versusvirus.reddrop.ui.NotificationsActivity;

public class ToolbarActionManager {

    public static void donorCard(Activity activity) {
        activity.startActivity(new Intent(activity, MyCardActivity.class));
    }

    public static void notifications(Activity activity) {
        activity.startActivity(new Intent(activity, NotificationsActivity.class));
    }

    public static void share(Activity activity) {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, activity.getString(R.string.textToShare) + " " + activity.getString(R.string.appStoreURL));
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        activity.startActivity(shareIntent);

    }
}
