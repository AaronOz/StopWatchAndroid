package com.example.aaron.stopwatch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.os.Handler;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {



    TextView txt;
    Button startPause;
    Handler handler;
    private ListView mainListView;
    private ArrayAdapter<String> listAdapter;
    ArrayList<String> recordList = new ArrayList<String>();

    String result;
    String [] records;
    boolean pause = false;
    boolean started = false;
    int seconds, minutes = 0;
    long init,now,time,paused;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //List adapter
        mainListView = (ListView) findViewById(R.id.listView);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final Button start = (Button) findViewById(R.id.buttStart);
        start.setOnClickListener(new View.OnClickListener() {
            Runnable updater = new Runnable() {
                @Override
                public void run() {
                    txt = (TextView) findViewById(R.id.currTime);
                    now = System.currentTimeMillis();
                    time = now - init;
                    String s = Long.toString(time);
                    result = s;
                    txt.setText(minutes+":"+time/1000+"."+(time %1000) /10);
                    handler.postDelayed(this, 30);
                    if(time/1000 == 60){
                        init = System.currentTimeMillis();
                        minutes++;
                    }
                }
            };
            public void onClick(View view){
                startPause = (Button) findViewById(R.id.buttStart);
                if(pause == false){
                    if(started == false){
                        init = System.currentTimeMillis();
                        handler.post(updater);//Enter Loop
                        started = true;
                        pause = true;
                        startPause.setText("Pause");
                        startPause.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    }else {
                        onResume();
                        pause = true;
                        handler.post(updater);
                        startPause.setText("Pause");
                        startPause.setBackgroundColor(getResources().getColor(R.color.colorRed));
                    }
                }else{
                    onPause();
                    pause = false;
                    handler.removeCallbacks(updater);
                    startPause.setText("Resume");
                    startPause.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                }
                Log.i("pause",String.valueOf(pause));
            }
            });
    }
    public void Reset(View view){
        startPause = (Button) findViewById(R.id.buttStart);
        txt = (TextView) findViewById(R.id.currTime);
        if(pause == false) {
            init = System.currentTimeMillis();
            startPause.setText("Start");
            txt.setText("00:00:00");
        }else{//Store record
            recordList.add(txt.getText().toString());
            listAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, recordList);
            mainListView.setAdapter(listAdapter);
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
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onPause() {
        super.onPause();
        paused = System.currentTimeMillis();
    }

    @Override
    protected void onResume() {
        super.onResume();
        init += System.currentTimeMillis() - paused;
    }
}