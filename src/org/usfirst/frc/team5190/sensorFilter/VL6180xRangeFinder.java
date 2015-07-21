package org.usfirst.frc.team5190.sensorFilter;

import java.util.TimerTask;

import org.usfirst.frc.team5190.protocol.I2CPlus;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
//import edu.wpi.first.wpilibj.Timer;

public class VL6180xRangeFinder extends AbstractVL6180xRangeFinder implements PIDSource {
	private java.util.Timer updater;

	private final int RANGE_FINDER_ADDR = 0x29;

	public VL6180xRangeFinder(Port port) {
		i2c = new I2CPlus(port, RANGE_FINDER_ADDR);

		distance = new byte[1];
		sensorStatus = new byte[1];
		isInitialized = false;
		
		updater = new java.util.Timer();

		this.getIdentification();

		boolean initSuccess = this.vl6180xInit();
		if (initSuccess) {
			this.vl6180xDefautSettings();
			float ambient = this.getAmbientLight(VL6180x_als_gain.GAIN_1);
			
			//Start the cyclic updates here
			i2c.write16bitRegister(AbstractVL6180xRangeFinder.VL6180X_SYSRANGE_START,
					0x03); // Initiate measurement
			
			System.out.println("Ambient light is: " + ambient);
			System.out.println();
			
			isInitialized = true;
		} else {
			System.out.println("VL6180X init failed!");
		}
	}

	public boolean isInitialized(){
		return isInitialized;
	}
	
	
	// Distance in cm, returns -1 if the data is not valid
	public int getDistance() {
		//TS - added double-check on data
		if(isInitialized == true){
			return Byte.toUnsignedInt(distance[0]);			
		}
		else{
			return 0;		//this allows callers to do their own check on data range
		}
	}

	@Override
	public double pidGet() {
		return getDistance();
	}

	// Start 10Hz polling
	public void start() {
		updater.scheduleAtFixedRate(new Updater(), 0,100);
	}

	// Start polling for period in milliseconds
	public void start(int period) {
		if(period < 100){
			period = 100;
		}
		updater.scheduleAtFixedRate(new Updater(), 0, period);
	}

	public void stop() {
		updater.cancel();
		updater = new java.util.Timer();
	}

	// Update distance variable
	//Alternative method, no timer delay, reads in value then kicks off next measurement
	//The data sheet has a check of the range status and interrupt status, which would flag a comm or range error better
	public void update() {
		
		int rangeStatus = 0;

		i2c.read16bitRegister(AbstractVL6180xRangeFinder.VL6180X_RESULT_INTERRUPT_STATUS_GPIO,
				1, sensorStatus); // Read status
		
		rangeStatus = sensorStatus[0] & 0x07;
		
		if (rangeStatus == 4)
		{
		   // The data is ready
			i2c.read16bitRegister(AbstractVL6180xRangeFinder.VL6180X_RESULT_RANGE_VAL,
					1, distance); // Read in	
		}
		//Clear the interrupt
		i2c.write16bitRegister(VL6180X_SYSTEM_INTERRUPT_CLEAR, 0x07);	
		
		System.out.println("Sensor Status is: " + sensorStatus);
		System.out.println();
		
		System.out.println("Distance is: " + Byte.toUnsignedInt(distance[0]));
		System.out.println();

	}
	
	//This is the original function
//	public void update() {
//		i2c.write16bitRegister(AbstractVL6180xRangeFinder.VL6180X_SYSRANGE_START,
//				0x01); // Initiate measurement
//		Timer.delay(0.10); // Delay for measurement to be taken
//		i2c.read16bitRegister(AbstractVL6180xRangeFinder.VL6180X_RESULT_RANGE_VAL,
//				1, distance); // Read in
//	}

	// Timer task to keep distance updated
	private class Updater extends TimerTask {
		@Override
		public void run() {
			update();
		}
	}
}