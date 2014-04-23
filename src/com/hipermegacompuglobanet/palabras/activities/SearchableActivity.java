package com.hipermegacompuglobanet.palabras.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * This activity has been created only to debug the global search feature. It
 * should be deleted as soon as search is working in MainActivity
 * 
 * @author pablo
 * 
 */
public class SearchableActivity extends Activity {
	private static String TAG = MainActivity.class.getName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();

		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d(TAG, "searching: " + query);
			doMySearch(query);
		}
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent()");
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			// doMySearch(query);
			Log.d(TAG, "searching: " + query);
		} else {
			Log.w(TAG, "Intent type not supported:" + intent.getAction());
		}
	}

	/**
	 * Performs a search and passes the results to the container Activity that
	 * holds your Fragments.
	 */
	public void doMySearch(String query) {
		// TODO: implement this
	}
}