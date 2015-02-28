package org.usfirst.frc.team5190.robot;

public class UnsupportedSensorException extends Exception {
	private static final long serialVersionUID = 1L;

	protected String message;

	public UnsupportedSensorException() {
		message = new String();
	}

	public UnsupportedSensorException(String mess) {
		message = mess;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		return message;
	}
}
