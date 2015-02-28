package org.usfirst.frc.team5190.sensorFilter;

public interface VL6180xRangeFinderIntf {
	public static final int VL6180x_FAILURE_RESET = -1;

	public static final int  VL6180X_IDENTIFICATION_MODEL_ID             = 0x0000;
	public static final int  VL6180X_IDENTIFICATION_MODEL_REV_MAJOR      = 0x0001;
	public static final int  VL6180X_IDENTIFICATION_MODEL_REV_MINOR      = 0x0002;
	public static final int  VL6180X_IDENTIFICATION_MODULE_REV_MAJOR     = 0x0003;
	public static final int  VL6180X_IDENTIFICATION_MODULE_REV_MINOR     = 0x0004;
	public static final int  VL6180X_IDENTIFICATION_DATE                 = 0x0006; //16bit value
	public static final int  VL6180X_IDENTIFICATION_TIME                 = 0x0008; //16bit value

	public static final int  VL6180X_SYSTEM_MODE_GPIO0                   = 0x0010;
	public static final int  VL6180X_SYSTEM_MODE_GPIO1                   = 0x0011;
	public static final int  VL6180X_SYSTEM_HISTORY_CTRL                 = 0x0012;
	public static final int  VL6180X_SYSTEM_INTERRUPT_CONFIG_GPIO        = 0x0014;
	public static final int  VL6180X_SYSTEM_INTERRUPT_CLEAR              = 0x0015;
	public static final int  VL6180X_SYSTEM_FRESH_OUT_OF_RESET           = 0x0016;
	public static final int  VL6180X_SYSTEM_GROUPED_PARAMETER_HOLD       = 0x0017;

	public static final int  VL6180X_SYSRANGE_START                      = 0x0018;
	public static final int  VL6180X_SYSRANGE_THRESH_HIGH                = 0x0019;
	public static final int  VL6180X_SYSRANGE_THRESH_LOW                 = 0x001A;
	public static final int  VL6180X_SYSRANGE_INTERMEASUREMENT_PERIOD    = 0x001B;
	public static final int  VL6180X_SYSRANGE_MAX_CONVERGENCE_TIME       = 0x001C;
	public static final int  VL6180X_SYSRANGE_CROSSTALK_COMPENSATION_RATE= 0x001E;
	public static final int  VL6180X_SYSRANGE_CROSSTALK_VALID_HEIGHT     = 0x0021;
	public static final int  VL6180X_SYSRANGE_EARLY_CONVERGENCE_ESTIMATE = 0x0022;
	public static final int  VL6180X_SYSRANGE_PART_TO_PART_RANGE_OFFSET  = 0x0024;
	public static final int  VL6180X_SYSRANGE_RANGE_IGNORE_VALID_HEIGHT  = 0x0025;
	public static final int  VL6180X_SYSRANGE_RANGE_IGNORE_THRESHOLD     = 0x0026;
	public static final int  VL6180X_SYSRANGE_MAX_AMBIENT_LEVEL_MULT     = 0x002C;
	public static final int  VL6180X_SYSRANGE_RANGE_CHECK_ENABLES        = 0x002D;
	public static final int  VL6180X_SYSRANGE_VHV_RECALIBRATE            = 0x002E;
	public static final int  VL6180X_SYSRANGE_VHV_REPEAT_RATE            = 0x0031;

	public static final int  VL6180X_SYSALS_START                        = 0x0038;
	public static final int  VL6180X_SYSALS_THRESH_HIGH                  = 0x003A;
	public static final int  VL6180X_SYSALS_THRESH_LOW                   = 0x003C;
	public static final int  VL6180X_SYSALS_INTERMEASUREMENT_PERIOD      = 0x003E;
	public static final int  VL6180X_SYSALS_ANALOGUE_GAIN                = 0x003F;
	public static final int  VL6180X_SYSALS_INTEGRATION_PERIOD           = 0x0040;

