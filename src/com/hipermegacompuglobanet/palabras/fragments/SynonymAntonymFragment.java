package com.hipermegacompuglobanet.palabras.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hipermegacompuglobanet.palabras.R;

public class SynonymAntonymFragment extends Fragment implements
		IPalabrasFragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	public static final String ARG_SECTION_NUMBER = "section_number";

	public SynonymAntonymFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main_dummy,
				container, false);
		TextView dummyTextView = (TextView) rootView
				.findViewById(R.id.section_label);
		dummyTextView.setText("Sinónimos y antónimos");
		return rootView;
	}

	@Override
	public String getTitle() {
		return "(Sin/ant)ónimos";
	}

}
