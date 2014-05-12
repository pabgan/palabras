package com.ganuzapps.palabras;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Pablo Ganuza
 * 
 */
public class SettingsActivity extends Activity {
	private final String TAG = SettingsActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the fragment as the main content.
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

}
