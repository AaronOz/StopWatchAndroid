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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.os.Handler;

import java.text.DecimalFormat;


public class MainActivity extends AppCompatActivity {

//    Chronometer timer = (Chronometer) findViewById(R.id.chronometerMain);

    long init,now,time,paused;
    TextView txt;
    Button startPause;
    int seconds, minutes = 0;
    Handler handler;
    String result;
    boolean pause = false;
    boolean started = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler = new Handler();
        setContentView(R.layout.content_main);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
                        handler.post(updater);
                        started = true;
                        pause = true;
                        startPause.setText("Pause");
                    }else {
                        onResume();
                        pause = true;
                        handler.post(updater);
                        startPause.setText("Pause");
                    }
                }else{
                    onPause();
                    pause = false;
                    handler.removeCallbacks(updater);
                    startPause.setText("Resume");
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
        }else{
            //Store in the listview
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