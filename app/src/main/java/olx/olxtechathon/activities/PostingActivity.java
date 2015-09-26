package olx.olxtechathon.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.hardware.Camera;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.plus.Plus;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import olx.olxtechathon.R;
import olx.olxtechathon.application.OLXApplication;
import olx.olxtechathon.models.CategoryModel;

public class PostingActivity extends AppCompatActivity implements SurfaceHolder.Callback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private OLXApplication olxApplication;

    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    protected static final int LOCATION_REQUEST_CODE = 137;

    private SurfaceView SurView;
    private SurfaceHolder camHolder;
    private boolean previewRunning;
    final Context mContext = this;
    public static Camera camera = null;

    private Dialog dialog;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean mIntentInProgress;
    private CategoryModel categoryModel;

    private EditText et_description, et_adtitle, et_category, et_locality;
    private Button b_upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.posting_activity_layout);
        olxApplication = OLXApplication.getInstance();
        categoryModel = new CategoryModel();

        SurView = (SurfaceView)findViewById(R.id.sv_cameraview);
        camHolder = SurView.getHolder();
        camHolder.addCallback(this);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);


        et_description = (EditText)findViewById(R.id.et_description);
        et_description.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                // TODO Auto-generated method stub
                if(et_description.getText().toString().length() > 10){
                    intelligenceActivited(et_description.getText().toString());
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {

                // TODO Auto-generated method stub
            }
        });


        et_adtitle = (EditText)findViewById(R.id.et_adtitle);
        et_category = (EditText)findViewById(R.id.et_category);
        et_locality = (EditText)findViewById(R.id.et_locality);

        b_upload = (Button)findViewById(R.id.b_upload);

        setHideSoftKeyboard();

        buildGoogleApiClient();

    }

    protected void buildGoogleApiClient() {
        disconnect();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }


    @Override
    protected void onStop() {
        super.onStop();
        disconnect();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        if (listView != null) {
//            outState.putInt("first_visible_item", listView.getFirstVisiblePosition());
//        }
//        if (dialog != null && dialog.isShowing()) {
//            outState.putBundle("comment_dialog", dialog.onSaveInstanceState());
//        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        if (savedInstanceState != null) {
//            lastPosition = savedInstanceState.getInt("first_visible_item");
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_add) {
//
//
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if(previewRunning){
            camera.stopPreview();
        }
        Camera.Parameters camParams = camera.getParameters();
        Camera.Size size = camParams.getSupportedPreviewSizes().get(0);
        camParams.setPreviewSize(size.width, size.height);


        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

        if(display.getRotation() == Surface.ROTATION_0)
        {
            camera.setDisplayOrientation(90);
        }

        if(display.getRotation() == Surface.ROTATION_90)
        {
//            parameters.setPreviewSize(width, height);
        }

        if(display.getRotation() == Surface.ROTATION_180)
        {
//            parameters.setPreviewSize(height, width);
        }

        if(display.getRotation() == Surface.ROTATION_270)
        {
            camera.setDisplayOrientation(180);
        }

        camera.setParameters(camParams);

        try{
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            previewRunning=true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera=Camera.open();
        }catch(Exception e){
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(camera != null){
            camera.stopPreview();
            camera.release();
            camera=null;
        }
    }

    public void showAlertDialog(final String comment) {
        PostingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog == null) {
                    dialog = new Dialog(PostingActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    View view = LayoutInflater.from(PostingActivity.this).inflate(R.layout.dialog_view, null, false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(view);
                    // set the custom dialog components - text, image and button
                    dialog.show();
                }
            }
        });


    }

    public void hideAlertDialog() {
        PostingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            et_description.setText(getLocationName(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mIntentInProgress = false;
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        boolean flag = connectionResult.hasResolution();

        if (!mIntentInProgress && flag) {
            try {
                mIntentInProgress = true;
//                Log.d("GooglePlusLogin", "result: " + result.getResolution().getIntentSender());
                PostingActivity.this.startIntentSenderForResult(connectionResult.getResolution().getIntentSender(),
                        LOCATION_REQUEST_CODE, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                // The intent was canceled before it was sent.  Return to the default
                // state and attempt to connect to get an updated ConnectionResult.
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        } else {
            mIntentInProgress = false;
            disconnect();
        }
    }

    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        } else if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        mIntentInProgress = false;
    }

    private void setHideSoftKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_description.getWindowToken(), 0);
    }

    public String getLocationName(double lattitude, double longitude) {

        String cityName = "";
        Geocoder gcd = new Geocoder(PostingActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = gcd.getFromLocation(lattitude, longitude, 10);
            for (Address adrs : addresses) {
                if (adrs != null) {

                    String city = adrs.getLocality();
                    if (city != null && !city.equals("")) {
                        cityName = city;
                        System.out.println("city ::  " + cityName);
                    } else {

                    }
                    // // you should also try with addresses.get(0).toSring();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK) {
                    mIntentInProgress = false;
                    if (mGoogleApiClient != null && !mGoogleApiClient.isConnecting()) {
                        mGoogleApiClient.connect();
                    }
                }
                break;

            case CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE:
                break;
            default:
                break;
        }
    }



    private void intelligenceActivited(final String input){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] stringData = input.split(" ");
                Map<String, Integer> occurrences = new HashMap<String, Integer>();

                for ( String word : stringData ) {
                    Integer oldCount = occurrences.get(word);
                    if ( oldCount == null ) {
                        oldCount = 0;
                    }
                    occurrences.put(word, oldCount + 1);
                }

                int maxRepeat = 0;
                String maxRepeatWord = "";
                for ( String word : occurrences.keySet() ) {
                    //do something with word
                    if(occurrences.get(word) > maxRepeat){
                        maxRepeat = occurrences.get(word);
                        maxRepeatWord = word;
                    }
                }

                setTitle(maxRepeatWord);

                for (int i = 0; i < stringData.length; i++) {
                    for (int j = 0; j < categoryModel.getCategoryList().size(); j++) {
                        CategoryModel.CategoryItem categoryItem = categoryModel.getCategoryList().get(j);
                        if(categoryItem.getName().equalsIgnoreCase(stringData[i])){
                            setCategory(stringData[i]);
                            break;
                        }
                        if(categoryItem.getSubCategories().contains(stringData[i])){
                            int indexofSubCat = categoryItem.getSubCategories().indexOf(stringData[i]);
                            setCategory(categoryItem.getName() + " -> " + categoryItem.getSubCategories().get(indexofSubCat));
                            break;
                        }
                    }
                }

            }
        }).start();
    }


    private void setCategory(final String category){
        PostingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_category.setText(category);
            }
        });
    }

    private void setTitle(final String title){
        PostingActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                et_adtitle.setText(title);
            }
        });
    }


}
