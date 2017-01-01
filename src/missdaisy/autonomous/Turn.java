package missdaisy.autonomous;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import missdaisy.Constants;
import missdaisy.loops.DriveDistance;
import missdaisy.utilities.SynchronousPID;
import missdaisy.utilities.Trajectory;
import missdaisy.subsystems.*;

/**
 *
 * @author Pratik341
 */
public class Turn extends State
{
    private double mAngle;
    private double error, kp, ki, kd;
    SmartDashboard ds; 
    double mHeight;
    
    public Turn(double angle, double elevatorHeight)
    {
        super("Turn");
       
        
        mAngle = angle;
        kp = 0.06; ki = 0.0; kd = 0.0;
        mHeight = elevatorHeight;
        
    }

    public void enter()
    {
        mDrive.reset();
        error = mAngle - mDrive.getGyroAngle();
        mElevator.setOpenLoop(false);
        mElevator.setTarget(mHeight);
        
    }
    
    public void exit()
    {
        mDrive.setOpenLoop();
        mDrive.driveLeftRight(0.0,0.0);
    }
    
    public void running() 
    {
    	error = mAngle - mDrive.getGyroAngle();
    	
    	mDrive.driveSpeedTurn(0.0, -kp*error);
    	
    	mElevator.goToPosition();
    }

    public boolean isDone() 
    {
    	return Math.abs(error) <= 5.0;// && Math.abs(mHeight - mElevator.getHeight()) < .1;
    }
}
