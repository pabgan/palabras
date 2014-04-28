package com.hipermegacompuglobanet.palabras;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get the intent, verify the action and get the query
		Intent intent = getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d(TAG, "onCreate().Searching: " + query);
			doSearch(query);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);

		// Get the SearchView and set the searchable configuration
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
				.getActionView();

		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager
				.getSearchableInfo(getComponentName()));

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		Log.d(TAG, "onNewIntent()");
		setIntent(intent);
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			Log.d(TAG, "onNewIntent().Searching: " + query);

			doSearch(query);
		}
	}

	private void doSearch(String query) {
		WebView myWebView = (WebView) findViewById(R.id.activity_main_webView);
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		ProgressBar pb = (ProgressBar) findViewById(R.id.activity_main_progressBar);

		if (query != null && !query.isEmpty()) {
			myWebView.setVisibility(View.GONE);
			pb.setVisibility(View.VISIBLE);
			myWebView
					.loadUrl("http://lema.rae.es/drae/srv/search?val=" + query);
			myWebView.setVisibility(View.VISIBLE);
			pb.setVisibility(View.GONE);
		} else {
			Log.e(TAG, "query not cool");
		}

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(item = " + item.toString());
		switch (item.getItemId()) {
		case R.id.action_search:
			(Toast.makeText(getApplicationContext(), "Search selected",
					Toast.LENGTH_SHORT)).show();
			break;
		case R.id.action_about:
		case R.id.action_settings:
		default:
			(Toast.makeText(getApplicationContext(), "Not implemented",
					Toast.LENGTH_SHORT)).show();
			break;
		}

		return super.onOptionsItemSelected(item);
	}
}
