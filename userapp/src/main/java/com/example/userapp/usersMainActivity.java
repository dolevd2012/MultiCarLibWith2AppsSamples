package com.example.userapp;

import android.os.Bundle;
import com.example.common.Activity_CarParent;

public class usersMainActivity extends Activity_CarParent {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Users App");
        downloadCars();
    }


}


