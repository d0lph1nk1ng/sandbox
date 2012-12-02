package com.example.dk.sandbox;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class DragDropActivity extends Activity {

	private static int mAdjY;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_drop);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			View droid = findViewById(R.id.droid);
			droid.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					ClipData data = ClipData.newPlainText("foo", "bar");
					DragShadowBuilder shadowBuilder = new DragShadowBuilder();
					v.startDrag(data, shadowBuilder, v, 0);
					return true;
				}
			});
			
			findViewById(R.id.topLeft).setOnDragListener(new BoxDragListener());
			findViewById(R.id.bottomLeft).setOnDragListener(new BoxDragListener());
			findViewById(R.id.topRight).setOnDragListener(new BoxDragListener());
			findViewById(R.id.bottomRight).setOnDragListener(new BoxDragListener());
			
			final View rv = findViewById(R.id.relative_layout);
			rv.post(new Runnable() {
			    @Override
			    public void run() {
			    	int[] quadrantCoords = new int[2];
					rv.getLocationInWindow(quadrantCoords);
					mAdjY = quadrantCoords[1];
			    }
			});
			
			// Show the Up button in the action bar.		
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_drag_drop, menu);
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
	
	class BoxDragListener implements OnDragListener {
		boolean insideOfMe = false;
		Drawable border = null;
		Drawable redBorder = getResources().getDrawable(R.drawable.border3);

		@Override
		public boolean onDrag(View self, DragEvent event) {
			if (event.getAction() == DragEvent.ACTION_DRAG_STARTED) {
				border = self.getBackground();
				self.setBackgroundDrawable(redBorder);
			} else if (event.getAction() == DragEvent.ACTION_DRAG_ENTERED) {
				insideOfMe = true;
			} else if (event.getAction() == DragEvent.ACTION_DRAG_EXITED) {
				insideOfMe = false;
			} else if (event.getAction() == DragEvent.ACTION_DROP) {
				if (insideOfMe) {
					View view = (View) event.getLocalState();
					
					int[] quadrantCoords = new int[2];
					self.getLocationInWindow(quadrantCoords);
					
					LayoutParams params = new LayoutParams(view.getWidth(), view.getHeight());
					params.setMargins(quadrantCoords[0], quadrantCoords[1] - mAdjY, 0, 0);
					view.setLayoutParams(params);
				}
			} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
				self.setBackgroundDrawable(border);
			}
			return true;
		}
	}
	
}
