package olx.olxtechathon.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import olx.olxtechathon.R;
import olx.olxtechathon.application.OLXApplication;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressBar progressBar;
    private OLXApplication olxApplication;
    private BroadcastReceiver mReceiver;
    private IntentFilter mIntentFilter;
    private int lastPosition;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        olxApplication = OLXApplication.getInstance();

        mIntentFilter = new IntentFilter("data.fetching.completed");
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!TextUtils.isEmpty(intent.getStringExtra("success"))) {
//                    populateListData();
                } else if (!TextUtils.isEmpty(intent.getStringExtra("failure"))) {
                    progressBar.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Failed : " + intent.getStringExtra("failure"), Toast.LENGTH_LONG).show();
                }
            }
        };

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listView != null) {
            outState.putInt("first_visible_item", listView.getFirstVisiblePosition());
        }
        if (dialog != null && dialog.isShowing()) {
            outState.putBundle("comment_dialog", dialog.onSaveInstanceState());
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            lastPosition = savedInstanceState.getInt("first_visible_item");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent intent = new Intent(MainActivity.this, PostingActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    public void hideAlertDialog() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }
}
