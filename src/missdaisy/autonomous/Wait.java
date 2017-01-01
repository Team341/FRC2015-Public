package missdaisy.autonomous;

import edu.wpi.first.wpilibj.Timer;

import missdaisy.Constants;
import missdaisy.loops.DriveDistance;
import missdaisy.utilities.Trajectory;
import missdaisy.subsystems.*;

/**
 *
 * @author Pratik341
 */
public class Wait extends State
{
	private double mTime;
	private Timer timer;
    
    public Wait(double time)
    {
        super("Wait");
        
        timer = new Timer();
        mTime = time;
    }

    public void enter()
    {
        timer.start();
        timer.reset();
        mDrive.driveSpeedTurn(0.0, 0.0);
    }
    
    public void exit()
    {
        mDrive.reset();
        mDrive.driveSpeedTurn(0.0, 0.0);
    }
    
    public void running() 
    {
    	mDrive.driveSpeedTurn(0.0, 0.0);
    	
    }

    public boolean isDone() 
    {
        return timer.get() >= mTime;
    }
}
