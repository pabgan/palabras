/**
 * 
 */
package com.ganuzapps.palabras;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.SearchRecentSuggestions;
import android.widget.Toast;

/**
 * @author Pablo Ganuza
 * 
 */
public class SettingsFragment extends PreferenceFragment {
	private final String TAG = SettingsFragment.class.getSimpleName();
	private final String PREFERENCE_CLEAR_HISTORY_BUTTON = "pref_key_clear_search_history";

	public SettingsFragment() {
		super();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Load the preferences from an XML resource
		addPreferencesFromResource(R.xml.preferences);

		Preference button = (Preference) findPreference(PREFERENCE_CLEAR_HISTORY_BUTTON);

		button.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {

			@Override
			public boolean onPreferenceClick(Preference arg0) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						getActivity());
				builder.setMessage(
						R.string.pref_confirmation_clear_search_history)
						.setTitle(R.string.pref_title_clear_search_history);

				builder.setPositiveButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User clicked OK button
								SearchRecentSuggestions searchRecentSuggestions = new SearchRecentSuggestions(
										getActivity(),
										SearchSuggestionsProvider.AUTHORITY,
										SearchSuggestionsProvider.MODE);
								searchRecentSuggestions.clearHistory();

								(Toast.makeText(getActivity()
										.getApplicationContext(),
										"Search history cleared!",
										Toast.LENGTH_SHORT)).show();
							}
						});
				builder.setNegativeButton(android.R.string.cancel,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								// User cancelled the dialog
							}
						});
				AlertDialog dialog = builder.create();
				dialog.show();

				return true;
			}
		});

	}
}
