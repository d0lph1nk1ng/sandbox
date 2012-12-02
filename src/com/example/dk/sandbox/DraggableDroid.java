package com.example.dk.sandbox;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.widget.ImageView;

public class DraggableDroid extends ImageView {
	
	private final static String TAG = "DND";
	private boolean mDragInProgress;
	
	public DraggableDroid(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
     * Drag and drop
     */
    @Override
    public boolean onDragEvent(DragEvent event) {
        boolean result = false;
        switch (event.getAction()) {
	        case DragEvent.ACTION_DRAG_STARTED: {
	            // claim to accept any dragged content
	            Log.i(TAG, "Drag started, event=" + event);
	            // cache whether we accept the drag to return for LOCATION events
	            mDragInProgress = true;
	        } break;
	
	        case DragEvent.ACTION_DRAG_ENDED: {
	            Log.i(TAG, "Drag ended.");
	            mDragInProgress = false;
	        } break;
	
	        case DragEvent.ACTION_DRAG_LOCATION: {
	            // we returned true to DRAG_STARTED, so return true here
	            Log.i(TAG, "... seeing drag locations ...");
	            result = true;
	        } break;
	
	        case DragEvent.ACTION_DROP: {
	            Log.i(TAG, "Got a drop! dot=" + this + " event=" + event);
	            result = true;
	        } break;
	
	        case DragEvent.ACTION_DRAG_ENTERED: {
	            Log.i(TAG, "Entered dot @ " + this);
	            invalidate();
	        } break;
	
	        case DragEvent.ACTION_DRAG_EXITED: {
	            Log.i(TAG, "Exited dot @ " + this);
	            invalidate();
	        } break;
	
	        default:
	            Log.i(TAG, "other drag event: " + event);
	            result = true;
	            break;
	    }
	
	    return result;
    }
}
