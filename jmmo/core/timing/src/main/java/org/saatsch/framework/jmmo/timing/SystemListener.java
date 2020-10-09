package org.saatsch.framework.jmmo.timing;

/**
 * @author saatsch
 * 
 */
public interface SystemListener {

	/**
	 * Callback to indicate the application to initialize.
	 */

	public void initialize();

	/**
	 * Callback to indicate that the context has been destroyed.
	 */
	public void destroy();

	/**
	 * Callback to update application state.
	 */
	public void update();

	/**
	 * Called when an error has occurred. This is typically invoked when an
	 * uncaught exception is thrown in the render thread.
	 * 
	 * @param errorMsg
	 *            The error message, if any, or null.
	 * @param t
	 *            Throwable object, or null.
	 */
	public void handleError(String string, Throwable thrown);

}
