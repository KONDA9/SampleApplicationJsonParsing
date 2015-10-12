package com.altimetrik.sampleapplicationjsonparsing;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents get the data from the server and displays
 *
 * Created by bkondaiah on 09-10-2015.
 */
public class MainActivity extends Activity implements AdapterViewCompat.OnItemClickListener, Listener {

    static ArrayList <JsonArrayDTO> iTunesList;
    HTTPHandler request;
    private Context mContext;
    private ListView listview;
    private ListViewAdapter adapter;
    private ProgressDialog progressDialog;
    private JsonArrayDTO iTunes;
    JSONArray jsonarray;
    CountDownTimer myTimer = null;
    private JSONObject jsonObject;
    private String imageValue;
    private String url = "https://raw.githubusercontent.com/Saltside/mobile-coding-exercise/master/items.json";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        final Handler handler = new Handler();
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        GettingLocation();
                    }

                });
            }
        }, 2000);

        listview = (ListView) findViewById(R.id.listView);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "ListView clicked row  position==  " + position, Toast.LENGTH_SHORT).show();
                View parentView = (View) view.getParent();

                JsonArrayDTO selectedData=  iTunesList.get(position);

                String titleTV =selectedData.getTitle() ;
                String descriptionTV = selectedData.getDescription() ;
                String image =selectedData.getImage();

                Intent intent = new Intent(MainActivity.this, SingleItemView.class);
                // Pass all data rank
                intent.putExtra("title", titleTV);
                // Pass all data country
                intent.putExtra("description", descriptionTV);
                // Pass all data population
                intent.putExtra("image", image);
                // Pass all data flag
                startActivity(intent);
            }
        });

    }

    private void GettingLocation() {


        startProgress();
        request = new HTTPHandler(this, url);
        Thread th = new Thread(request);
        th.start();
    }


    @Override
    public void onResponse(String result, String res) {
        if (res.equals("success")) {

            this.iTunesList = new ArrayList();
            String jsonvalue = result;
            String text = null;

            try {
                jsonarray = new JSONArray(jsonvalue);

                for (int i = 0; i < jsonarray.length(); i++) {
                    jsonObject = jsonarray.getJSONObject(i);
                    iTunes = new JsonArrayDTO();
                    iTunes.setTitle(jsonObject.getString("title"));
                    iTunes.setDescription(jsonObject.getString("description"));
                    iTunes.setImage(jsonObject.getString("image"));

                    iTunesList.add(iTunes);


                }
                stopProgress();
            } catch (JSONException ex) {
                ex.printStackTrace();
                text = "JSON Exception: " + ex.getMessage() + "\n";
            } catch (Exception ex) {
                ex.printStackTrace();
                text = "Other Exception: " + ex.getMessage() + "\n";
            }
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                adapter = new ListViewAdapter(MainActivity.this, iTunesList);
                listview.setAdapter(adapter);
            }
        });

    }

    public void onError(String errorMessage) {
        Log.i("ITunes", "errorMessage -" + errorMessage);
        stopProgress();
    }
   //This method represents the progress message dialogue message
    private void startProgress() {
        progressDialog = ProgressDialog.show(this, "Parsing Data please wait",
                "Loading...", true);
    }

    private void stopProgress() {
        progressDialog.dismiss();
        Log.i("ITunes", "stopProgress");
    }

    @Override
    public void locError(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void locSuccess(String msg) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {

    }
}
