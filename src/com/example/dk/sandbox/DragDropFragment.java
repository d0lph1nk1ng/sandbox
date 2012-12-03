package com.example.dk.sandbox;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout.LayoutParams;

public class DragDropFragment extends ExampleDetailFragment {

	private static int mAdjX;
	private static int mAdjY;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			final View view = getView();
			
			View droid = view.findViewById(R.id.droid);
			droid.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					ClipData data = ClipData.newPlainText("foo", "bar");
					DragShadowBuilder shadowBuilder = new DragShadowBuilder();
					v.startDrag(data, shadowBuilder, v, 0);
					return true;
				}
			});
			
			view.findViewById(R.id.topLeft).setOnDragListener(new BoxDragListener());
			view.findViewById(R.id.bottomLeft).setOnDragListener(new BoxDragListener());
			view.findViewById(R.id.topRight).setOnDragListener(new BoxDragListener());
			view.findViewById(R.id.bottomRight).setOnDragListener(new BoxDragListener());
			
			final View rv = view.findViewById(R.id.relative_layout);
			rv.post(new Runnable() {
			    @Override
			    public void run() {
			    	int[] quadrantCoords = new int[2];
					rv.getLocationInWindow(quadrantCoords);
					mAdjX = quadrantCoords[0];
					mAdjY = quadrantCoords[1];
			    }
			});
		}
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
					params.setMargins(quadrantCoords[0] - mAdjX, quadrantCoords[1] - mAdjY, 0, 0);
					view.setLayoutParams(params);
				}
			} else if (event.getAction() == DragEvent.ACTION_DRAG_ENDED) {
				self.setBackgroundDrawable(border);
			}
			return true;
		}
	}
	
}
