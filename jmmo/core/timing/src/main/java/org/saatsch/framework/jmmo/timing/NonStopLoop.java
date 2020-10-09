package org.saatsch.framework.jmmo.timing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the entry point to the (game) loop API. To start a loop, use
 * 
 * <pre>
 * NonStopLoop app = new NonStopLoop();
 * app.start();
 * </pre>
 * 
 * to use configuration other than the default call
 * 
 * <pre>
 * {@link #start(boolean, String, int, boolean)}
 * </pre>
 * 
 * A loop is started on a separate thread. You can configure the loop to run on
 * a daemon thread. Default is non-daemon. When non-daemon, it prevents the JVM
 * from stopping. Keep in mind that the JVM will <b>always</b> stop when running
 * this code in a JUnit test, because JUnit calls {@link System#exit(int)} when
 * the main thread ends.
 * 
 * The loop can also be configured with a frame rate. Default is 1. This means 1
 * "frame" per second. Each "frame" {@link #update()} is called and you can
 * extend this class and override {@link #update()} if you want the chance to
 * execute some code every frame. You can also change the frame rate while the
 * loop is running. See {@link #getContext()}.
 * 
 * If you override {@link #update()}, you may also want to override
 * {@link #handleError(String, Throwable)}. This gets called when an uncaught
 * exception occurs on the thread (= the thread that calls your overridden
 * {@link #update()} method). The default behavior is to stop the loop on an
 * uncaught exception.
 * 
 * This code was extracted from JMonkeyEngine and then adapted. It should thus
 * be production ready.
 * 
 * @author saatsch
 * 
 */
public class NonStopLoop implements SystemListener {

	private static final Logger LOG = LoggerFactory.getLogger(NonStopLoop.class);

	protected NonStopContext context;

	public NonStopLoop() {

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize() {
	  //
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
	  //
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update() {
	  //
	}

	/**
	 * starts the loop on a separate thread, waiting until the separate thread
	 * is fully operational. Starts with a frame rate of 1 in non-daemon mode.
	 * 
	 * @see #start(boolean, String, int, boolean)
	 */
	public void start() {
		start(true, null, 1, false);
	}

	/**
	 * Starts the non stop loop on a separate thread. Note that this method does
	 * not block.
	 * 
	 * @param waitFor
	 *            if it should wait until the separate thread is fully
	 *            operational.
	 * @param threadName
	 *            the desired name of the separate thread that will be created
	 *            or <code>null</code> for the default name.
	 * @param frameRate
	 *            times per second the {@link #update()} method will be called.
	 * @param daemon
	 *            if it should run as daemon thread.
	 */
	public void start(boolean waitFor, String threadName, int frameRate, boolean daemon) {
		if (context != null && context.isCreated()) {
			LOG.warn("start() called when application already created!");
			return;
		}

		if (LOG.isInfoEnabled()) {
			LOG.info("Starting loop: " + getClass().getName());
		}

		context = new NonStopContext();
		context.setSystemListener(this);
		context.create(waitFor, threadName, frameRate, daemon);
	}

	@Override
	public void handleError(final String errMsg, final Throwable t) {
		LOG.error(errMsg, t);
		// user should add additional code to handle the error.
		stop(); // stop the loop

	}

	/**
	 * Requests the context to close, shutting down the main loop and making
	 * necessary cleanup operations.
	 * 
	 * Same as calling stop(false)
	 * 
	 * @see #stop(boolean)
	 */
	public void stop() {
		stop(false);
	}

	/**
	 * Requests the context to close, shutting down the main loop and making
	 * necessary cleanup operations. After the application has stopped, it
	 * cannot be used anymore.
	 */
	public void stop(final boolean waitFor) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("Closing application: {0}" + getClass().getName());
		}
		context.destroy(waitFor);
	}

	/**
	 * @return The {@link NonStopContext context} for the application
	 */
	public NonStopContext getContext() {
		return context;
	}

}
