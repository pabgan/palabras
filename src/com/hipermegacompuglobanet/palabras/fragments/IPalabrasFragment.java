package com.hipermegacompuglobanet.palabras.fragments;

import android.os.Bundle;

public interface IPalabrasFragment {
	public static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * @return title of the fragment
	 */
	public String getTitle();

	public void setArguments(Bundle args);

}
