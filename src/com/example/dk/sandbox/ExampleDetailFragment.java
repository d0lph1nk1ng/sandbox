package com.example.dk.sandbox;

import com.example.dk.sandbox.model.ExampleContent;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Example detail screen. This fragment is
 * either contained in a {@link ExampleListActivity} in two-pane mode (on
 * tablets) or a {@link ExampleDetailActivity} on handsets.
 */
public class ExampleDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private ExampleContent.ExampleItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ExampleDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = ExampleContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView;

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			rootView = inflater.inflate(mItem.layoutId,
					container, false);
		} else {
			rootView = inflater.inflate(R.layout.fragment_example_detail,
					container, false);
			((TextView) rootView.findViewById(R.id.example_detail))
					.setText("No data");
		}

		return rootView;
	}
}
