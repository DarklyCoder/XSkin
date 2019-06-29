package com.darklycoder.xskin.core.listener;

public interface ISkinLoader {

	void attach(ISkinUpdate observer);

	void detach(ISkinUpdate observer);

	void notifySkinUpdate();
}
