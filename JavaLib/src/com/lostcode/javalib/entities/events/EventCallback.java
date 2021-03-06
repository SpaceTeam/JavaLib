package com.lostcode.javalib.entities.events;

import com.lostcode.javalib.entities.Entity;

/**
 * Interface for any event callback. EventCallbacks must subscribe to an
 * {@link EventHandler} that will invoke them when necessary.
 * 
 * @author Natman64
 * 
 */
public interface EventCallback {

	/**
	 * Called when the event that this callback subscribed to is invoked.
	 * 
	 * @param e
	 *            The Entity that triggered this event.
	 * @param args
	 *            Miscellaneous arguments for the event.
	 */
	public void invoke(Entity e, Object... args);

}
