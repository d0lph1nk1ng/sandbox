package com.example.dk.sandbox;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class MyCanvasView extends View implements ObjectAnimator.AnimatorUpdateListener {
    
	private final static String TAG = "MyCanvasView";
	private final static int FPS = 30;
	private final static int DURATION = 3000;
	private final static int OFFSCREEN = 50;
	private final static float PERCENT_SCREEN = 0.69f;
			
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    private Path mPath = new Path(), mSegment = new Path();
    private PathMeasure mPathMeasure = new PathMeasure(), mPathMeasureSegment = new PathMeasure();
    private LinearGradient mLineShader;
    private boolean mAnimate;
    private int mHeadX, mWidth, mHeight;
    private MyPoint mHead;
    ValueAnimator mTranslateAnim;

    public MyCanvasView(final Context context) {
    	this(context, null);
    }
    
    public MyCanvasView(final Context context, AttributeSet attrs) {
        super(context);
                
        final View view = this;
		view.post(new Runnable() {
		    @Override
		    public void run() {
		    	mWidth = getWidth();
				final int h = getHeight();
		        
				int xBlip = (int)(mWidth / 2.0);
				mPath.lineTo(xBlip, 0);
		        mPath.lineTo(xBlip + 15, (int)(40.0/110.0 * h/4.0));
		        mPath.lineTo(xBlip + 30, (int)(-20.0/110.0 * h/4.0));
		        mPath.lineTo(xBlip + 50, (int)(110.0/110.0 * h/4.0));
		        mPath.lineTo(xBlip + 65, (int)(-100.0/110.0 * h/4.0));
		        mPath.lineTo(xBlip + 80, (int)(0.0/110.0 * h/4.0));
		        mPath.lineTo(mWidth, 0);
		                
		        mLineShader = new LinearGradient(0, h/2, mWidth, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP);
		    	
		        mHeadX = -OFFSCREEN;
		    	mHead = new MyPoint(-OFFSCREEN, h/2);
		    	
		    	mTranslateAnim = ObjectAnimator.ofInt(mHead, "x", -OFFSCREEN, mWidth+OFFSCREEN+(int)(mWidth*PERCENT_SCREEN));
		        mTranslateAnim.setDuration(DURATION);
		        mTranslateAnim.setInterpolator(new LinearInterpolator());
		        mTranslateAnim.setRepeatCount(ValueAnimator.INFINITE);
		        mTranslateAnim.setRepeatMode(ValueAnimator.RESTART);
		        mTranslateAnim.addUpdateListener(getListener());
		        mTranslateAnim.start();
		    } 
		});
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
        
        mHeadX = mHead.getX();
        
        int tailX = (int)(mHeadX - PERCENT_SCREEN*mWidth);
/*     
        // draw the segment of the path
        
        mSegment.reset();
        mPathMeasure.getSegment(Math.min(Math.max(tailX, 0), mWidth), Math.min(Math.max(mHeadX, 0), mWidth), mSegment, true);
        mPathMeasureSegment.setPath(mSegment, false);
        float lenSeg = mPathMeasureSegment.getLength();
        
        if(lenSeg > 0) {
        	paint.setShader(new LinearGradient(tailX, h/2, mHeadX, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));
        	canvas.drawPath(mSegment, paint);
        }
*/
        canvas.translate(0, h/4);
        
        // draw the entire path
        paint.setShader(new LinearGradient(tailX, h/2, mHeadX, h/2, 0xff000000, 0xff00ff00, Shader.TileMode.CLAMP));
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
        /*if(mTranslateAnim != null) {
        	mTranslateAnim.addUpdateListener(this);
        	mTranslateAnim.start();
        }*/
    }

    @Override
    protected void onDetachedFromWindow() {
        mAnimate = false;
        if(mTranslateAnim != null) {
        	mTranslateAnim.cancel();
        	mTranslateAnim.removeUpdateListener(this);
        }
        if (false) Log.d(TAG, "onDetachedFromWindow. mAnimate=" + mAnimate);
        super.onDetachedFromWindow();
    }

    public void onAnimationUpdate(ValueAnimator animation) {
    	invalidate();
    	//invalidate(tailX, canvasY-30, mHeadX+30, canvasY+30);
    }
    	
    private ObjectAnimator.AnimatorUpdateListener getListener() {
    	return this;
    }
    
	public class MyPoint extends Point {
		public MyPoint() {
			super(0, 0);
		}
		
		public MyPoint(int x, int y) {
			super(x, y);
		}
		
		public int getX() {
			return x;
		}
		
		public void setX(int x) {
			this.x = x;
		}
		
		public int getY() {
			return y;
		}
		
		public void setY(int y) {
			this.y = y;
		}
	}
	
}
