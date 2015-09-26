package olx.olxtechathon.services;


import android.app.IntentService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import olx.olxtechathon.db_helper.SQLiteAccess;

public class DataSynIntentService extends IntentService {

    public DataSynIntentService() {
        super("DataFetchingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
//        fetchIssues();
    }
}