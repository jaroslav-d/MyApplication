package com.example.jaroslav.myapplication;

import android.app.Activity;
import android.os.Bundle;


public class Main extends Activity {
    PostView postView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postView = new PostView(this);
        setContentView(postView);
    }
}
