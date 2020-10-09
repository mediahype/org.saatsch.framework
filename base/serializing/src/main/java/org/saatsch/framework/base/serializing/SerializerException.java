package org.saatsch.framework.base.serializing;

public class SerializerException extends Exception {

	/**
	 * Comment for
	 */
	private static final long serialVersionUID = 6465089178205997564L;

	public SerializerException() {
		super();
	}

	public SerializerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SerializerException(String message, Throwable cause) {
		super(message, cause);
	}

	public SerializerException(String message) {
		super(message);
	}

	public SerializerException(Throwable cause) {
		super(cause);
	}

}
