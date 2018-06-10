package com.example.jaroslav.myapplication;

import android.app.Activity;
import android.os.Bundle;


public class Main extends Activity {
    //MyView myView;

    @Override
    public void onCreate(Bundle savedInstatceState) {
        super.onCreate(savedInstatceState);
        //MyView myView = new MyView(this);
        //setContentView(myView);
        PostView postView = new PostView(this);
        setContentView(postView);
    }
}
