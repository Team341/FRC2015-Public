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
public class DropGrabber extends State
{
    private Timer timer;
    private double mTimeout;
    public DropGrabber(double timeout)
    {
        super("DropGrabber");
        
       timer = new Timer();
       mTimeout = timeout;
    }

    public void enter()
    {
        timer.reset();
        timer.start();
        mDrive.raiseGrabber();
    }
    
    public void exit()
    {
        mDrive.setOpenLoop();
        mDrive.driveLeftRight(0.0,0.0);
    }
    
    public void running() 
    {	
    }

    public boolean isDone() 
    {
        return timer.get() > mTimeout;
    }
}

