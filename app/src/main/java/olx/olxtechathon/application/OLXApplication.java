package olx.olxtechathon.application;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import olx.olxtechathon.db_helper.SQLiteAccess;

public class OLXApplication extends Application {

    private static OLXApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public OLXApplication() {
        instance = this;
    }

    public static OLXApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

}
