package org.usfirst.frc.team5190.sensorFilter;

import java.util.TimerTask;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class RangeFinder implements PIDSource{
	private I2C i2c;
	private byte[] distance;
	private java.util.Timer updater;

	private final int RANGE_FINDER_ADDR = 0x29;
	private final int VL6180X_SYSRANGE_START = 0x0018;
	private final int VL6180X_RESULT_RANGE_VAL = 0x0062;

	public RangeFinder(Port port) {
		i2c = new I2C(port, RANGE_FINDER_ADDR);

		distance = new byte[1];

		updater = new java.util.Timer();
		
	}

	// Distance in cm
	public int getDistance() {
		return Byte.toUnsignedInt(distance[0]);
	}

	@Override
	public double pidGet() {
		return getDistance();
	}

	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(new Updater(), 0, 100);
	}

	// Start polling for period in milliseconds
	public void start(int period) {
		updater.scheduleAtFixedRate(new Updater(), 0, period);
	}

	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}

	// Update distance variable
	public void update() {
		boolean devicePresent = i2c.addressOnly();
		i2c.write((registerAddr >> 8) & 0xFF); //MSB of register address
		i2c.write(registerAddr & 0xFF); //LSB of register address
		
		i2c.write(VL6180X_SYSRANGE_START, 0x01); // Initiate measurement
		Timer.delay(0.10); // Delay for measurement to be taken
		i2c.read(VL6180X_RESULT_RANGE_VAL, 1, distance); // Read in
														// measurement
		
		  Wire.write((registerAddr >> 8) & 0xFF); //MSB of register address
		  Wire.write(registerAddr & 0xFF); //LSB of register address
		  Wire.endTransmission(false); //Send address and register address bytes
		  Wire.requestFrom( _i2caddress , 1);
		  data = Wire.read(); //Read Data from selected register

		  
	}
	
	public void VL6180x_setRegister(uint16_t registerAddr, uint8_t data)
	{

	  Wire.beginTransmission( _i2caddress ); // Address set on class instantiation
	  Wire.write((registerAddr >> 8) & 0xFF); //MSB of register address
	  Wire.write(registerAddr & 0xFF); //LSB of register address
	  Wire.write(data); // Data/setting to be sent to device.
	  Wire.endTransmission(); //Send address and register address bytes
	}

	// Timer task to keep distance updated
	private class Updater extends TimerTask {
		@Override
		public void run() {
			update();
		}
	}
}