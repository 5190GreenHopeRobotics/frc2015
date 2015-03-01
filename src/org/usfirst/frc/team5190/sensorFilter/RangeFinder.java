package org.usfirst.frc.team5190.sensorFilter;

import java.util.TimerTask;

import org.usfirst.frc.team5190.protocol.I2CPlus;

import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Timer;

public class RangeFinder implements PIDSource{
	private I2CPlus i2c;
	private byte[] distance;
	private java.util.Timer updater;

	private final int RANGE_FINDER_ADDR = 0x29;
	
	public RangeFinder(Port port) {
		i2c = new I2CPlus(port, RANGE_FINDER_ADDR);

		boolean devicePresent = i2c.addressOnly();
		if (!devicePresent) {
			System.out.println("VL6180X device not present!");
		}
		distance = new byte[1];

		updater = new java.util.Timer();
		
		this.getIdentification();
		
		boolean initSuccess = this.vl6180xInit();
		if (initSuccess) {
			this.vl6180xDefautSettings();
		}else{
			System.out.println("VL6180X init failed!");
		}
		
	}
	
	public boolean vl6180xInit() {
		  byte[] data = new byte[1]; //for temp data storage

		  boolean status = i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSTEM_FRESH_OUT_OF_RESET, 1, data);

		  if(!status) {
			  return status;
		  }

		  //Required by datasheet
		  //http://www.st.com/st-web-ui/static/active/en/resource/technical/document/application_note/DM00122600.pdf
		  i2c.write16bitRegister(0x0207, 0x01);
		  i2c.write16bitRegister(0x0208, 0x01);
		  i2c.write16bitRegister(0x0096, 0x00);
		  i2c.write16bitRegister(0x0097, 0xfd);
		  i2c.write16bitRegister(0x00e3, 0x00);
		  i2c.write16bitRegister(0x00e4, 0x04);
		  i2c.write16bitRegister(0x00e5, 0x02);
		  i2c.write16bitRegister(0x00e6, 0x01);
		  i2c.write16bitRegister(0x00e7, 0x03);
		  i2c.write16bitRegister(0x00f5, 0x02);
		  i2c.write16bitRegister(0x00d9, 0x05);
		  i2c.write16bitRegister(0x00db, 0xce);
		  i2c.write16bitRegister(0x00dc, 0x03);
		  i2c.write16bitRegister(0x00dd, 0xf8);
		  i2c.write16bitRegister(0x009f, 0x00);
		  i2c.write16bitRegister(0x00a3, 0x3c);
		  i2c.write16bitRegister(0x00b7, 0x00);
		  i2c.write16bitRegister(0x00bb, 0x3c);
		  i2c.write16bitRegister(0x00b2, 0x09);
		  i2c.write16bitRegister(0x00ca, 0x09);  
		  i2c.write16bitRegister(0x0198, 0x01);
		  i2c.write16bitRegister(0x01b0, 0x17);
		  i2c.write16bitRegister(0x01ad, 0x00);
		  i2c.write16bitRegister(0x00ff, 0x05);
		  i2c.write16bitRegister(0x0100, 0x05);
		  i2c.write16bitRegister(0x0199, 0x05);
		  i2c.write16bitRegister(0x01a6, 0x1b);
		  i2c.write16bitRegister(0x01ac, 0x3e);
		  i2c.write16bitRegister(0x01a7, 0x1f);
		  i2c.write16bitRegister(0x0030, 0x00);
		  
		  return true;

	}
	
	public void vl6180xDefautSettings() {
		  //Recommended settings from datasheet
		  //http://www.st.com/st-web-ui/static/active/en/resource/technical/document/application_note/DM00122600.pdf

		  //Enable Interrupts on Conversion Complete (any source)
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO, (4 << 3)|(4) ); // Set GPIO1 high when sample complete


		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSTEM_MODE_GPIO1, 0x10); // Set GPIO1 high when sample complete
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD, 0x30); //Set Avg sample period
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSALS_ANALOGUE_GAIN, 0x46); // Set the ALS gain
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_VHV_REPEAT_RATE, 0xFF); // Set auto calibration period (Max = 255)/(OFF = 0)
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSALS_INTEGRATION_PERIOD, 0x63); // Set ALS integration time to 100ms
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_VHV_RECALIBRATE, 0x01); // perform a single temperature calibration
		  //Optional settings from datasheet
		  //http://www.st.com/st-web-ui/static/active/en/resource/technical/document/application_note/DM00122600.pdf
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_INTERMEASUREMENT_PERIOD, 0x09); // Set default ranging inter-measurement period to 100ms
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSALS_INTERMEASUREMENT_PERIOD, 0x0A); // Set default ALS inter-measurement period to 100ms
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO, 0x24); // Configures interrupt on ‘New Sample Ready threshold event’ 
		  //Additional settings defaults from community
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_MAX_CONVERGENCE_TIME, 0x32);
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_RANGE_CHECK_ENABLES, 0x10 | 0x01);
		  i2c.write16bitRegisterAnd16bitData(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_EARLY_CONVERGENCE_ESTIMATE, 0x7B );
		  i2c.write16bitRegisterAnd16bitData(VL6180xRangeFinderIntf.VL6180X_SYSALS_INTEGRATION_PERIOD, 0x64);

		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD,0x30);
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSALS_ANALOGUE_GAIN,0x40);
		  i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_FIRMWARE_RESULT_SCALER,0x01);
	}
	
	public void getIdentification(){
		byte[] buffer = new byte[1];
		boolean status;
		status =  i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_IDENTIFICATION_MODEL_ID, 1, buffer);
		System.out.println("idModel: " + Byte.toUnsignedInt(buffer[0]));
		buffer = new byte[1];
		status = i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_IDENTIFICATION_MODEL_REV_MAJOR, 1, buffer);
		System.out.println("idModelRevMajor: " + Byte.toUnsignedInt(buffer[0]));
		buffer = new byte[1];
		status = i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_IDENTIFICATION_MODEL_REV_MINOR, 1, buffer);
		System.out.println("idModelRevMinor: " + Byte.toUnsignedInt(buffer[0]));
		buffer = new byte[1];
		status = i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_IDENTIFICATION_MODULE_REV_MAJOR, 1, buffer);
		System.out.println("idModuleRevMajor: " + Byte.toUnsignedInt(buffer[0]));
		buffer = new byte[1];
		status = i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_IDENTIFICATION_MODULE_REV_MINOR, 1, buffer);
		System.out.println("idModuleRevMinor: " + Byte.toUnsignedInt(buffer[0]));
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
		i2c.write16bitRegister(VL6180xRangeFinderIntf.VL6180X_SYSRANGE_START, 0x01); // Initiate measurement
		Timer.delay(0.10); // Delay for measurement to be taken
		i2c.read16bitRegister(VL6180xRangeFinderIntf.VL6180X_RESULT_RANGE_VAL, 1, distance); // Read in
	}
	

	// Timer task to keep distance updated
	private class Updater extends TimerTask {
		@Override
		public void run() {
			update();
		}
	}
}