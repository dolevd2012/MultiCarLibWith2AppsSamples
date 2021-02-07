package com.example.common;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

public abstract class Activity_CarParent extends AppCompatActivity {

    TextView myRetroInfo;
    TextView myTitle;
    TextView timer;
    Thread thread;
    boolean interruptedThread=false;
    int count = 0;
    RoomDB database;
    boolean granted = false;
    TimeClass timeClass = new TimeClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carparent);

        myTitle= findViewById(R.id.TV_main_title);
        myRetroInfo=findViewById(R.id.TV_main_retrofitInfo);
        timer = findViewById(com.example.common.R.id.TV_main_timer);


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_CarParent.this
                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}
                    , 123);
        }
        else {
            granted = true;
        }
        Date currentTime = Calendar.getInstance().getTime();
        timeClass.setStartTime(currentTime.toString());
        database = RoomDB.getInstance(this);
        if(database.mainDao().getSpecificDataBysID(1)!=null){
            count = database.mainDao().getSpecificDataBysID(1).getTimer();
            timer.setText(String.valueOf(count));
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 123) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                granted=true;
            }
        }
    }


    protected void setTitle(String title) {
        myTitle.setText(title);
    }
    protected void downloadCars() {
        new CarProController().fetchAllCars(new CarProController.CallBack_Car() {
            @Override
            public void car(Car car) {
                myRetroInfo.setText(car.toString());
            }
        });
    }
    protected void startTime(){
        thread = new Thread() {
            @Override
            public void run() {
                while (!interruptedThread) {
                    try {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                count++;
                                timer.setText(String.valueOf(count));
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        interruptedThread=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        interruptedThread = false;
        startTime();
    }

    @Override
    protected void onDestroy() {
        isGranted();
        super.onDestroy();
        Log.d("currentFlow" , "Destroyed");
        MainData mainData= new MainData();
        mainData.setID(1);
        mainData.setTimer(count);
        Log.d("Timer" ,"Timer " + count + "Was enter to the room DATABASE");
        database.mainDao().insert(mainData);
    }
    protected void isGranted(){
        if(granted != false) {
            timeClass.setEndTime(Calendar.getInstance().getTime().toString());
            String str = new Gson().toJson(timeClass);
            writeToFile(str, "myFile.txt");
        }
    }


    protected void writeToFile(String dataToWrite, String fileNameWithExtension){
        Log.d("currentFlow","Writing to File");
        File folder = new File(getExternalFilesDir(null)
                + File.separator + "MyFiles" + File.separator + "Adopt_Files");

        boolean isExist = folder.exists();
        boolean success = folder.mkdirs();

        //  Root -> MyFiles -> Adopt_Files -> myFile.txt
        File file = new File(folder.getPath() + File.separator + fileNameWithExtension);
        PrintWriter writer = null;

        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            writer = new PrintWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.print(dataToWrite);
            writer.flush();
            writer.close();
        } catch (FileNotFoundException e) {
            Log.d("pttt", "writeToFile FileNotFoundException" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            Log.d("pttt", "writeToFile IOException" + e.getMessage());
            e.printStackTrace();
        }

    }


}
