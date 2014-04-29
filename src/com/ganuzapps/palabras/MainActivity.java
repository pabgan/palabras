package com.ganuzapps.palabras;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
	private final String TAG = MainActivity.class.getSimpleName();

	private final String SEARCH_URI_RAE = "http://lema.rae.es/drae/srv/search?val=";

	private WebView webView;
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
				progressBar.setVisibility(View.VISIBLE);
				webView.setVisibility(View.GONE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// Show webview and hide progress bar
				webView.setVisibility(View.VISIBLE);
				progressBar.setVisibility(View.GONE);
			}
		});

		webView.getSettings().setJavaScriptEnabled(true);
		searchRecentSuggestions = new SearchRecentSuggestions(this,
				SearchSuggestionsProvider.AUTHORITY,
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
		if (query != null && !query.isEmpty()) {
			searchRecentSuggestions.saveRecentQuery(query, null);
			webView.loadUrl(SEARCH_URI_RAE + query);
		} else {
			Log.e(TAG, "Query not cool");
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
			Intent intent = new Intent(this, AboutActivity.class);
			startActivity(intent);
			break;
		case R.id.action_settings:
		default:
			(Toast.makeText(getApplicationContext(),
					getString(R.string.not_implemented), Toast.LENGTH_SHORT))
					.show();
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
