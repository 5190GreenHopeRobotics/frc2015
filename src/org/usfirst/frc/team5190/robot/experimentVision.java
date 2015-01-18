package org.usfirst.frc.team5190.robot;

import com.ni.vision.NIVision.ParticleFilterCriteria;
import edu.wpi.first.wpilibj.CameraServer;

public class experimentVision {
	
	
	//Display Resolution 
	final int IMAGE_RES = 480;
	//Viewing 
	final double VIEW_ANGLE = 68.5;
	
	// The Value of PI
	final double PI = 3.141592653;
	
	
	// Limits for Identification 
	final int RECT_LIMIT = 40;
	final int ASPECT_RATIO_LIMIT = 55;
	
	
	// Limits for Target Identification
	final int TAPE_WIDTH_LIMIT = 50;
	final int VERTICAL_SCORE_LIMIT = 50;
	final int SCORE_LIMIT = 50;
	
	// Minimum Area of particles 
	final int AREA_MIN = 150;
	
	// Max of Particles 
	final int MAX_PART = 8;
	
	CameraServer camera;
	ParticleFilterCriteria PFC;
	
	
    public class Scores {
        double rectangularity;
        double aspectRatioVertical;
        double aspectRatioHorizontal;
    }
    
    public class TargetReport {
		int verticalIndex;
		int horizontalIndex;
		boolean Hot;
		double totalScore;
		double leftScore;
		double rightScore;
		double tapeWidthScore;
		double verticalScore;
    };
	
	
	public void cameraInit(){
		
		
		
	}
	
	
}
