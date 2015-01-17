
//Include Package 
package org.usfirst.frc.team5190.robot;



//Import Camera 
import edu.wpi.first.wpilibj.CameraServer;


//Public Camera Class
public class Camera {
	
	CameraServer serv;
	
	
	{

    serv = CameraServer.getInstance();
    serv.setQuality(50);
    serv.startAutomaticCapture("cam0");
		
	}
	



}
