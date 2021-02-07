package com.example.multicarapp;
import android.os.Bundle;
import com.example.common.Activity_CarParent;


public class GarageApp extends Activity_CarParent {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Garage App");
        downloadCars();
    }

}