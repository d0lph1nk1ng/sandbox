package com.example.dk.sandbox;

import com.example.dk.sandbox.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void launchScrollviewTest(View view) {
    	startActivity(new Intent(this, ScrollviewFragment.class));
    }
    
    public void launchDragDropTest(View view) {
    	startActivity(new Intent(this, DragDropFragment.class));
    }
    
    public void launchAnimationTest(View view) {
    	startActivity(new Intent(this, AnimationFragment.class));
    }
    
    public void launchCanvasTest(View view) {
    	startActivity(new Intent(this, CanvasFragment.class));
    }
    
}
