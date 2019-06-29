package com.darklycoder.xskin.core.listener;

import android.content.Context;

public interface ISkinChecker {
	
	/**
	 * Check whether the skin is exits or legal
	 */
	boolean isSkinPackageLegality(Context context, String path);
}
