package com.example.dk.sandbox;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.NavUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

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
	
	private class MyCanvasView extends View {
        
		private final static String TAG = "MyCanvasView";
		private final static int PADDING = 0;
		private final static int RADIUS = 80;
		private final static int FPS = 30;
				
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        private Path mPath = new Path();
        private RectF mBackgroundRect = new RectF();
        private LinearGradient mLineShader;
        private boolean mAnimate;
        private int mHeadX, mWidth;

        public MyCanvasView(Context context) {
            super(context);

            mHeadX = 0;
            
            final DisplayMetrics metrics = new DisplayMetrics();
        	final WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
    		wm.getDefaultDisplay().getMetrics(metrics);
    		mWidth = metrics.widthPixels;
    		final int h = metrics.heightPixels;
    		final float d = context.getResources().getDisplayMetrics().density;
            
            mPath.lineTo(100, 0);
            mPath.lineTo(115, (int)(40.0/110.0 * h/4.0));
            mPath.lineTo(130, (int)(-20.0/110.0 * h/4.0));
            mPath.lineTo(150, (int)(110.0/110.0 * h/4.0));
            mPath.lineTo(165, (int)(-100.0/110.0 * h/4.0));
            mPath.lineTo(180, (int)(0.0/110.0 * h/4.0));
            mPath.lineTo(380, 0);
            mPath.lineTo(mWidth-100, 0);		// should stop at about 380
                        
            mLineShader = new LinearGradient(0, h/2, mWidth, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP);
        }

        @Override
        protected void onDraw(Canvas canvas) {
        	int w = canvas.getWidth();
            int h = canvas.getHeight();        	
        	Paint paint = mPaint;

        	// bg color
            canvas.drawColor(Color.BLACK);
          
            // translate to the left edge of the vertical center
            canvas.translate(0, h/2);

            // set the paint obj to draw the line
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.GREEN);
                        
            // the line path
            //canvas.drawPath(mPath, paint);
            
            // the line tail
            int tailX = (int)(mHeadX - 0.69*mWidth);
            paint.setShader(new LinearGradient(tailX, h/2, mHeadX, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));
            canvas.drawLine(tailX, 0, mHeadX, 0, paint);
            
            // the line head circle
            canvas.translate(mHeadX, 0);
            paint.setShader(null);
            canvas.drawCircle(0, 0, 24, paint);
            
            // the line head dot
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(0, 0, 12, paint);
            
            
        }
        
        @Override
        protected void onAttachedToWindow() {
            mAnimate = true;
            if (false) Log.d(TAG, "onAttachedToWindow. mAnimate=" + mAnimate);
            super.onAttachedToWindow();
            mHandler.postDelayed(mUpdateUiTask, 1000/FPS);
        }

        @Override
        protected void onDetachedFromWindow() {
            mAnimate = false;
            if (false) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
            super.onDetachedFromWindow();
        }

		private Handler mHandler = new Handler();
		
		private Runnable mUpdateUiTask = new Runnable() {
			public void run() {
				invalidate();
				mHeadX += 0.0138 * mWidth;
				if(mHeadX > mWidth * 1.69) {
					mHeadX = -50;
				}
				
				if(mAnimate) {
					mHandler.postDelayed(this, 1000/FPS);
				}
			}
		};
		
    }
	
}