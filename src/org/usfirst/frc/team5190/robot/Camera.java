
//Include Package 
package org.usfirst.frc.team5190.robot;



//Import Camera and Things
import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ShapeMode;
import edu.wpi.first.wpilibj.CameraServer;



//Public Camera Class
public class Camera {
	
	public Robot robot = new Robot();
	
	//Define Some Variables
	int cameraSession;
	Image cameraFrame;
	
	
	public void cameraInit(){
		
		
		
		cameraFrame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
		
		
		cameraSession = NIVision.IMAQdxOpenCamera("cam0", 
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		
		
		NIVision.IMAQdxConfigureGrab(cameraSession);
		
	}
	
	public void cameraControl(){
		
		NIVision.IMAQdxStartAcquisition(cameraSession);
		
		
		NIVision.Rect rect = new NIVision.Rect(10, 10, 100, 100);
		
		
		while(robot.RobotIsEnabled){
			
			
			NIVision.IMAQdxGrab(cameraSession, cameraFrame, 1);
			
			NIVision.imaqDrawShapeOnImage(cameraFrame, cameraFrame, rect, 
					DrawMode.DRAW_VALUE, ShapeMode.SHAPE_OVAL, 0.0f);
			
			CameraServer.getInstance().setImage(cameraFrame);
		
		}
		
		NIVision.IMAQdxStopAcquisition(cameraSession);
		
	}


}
