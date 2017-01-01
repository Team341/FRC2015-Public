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
public class MoveElevator extends State
{
    double mHeight;
    Timer timer;
    
    public MoveElevator(double height)
    {
        super("MoveElevator");
        mHeight = height;
        timer = new Timer();
    }

    public void enter()
    {
    	mIntake.open();
    	mElevator.setOpenLoop(false);
    	mElevator.setTarget(mHeight);
    	timer.start();
    }
    
    public void exit()
    {
        mElevator.setOpenLoop(true);
        mElevator.runManual(0.0);
    }
    
    public void running() 
    {
    	mElevator.goToPosition();
    }

    public boolean isDone() 
    {
    	return mElevator.atTarget || timer.get() > 3;
    }
}
