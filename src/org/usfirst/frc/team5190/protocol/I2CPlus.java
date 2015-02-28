package org.usfirst.frc.team5190.protocol;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.hal.I2CJNI;

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

}