	public static final int  VL6180X_RESULT_RANGE_STATUS                 = 0x004D;
	public static final int  VL6180X_RESULT_ALS_STATUS                   = 0x004E;
	public static final int  VL6180X_RESULT_INTERRUPT_STATUS_GPIO        = 0x004F;
	public static final int  VL6180X_RESULT_ALS_VAL                      = 0x0050;
	public static final int  VL6180X_RESULT_HISTORY_BUFFER               = 0x0052;
	public static final int  VL6180X_RESULT_RANGE_VAL                    = 0x0062;
	public static final int  VL6180X_RESULT_RANGE_RAW                    = 0x0064;
	public static final int  VL6180X_RESULT_RANGE_RETURN_RATE            = 0x0066;
	public static final int  VL6180X_RESULT_RANGE_REFERENCE_RATE         = 0x0068;
	public static final int  VL6180X_RESULT_RANGE_RETURN_SIGNAL_COUNT    = 0x006C;
	public static final int  VL6180X_RESULT_RANGE_REFERENCE_SIGNAL_COUNT = 0x0070;
	public static final int  VL6180X_RESULT_RANGE_RETURN_AMB_COUNT       = 0x0074;
	public static final int  VL6180X_RESULT_RANGE_REFERENCE_AMB_COUNT    = 0x0078;
	public static final int  VL6180X_RESULT_RANGE_RETURN_CONV_TIME       = 0x007C;
	public static final int  VL6180X_RESULT_RANGE_REFERENCE_CONV_TIME    = 0x0080;

	public static final int  VL6180X_READOUT_AVERAGING_SAMPLE_PERIOD     = 0x010A;
	public static final int  VL6180X_FIRMWARE_BOOTUP                     = 0x0119;
	public static final int  VL6180X_FIRMWARE_RESULT_SCALER              = 0x0120;
	public static final int  VL6180X_I2C_SLAVE_DEVICE_ADDRESS            = 0x0212;
	public static final int  VL6180X_INTERLEAVED_MODE_ENABLE             = 0x02A3;
	

	//Send manditory settings as stated in ST datasheet.
	// http://www.st.com/st-web-ui/static/active/en/resource/technical/document/application_note/DM00122600.pdf (Section 1.3)
	public int VL6180xInit();
	
	// Use default settings from ST data sheet section 9.
	// http://www.st.com/st-web-ui/static/active/en/resource/technical/document/application_note/DM00122600.pdf
	public void VL6180xDefautSettings();

	// Get Range distance in (mm)
	public int getDistance();
	// Get ALS level in Lux
	public float getAmbientLight(vl6180x_als_gain VL6180X_ALS_GAIN);

	//Load structure provided by the user with identification info
	//Structure example:
	// struct VL6180xIdentification
	//  {
	//   uint8_t idModel;
	//   uint8_t idModelRevMajor;
	//   uint8_t idModelRevMinor;
	//   uint8_t idModuleRevMajor;
	//   uint8_t idModuleRevMinor;
	//   uint16_t idDate;
	//   uint16_t idTime;
	//   };
	public void getIdentification();

		  //Change the default address of the device to allow multiple
		  //sensors on the bus.  Can use up to 127 sensors. New address
		  //is saved in non-volatile device memory.
	public int changeAddress(int old_address, int new_address);

		  //Store address given when the class is initialized.
		  //This value can be changed by the changeAddress() function
//	public int _i2caddress;

	public int VL6180x_getRegister(int registerAddr);
	public int VL6180x_getRegister16bit(int registerAddr);

	public void VL6180x_setRegister(int registerAddr, int data);
	public void VL6180x_setRegister16bit(int registerAddr, int data);



}

enum vl6180x_als_gain { //Data sheet shows gain values as binary list

GAIN_20, // Actual ALS Gain of 20
GAIN_10,     // Actual ALS Gain of 10.32
GAIN_5,      // Actual ALS Gain of 5.21
GAIN_2_5,    // Actual ALS Gain of 2.60
GAIN_1_67,   // Actual ALS Gain of 1.72
GAIN_1_25,   // Actual ALS Gain of 1.28
GAIN_1 ,     // Actual ALS Gain of 1.01
GAIN_40,     // Actual ALS Gain of 40

}
