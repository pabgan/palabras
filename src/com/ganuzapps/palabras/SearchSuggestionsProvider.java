package com.ganuzapps.palabras;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionsProvider extends SearchRecentSuggestionsProvider {
	public final static String AUTHORITY = SearchSuggestionsProvider.class.getName(); // "com.ganuzapps.palabras.SearchSuggestionsProvider";
	public final static int MODE = DATABASE_MODE_QUERIES;

	public SearchSuggestionsProvider() {
		setupSuggestions(AUTHORITY, MODE);
	}

	public SearchSuggestionsProvider(MainActivity mainActivity,
			String authority2, int mode2) {
		super();
	}
}
