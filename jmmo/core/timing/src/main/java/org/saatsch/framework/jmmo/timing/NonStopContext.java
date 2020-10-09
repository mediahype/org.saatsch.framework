package org.saatsch.framework.jmmo.timing;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NonStopContext implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(NonStopContext.class);
	private SystemListener listener;
	private final AtomicBoolean created = new AtomicBoolean(false);
	private final AtomicBoolean needClose = new AtomicBoolean(false);
	private final Object createdLock = new Object();
	private int frameRate = 1;
	private Timer timer;
	private Thread thread;

	void setSystemListener(final SystemListener listener) {
		this.listener = listener;
	}

	public boolean isCreated() {
		return created.get();
	}

	public void create(final boolean waitFor, String threadName, int f, boolean daemon) {
		if (created.get()) {
			LOG.warn("create() called when Context is already created!");
			return;
		}

		if (null == threadName) {
			threadName = "NonStop Context Thread";
		}

		this.frameRate = f;

		thread = new Thread(this, threadName);
		thread.setDaemon(daemon);
		thread.start();
		if (waitFor)
			waitFor(true);

	}

	protected void waitFor(final boolean createdVal) {
		synchronized (createdLock) {
			while (created.get() != createdVal) {
				try {
					createdLock.wait();
				} catch (final InterruptedException ex) {
				}
			}
		}
	}

	public void destroy(final boolean waitFor) {
		needClose.set(true);
		if (waitFor)
			waitFor(false);

	}

	@Override
	public void run() {
		initInThread();

		while (!needClose.get()) {
			listener.update();

			if (frameRate > 0)
				sync(frameRate);
		}

		deinitInThread();

		LOG.warn("Context destroyed.");

	}

	protected void initInThread() {
		LOG.debug("Running on thread: " + Thread.currentThread().getName());

		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(final Thread thread, final Throwable thrown) {
				listener.handleError("Uncaught exception thrown in " + thread.toString(), thrown);
			}
		});

		timer = new NanoTimer();
		synchronized (createdLock) {
			created.set(true);
			createdLock.notifyAll();
		}

		listener.initialize();
	}

	private static long timeThen;
	private static long timeLate;

	private void sync(final int fps) {
		long timeNow;
		long gapTo;
		long savedTimeLate;

		gapTo = timer.getResolution() / fps + timeThen;
		timeNow = timer.getTime();
		savedTimeLate = timeLate;

		try {
			while (gapTo > timeNow + savedTimeLate) {
				Thread.sleep(1);
				timeNow = timer.getTime();
			}
		} catch (final InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		if (gapTo < timeNow) {
			timeLate = timeNow - gapTo;
		} else {
			timeLate = 0;
		}

		timeThen = timeNow;
	}

	private void deinitInThread() {
		listener.destroy();
		timer = null;
		synchronized (createdLock) {
			created.set(false);
			createdLock.notifyAll();
		}
	}

	public void setFrameRate(int frameRate) {
		this.frameRate = frameRate;
	}
}
