package missdaisy.autonomous;

import edu.wpi.first.wpilibj.Timer;
import missdaisy.Constants;
import missdaisy.loops.DriveDistance;
import missdaisy.utilities.SynchronousPID;
import missdaisy.utilities.Trajectory;
import missdaisy.subsystems.*;

/**
 *
 * @author Pratik341
 */
public class MixedDrive extends State
{
    private double mDistance;
    private double mHeading;
    private int mHeight;
    private double kP, kI, kD;
    private SynchronousPID controller;
    private double mIntakeSpeed;
    
    public MixedDrive(double distance, int elevatorHeight, double intakeSpeed)
    {
        super("MixedDrive");
        
        kP = 0.8;
        kI = 0.0;
        kD = 0.07;
        
        mDistance = distance;
        controller = new SynchronousPID(kP, kI, kD);
        mHeight = elevatorHeight;
        mIntakeSpeed = intakeSpeed;
    }

    public void enter()
    {
    	/*
        mDrive.resetEncoders();
        mDrive.reset();
        controller.setSetpoint(mDistance);
        //mIntake.close();
        mElevator.setOpenLoop(false);
        mElevator.setTarget(mHeight);
        */
       
    }
    
    public void exit()
    {
    	/*
        mDrive.setOpenLoop();
        mDrive.driveLeftRight(0.0,0.0);
        */
    }
    
    public void running() 
    {
    	/*
    	mDrive.driveSpeedTurn(0.7*(controller.calculate(mDrive.getAverageDistance())),0.0);
    	mIntake.set(mIntakeSpeed);
    	mElevator.goToPosition();
    	*/
    	
    }

    public boolean isDone() 
    {
        return controller.onTarget(0.1) && mElevator.atTarget;
    }
    
    public String parametersToString()
    {
		return mDistance + ", " + mHeight + ", " + mIntakeSpeed;
    	
    }

}

