package com.ganuzapps.palabras;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.SearchRecentSuggestions;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private final String TAG = this.getClass().getSimpleName();

	private final String SEARCH_URI_DEFAULT = "http://lema.rae.es/drae/srv/search?val=";

	private WebView webView;
	private SearchView searchView;
	private ProgressBar progressBar;

	// private SearchSuggestionsProvider searchRecentSuggestionsProvider;
	private SearchRecentSuggestions searchRecentSuggestions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "onCreate()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		progressBar = (ProgressBar) findViewById(R.id.activity_main_progressBar);
		webView = (WebView) findViewById(R.id.activity_main_webView);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d(TAG, "onPageStarted()");
				searchView.setIconified(true);
				progressBar.setVisibility(View.VISIBLE);
				webView.setVisibility(View.GONE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				Log.d(TAG, "onPageFinished()");
				// Show webview and hide progress bar
				webView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		});

		webView.getSettings().setJavaScriptEnabled(true);
		searchRecentSuggestions = new SearchRecentSuggestions(this, SearchSuggestionsProvider.AUTHORITY,
				SearchSuggestionsProvider.MODE);

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
		searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

		// Assumes current activity is the searchable activity
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

		if (!searchItem.expandActionView()) {
			Log.w(TAG, "Search could not be expanded");
		}

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
		if (query != null && !query.isEmpty()) {
			searchRecentSuggestions.saveRecentQuery(query, null);
			SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
			String definitionProvider = sharedPref.getString(SettingsActivity.DEFINITION_PROVIDER, SEARCH_URI_DEFAULT);

			Log.d(TAG, "Definition provider on preferences: " + definitionProvider);

			webView.loadUrl(definitionProvider + query);
		} else {
			Log.e(TAG, "Query not cool");
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.d(TAG, "onOptionsItemSelected(item = " + item.toString());
		switch (item.getItemId()) {
			case R.id.action_search:
				(Toast.makeText(getApplicationContext(), "Search selected", Toast.LENGTH_SHORT)).show();
				break;
			case R.id.action_about:
				startActivity(new Intent(this, AboutActivity.class));
				break;
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				break;
			default:
				(Toast.makeText(getApplicationContext(), getString(R.string.general_not_implemented),
						Toast.LENGTH_SHORT)).show();
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
				case KeyEvent.KEYCODE_BACK:
					if (webView.canGoBack()) {
						webView.goBack();
						return true;
					}
			}
		}

		return super.onKeyDown(keyCode, event);
	}
}
