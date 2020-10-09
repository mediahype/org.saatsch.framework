package org.saatsch.framework.jmmo.timing;

/**
 * <code>NanoTimer</code> is a System.nanoTime implementation of
 * <code>Timer</code>. This is primarily useful for headless applications
 * running on a server.
 * 
 * @author Matthew D. Hicks
 */
public class NanoTimer extends Timer {

	private static final long TIMER_RESOLUTION = 1000000000L;
	private static final float INVERSE_TIMER_RESOLUTION = 1f / 1000000000L;

	private long startTime;
	private long previousTime;
	private float tpf;
	private float fps;

	public NanoTimer() {
		startTime = System.nanoTime();
	}

	/**
	 * Returns the time in seconds. The timer starts at 0.0 seconds.
	 * 
	 * @return the current time in seconds
	 */
	@Override
	public float getTimeInSeconds() {
		return getTime() * INVERSE_TIMER_RESOLUTION;
	}

	@Override
	public long getTime() {
		return System.nanoTime() - startTime;
	}

	@Override
	public long getResolution() {
		return TIMER_RESOLUTION;
	}

	@Override
	public float getFrameRate() {
		return fps;
	}

	@Override
	public float getTimePerFrame() {
		return tpf;
	}

	@Override
	public void update() {
		tpf = (getTime() - previousTime) * (1.0f / TIMER_RESOLUTION);
		fps = 1.0f / tpf;
		previousTime = getTime();
	}

	@Override
	public void reset() {
		startTime = System.nanoTime();
		previousTime = getTime();
	}
}
