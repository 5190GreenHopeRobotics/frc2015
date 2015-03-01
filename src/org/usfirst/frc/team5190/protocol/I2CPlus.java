package org.usfirst.frc.team5190.protocol;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.hal.I2CJNI;
import edu.wpi.first.wpilibj.util.BoundaryException;

public class I2CPlus extends I2C {

	private Port port;
	private int deviceAddress;

	public I2CPlus(Port port, int deviceAddress) {
		super(port, deviceAddress);
		this.port = port;
		this.deviceAddress = deviceAddress;
	}

	public synchronized boolean write16bitRegister(int registerAddress, int data) {
		byte[] buffer = new byte[3];
		buffer[0] = (byte) ((registerAddress >> 8) & 0xFF);
		buffer[1] = (byte) (registerAddress & 0xFF);
		buffer[2] = (byte) data;

		ByteBuffer dataToSendBuffer = ByteBuffer.allocateDirect(3);
		dataToSendBuffer.put(buffer);

		return I2CJNI.i2CWrite((byte) port.getValue(), (byte) deviceAddress,
				dataToSendBuffer, (byte) buffer.length) < 0;
	}
	
	public synchronized boolean write16bitRegisterAnd16bitData(int registerAddress, int data) {
		byte[] buffer = new byte[4];
		buffer[0] = (byte) ((registerAddress >> 8) & 0xFF);
		buffer[1] = (byte) (registerAddress & 0xFF);
		buffer[2] = (byte) ((data >> 8) & 0xFF);
		buffer[3] = (byte) (data & 0xFF);

		ByteBuffer dataToSendBuffer = ByteBuffer.allocateDirect(4);
		dataToSendBuffer.put(buffer);

		return I2CJNI.i2CWrite((byte) port.getValue(), (byte) deviceAddress,
				dataToSendBuffer, (byte) buffer.length) < 0;
	}
	
	public boolean read16bitRegister(int registerAddress, int count, byte[] buffer) {
		BoundaryException.assertWithinBounds(count, 1, 7);
		if (buffer == null) {
			throw new NullPointerException("Null return buffer was given");
		}
		byte[] registerAddressArray = new byte[2];
		registerAddressArray[0] = (byte) ((registerAddress >> 8) & 0xFF);
		registerAddressArray[1] = (byte) (registerAddress & 0xFF);

		return transaction(registerAddressArray, registerAddressArray.length,
				buffer, count);
	}

}
