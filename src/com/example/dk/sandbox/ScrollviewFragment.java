package com.example.dk.sandbox;

import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ScrollviewFragment extends ExampleDetailFragment {

	private final static int MARGIN = 10;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		final HorizontalScrollView hsv = (HorizontalScrollView)getView().findViewById(R.id.vScrollview);
		hsv.post(new Runnable() {
		    @Override
		    public void run() {
		    	final int w = getView().getWidth();
		    	
		    	LayoutParams lpTri = new LayoutParams((int)(w*2.0/3.0) - 2*MARGIN, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		    	lpTri.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
		    	((TextView)hsv.findViewById(R.id.txtTri)).setLayoutParams(lpTri);
		    	
		    	LayoutParams lpCol = new LayoutParams((int)(w/3.0) - 2*MARGIN, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		    	lpCol.setMargins(MARGIN, MARGIN, MARGIN, MARGIN);
		    	((TextView)hsv.findViewById(R.id.txtCol1)).setLayoutParams(lpCol);
		    	((TextView)hsv.findViewById(R.id.txtCol2)).setLayoutParams(lpCol);
		    	((TextView)hsv.findViewById(R.id.txtCol3)).setLayoutParams(lpCol);
		    	
		    	hsv.scrollTo(hsv.getWidth(), 0);
		    } 
		});
	}

}
