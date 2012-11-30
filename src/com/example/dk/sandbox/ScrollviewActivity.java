package com.example.dk.sandbox;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ScrollviewActivity extends Activity {

	private final static int MARGIN = 10;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		
		// Show the Up button in the action bar.
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
		
		final DisplayMetrics metrics = new DisplayMetrics();
    	final WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		final HorizontalScrollView hsv = (HorizontalScrollView)findViewById(R.id.vScrollview);
		hsv.post(new Runnable() {
		    @Override
		    public void run() {
		    	wm.getDefaultDisplay().getMetrics(metrics);
		    	final float w = metrics.widthPixels;
		    	
		    	LayoutParams lpTri = new LayoutParams((int)(w*2.0/3.0) - 2*MARGIN, LayoutParams.MATCH_PARENT);
		    	lpTri.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
		    	((TextView)findViewById(R.id.txtTri)).setLayoutParams(lpTri);
		    	
		    	LayoutParams lpCol = new LayoutParams((int)(w/3.0) - 2*MARGIN, LayoutParams.MATCH_PARENT);
		    	lpCol.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
		    	((TextView)findViewById(R.id.txtCol1)).setLayoutParams(lpCol);
		    	((TextView)findViewById(R.id.txtCol2)).setLayoutParams(lpCol);
		    	((TextView)findViewById(R.id.txtCol3)).setLayoutParams(lpCol);
		    	
		    	hsv.scrollTo(hsv.getWidth(), 0);
		    } 
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_scrollview, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
