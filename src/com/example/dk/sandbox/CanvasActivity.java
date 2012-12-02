package com.example.dk.sandbox;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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
		private final static int FPS = 30;
				
		private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        private Path mPath = new Path(), mSegment = new Path();
        private PathMeasure mPathMeasure = new PathMeasure(), mPathMeasureSegment = new PathMeasure();
        private LinearGradient mLineShader;
        private boolean mAnimate;
        private int mHeadX, mWidth, mHeight;

        public MyCanvasView(Context context) {
            super(context);

            mHeadX = -50;
            
            final DisplayMetrics metrics = new DisplayMetrics();
        	final WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
    		wm.getDefaultDisplay().getMetrics(metrics);
    		mWidth = metrics.widthPixels;
    		final int h = metrics.heightPixels;
    		final float d = context.getResources().getDisplayMetrics().density;
            
    		int xBlip = (int)(mWidth / 2.0);
    		mPath.lineTo(xBlip, 0);
            mPath.lineTo(xBlip + 15, (int)(40.0/110.0 * h/4.0));
            mPath.lineTo(xBlip + 30, (int)(-20.0/110.0 * h/4.0));
            mPath.lineTo(xBlip + 50, (int)(110.0/110.0 * h/4.0));
            mPath.lineTo(xBlip + 65, (int)(-100.0/110.0 * h/4.0));
            mPath.lineTo(xBlip + 80, (int)(0.0/110.0 * h/4.0));
            mPath.lineTo(mWidth, 0);
            
            mPathMeasure.setPath(mPath, false);
            Log.d(TAG, "path length: " + mPathMeasure.getLength());
            
            mLineShader = new LinearGradient(0, h/2, mWidth, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP);
        }

        @Override
        protected void onDraw(Canvas canvas) {
        	int w = canvas.getWidth();
            int h = canvas.getHeight();        	
        	mHeight = h;
            Paint paint = mPaint;

        	// bg color
            canvas.drawColor(Color.BLACK);
          
            // translate to the left edge of the vertical center
            canvas.translate(0, h/4);

            // set the paint obj to draw the line
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(Color.GREEN);
            paint.setStrokeWidth(5f);
            
            // draw the segment of the path
            int tailX = (int)(mHeadX - 0.69*mWidth);
            mSegment.reset();
            mPathMeasure.getSegment(Math.min(Math.max(tailX, 0), mWidth), Math.min(Math.max(mHeadX, 0), mWidth), mSegment, true);
            mPathMeasureSegment.setPath(mSegment, false);
            float lenSeg = mPathMeasureSegment.getLength();
            
            if(lenSeg > 0) {
            	paint.setShader(new LinearGradient(tailX, h/2, mHeadX, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));
            	canvas.drawPath(mSegment, paint);
            }

            canvas.translate(0, h/4);
            
            // draw the entire path
            canvas.drawPath(mPath, paint);
            
            // hide part of the path
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setShader(null);
            paint.setColor(Color.BLACK);
            canvas.drawRect(Math.max(mHeadX, 0.0f), -h/3f, (float)mWidth, h/3f, paint);
            
            canvas.translate(0, h/4);
            
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.GREEN);
            
            // the line tail
            
            //paint.setShader(new LinearGradient(tailX, h/2, mHeadX, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));
            //canvas.drawLine(tailX, 0, mHeadX, 0, paint);
/*                        
            // the line head circle
            canvas.translate(mHeadX, 0);
            paint.setShader(null);
            canvas.drawCircle(0, 0, 24, paint);
            
            // the line head dot
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(0, 0, 12, paint);
*/            
            
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
				mHeadX += 0.0138 * mWidth;
				if(mHeadX > mWidth * 1.69) {
					mHeadX = -50;
				}
				
				int canvasY = (int)(3.0/4.0*mHeight);
	            int tailX = (int)(mHeadX - 0.69*mWidth);
				//invalidate(tailX, canvasY-30, mHeadX+30, canvasY+30);
				invalidate();
	            
				if(mAnimate) {
					mHandler.postDelayed(this, 1000/FPS);
				}
			}
		};
		
    }
	
}