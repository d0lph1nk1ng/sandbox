package com.example.dk.sandbox;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class CanvasActivity extends Activity {

	private View mView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mView = new MyCanvasView(this);
		setContentView(mView);
		
		// Show the Up button in the action bar.
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_canvas, menu);
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
	
	private Context getActivityContext() {
		return this;
	}
	
	private class MyCanvasView extends View implements View.OnClickListener {
        
		private final static String TAG = "MyCanvasView";
		private final static int PADDING = 20;
		private final static int RADIUS = PADDING*4;
		private Paint mPaint = new Paint();
        private Path mPath = new Path();
        private boolean mAnimate;

        public MyCanvasView(Context context) {
            super(context);

            // Construct a wedge-shaped path
            mPath.moveTo(0, -50);
            mPath.lineTo(-20, 60);
            mPath.lineTo(0, 50);
            mPath.lineTo(20, 60);
            mPath.close();
        }

        @Override
        protected void onDraw(Canvas canvas) {
        	int w = canvas.getWidth();
            int h = canvas.getHeight();        	
        	Paint paint = mPaint;
        	paint.setAntiAlias(true);

        	// bg color
            canvas.drawColor(Color.WHITE);
            
            // rounded rect bg
            paint.setColor(Color.CYAN);            
            canvas.drawRoundRect(new RectF(PADDING, PADDING, w-PADDING, h-PADDING), RADIUS, RADIUS, paint);
            
            // compass obj
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            
            int cx = w / 2;
            int cy = h / 2;

            canvas.translate(cx, cy);
            canvas.drawPath(mPath, mPaint);
        }
        
        @Override
        protected void onAttachedToWindow() {
            mAnimate = true;
            if (false) Log.d(TAG, "onAttachedToWindow. mAnimate=" + mAnimate);
            super.onAttachedToWindow();
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimate = false;
            if (false) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
            super.onDetachedFromWindow();
        }

		@Override
		public void onClick(View v) {
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
				try {
					ObjectAnimator anim = (ObjectAnimator)AnimatorInflater.loadAnimator(getActivityContext(), R.anim.flipping); 
					anim.setTarget(this);
					anim.setDuration(3000);
					anim.start();
				} catch(Exception e) {
					;
				}
			}
		}
    }

}