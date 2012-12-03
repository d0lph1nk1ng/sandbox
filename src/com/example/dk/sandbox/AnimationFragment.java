package com.example.dk.sandbox;

import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AnimationFragment extends ExampleDetailFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		((Button)getView().findViewById(R.id.btnStart)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startAnimation();			
			}
		});
	}
		
	private void startAnimation() {
		ImageView spaceshipImage = (ImageView)getView().findViewById(R.id.imgDroid);
		//Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(this, R.anim.hyperspace_jump);
		//spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			try {
				ObjectAnimator anim = (ObjectAnimator)AnimatorInflater.loadAnimator(getActivity(), R.anim.flipping); 
				anim.setTarget(spaceshipImage);
				anim.setDuration(3000);
				anim.start();
			} catch(Exception e) {
				;
			}
		}
	}
	
}
