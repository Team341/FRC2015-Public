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
public class JustDrive extends State
{
    private double mDistance;
    private double mHeading;
    private double mHeight;
    private double kP, kI, kD;
    private SynchronousPID controller;
    private double mIntakeSpeed;
    private Timer mTimer;
    private double mTimeout;
    private double start;
    
    public JustDrive(double distance, double timeout)
    {
        super("JustDrive");
        
        kP = 0.8;
        kI = 0.0;
        kD = 0.07;
        
        mDistance = distance;
        controller = new SynchronousPID(kP, kI, kD);
        mTimer =new Timer();
        mTimeout = timeout;
    }

    public void enter()
    {
        mDrive.resetEncoders();
        mDrive.reset();
        controller.setSetpoint(mDistance);
        mIntake.close();
        mTimer.reset();
        mTimer.start();
        start = Timer.getFPGATimestamp();
       
    }
    
    public void exit()
    {
        mDrive.setOpenLoop();
        mDrive.driveLeftRight(0.0,0.0);
    }
    
    public void running() 
    {
    	mDrive.driveSpeedTurn(0.7*(controller.calculate(mDrive.getAverageDistance())),0.0);
    	
    }

    public boolean isDone() 
    {
        return Timer.getFPGATimestamp() - start == mTimeout;
    }
}

