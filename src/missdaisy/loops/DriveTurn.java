package missdaisy.loops;

import missdaisy.subsystems.Drive;
import missdaisy.utilities.DaisyMath;
import missdaisy.utilities.SynchronousPID;
import missdaisy.utilities.Trajectory;
import missdaisy.utilities.TrajectoryFollower;

/**
 *
 * @author Jared
 */
public class DriveTurn implements Controller
{
    //private PropertySet mProperties = PropertySet.getInstance();
    private Drive mDrive = Drive.getInstance();
    private TrajectoryFollower mFollower;
    private SynchronousPID mPID;
    private double distanceThreshold;
    private double direction;
    private double goalAngle;
    
    private static DriveTurn instance = null;
    
    public static DriveTurn getInstance()
    {
        if( instance == null )
        {
            instance = new DriveTurn();
        }
        return instance;
    }
    
    private DriveTurn()
    {
        mFollower = new TrajectoryFollower();
        mPID = new SynchronousPID();
        loadProperties();
    }
    
    public void loadProfile(Trajectory profile, double direction, double goalAngle)
    {
        reset();
        this.goalAngle = goalAngle;
       // mFollower.setTrajectory(profile);   
        mPID.setSetpoint(0.0);
        this.direction = direction;
    }

    public void reset() 
    {
        loadProperties();
        //mFollower.reset();
        mPID.reset();
        mDrive.resetEncoders();
    }

    public boolean onTarget() 
    {
        return mFollower.isFinishedTrajectory() && mPID.onTarget(distanceThreshold);
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
            if( mFollower.isFinishedTrajectory() )//&& mFollower.onTarget(distanceThreshold) )
            {
                double angleError = DaisyMath.boundAngleNeg180to180Degrees(mDrive.getGyroAngle() - goalAngle);
                //System.out.println(mDrive.getGyroAngle() + " " + goalAngle + " " + angleError);
                mDrive.driveSpeedTurn(0.0,mPID.calculate(angleError));
            }
            else
            {
                double distance = direction*Math.abs((mDrive.getLeftDistance()-mDrive.getRightDistance())/2.0);
                //double turn = direction*mFollower.calculate(Math.abs(distance));
                //System.out.println(distance + " " + turn);
                //mDrive.driveSpeedTurn(0.0, turn);
            }
        }
    }
    
}
