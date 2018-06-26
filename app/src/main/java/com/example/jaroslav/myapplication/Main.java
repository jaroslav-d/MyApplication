package com.example.jaroslav.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;


public class Main extends Activity {
    //MyView myView;
    PostView postView;

    @Override
    public void onCreate(Bundle savedInstatceState) {
        super.onCreate(savedInstatceState);
        //MyView myView = new MyView(this);
        //setContentView(myView);
        postView = new PostView(this);
        setContentView(postView);
        postView.setOnTouchListener(myTouch);
    }

    private View.OnTouchListener myTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    postView.actionDownX = event.getAxisValue(event.AXIS_X,0);
                    postView.actionDownY = event.getAxisValue(event.AXIS_Y,0);
                    return true;


                case MotionEvent.ACTION_MOVE:

                    if (Math.abs(postView.actionDownY - event.getAxisValue(event.AXIS_Y)) >
                            Math.abs(postView.actionDownX - event.getAxisValue(event.AXIS_X))) {
                        if (postView.actionDownY - event.getAxisValue(event.AXIS_Y) < 0) {
                            postView.touch = "down"; break;
                        } else {
                            postView.touch = "up"; break;
                        }
                    } else {
                        if (postView.actionDownX - event.getAxisValue(event.AXIS_X) < 0) {
                            postView.touch = "right"; break;
                        } else {
                            postView.touch = "left"; break;
                        }
                    }

                case MotionEvent.ACTION_UP:
                    postView.actionDownX = 0;
                    postView.actionDownY = 0;
            }
            return true;
        }
    };
}
