package missdaisy.loops;

import missdaisy.subsystems.Drive;
import missdaisy.utilities.DaisyMath;

/**
 *
 * @author Jared
 */
public class DriveTurnSimple implements Controller
{
    //private PropertySet mProperties = PropertySet.getInstance();
    private Drive mDrive = Drive.getInstance();
    private double distanceThreshold;
    private double direction;
    private double goalAngle;
    private double speed;
    
    private static DriveTurnSimple instance = null;
    
    public static DriveTurnSimple getInstance()
    {
        if( instance == null )
        {
            instance = new DriveTurnSimple();
        }
        return instance;
    }
    
    private DriveTurnSimple()
    {
        loadProperties();
    }
    
    public void setGoal(double aAngle, double aDirection)
    {
        goalAngle = aAngle;
        direction = aDirection;
    }

    public void reset() 
    {
    }

    public boolean onTarget() 
    {
        double angleDiff = DaisyMath.boundAngleNeg180to180Degrees(mDrive.getGyroAngle() - goalAngle);
        
        if( direction > 0.0 )
        {
            return angleDiff < Math.abs(distanceThreshold) || angleDiff > 0.0;
        }
        else
        {
            return angleDiff < Math.abs(distanceThreshold) || angleDiff < 0.0;
        }
    }

    public final void loadProperties() 
    {
        
    }

    public void run() 
    {
        if( onTarget() )
        {
            mDrive.driveLeftRight(0.0, 0.0);
        }
        else
        {
            mDrive.driveSpeedTurn(0.0, direction*speed);
        }
    }
    
}
