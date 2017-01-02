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
public class RunIntake extends State
{
    double mSpeed;
    double mTime;
    Timer timer;
    
    public RunIntake(double speed, double time)
    {
        super("RunIntake");
        mSpeed = speed;
        mTime = time;
        timer = new Timer();
    }

    public void enter()
    {
    	mIntake.close();
    	timer.start();
    }
    
    public void exit()
    {
        mIntake.set(0.0);
        mIntake.close();
    }
    
    public void running() 
    {
    	mIntake.set(-mSpeed);
    }

    public boolean isDone() 
    {
    	return timer.get() >= mTime;
    }
    
    public String parametersToString()
    {
    	return mSpeed + ", " + mTime;
    }
}
