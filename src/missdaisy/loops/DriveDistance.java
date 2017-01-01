package missdaisy.loops;

//import missdaisy.fileio.PropertySet;
import missdaisy.subsystems.Drive;
import missdaisy.utilities.DaisyMath;
import missdaisy.utilities.Trajectory;
import missdaisy.utilities.TrajectoryFollower;

/**
 *
 * @author Jared
 */
public class DriveDistance implements Controller
{
    //private PropertySet mProperties = PropertySet.getInstance();
    private Drive mDrive = Drive.getInstance();
    private TrajectoryFollower mFollower;
    private double kTurn;
    private double distanceThreshold;
    private double heading;
    private double direction;
    
    private static DriveDistance instance = null;
    
    public static DriveDistance getInstance()
    {
        if( instance == null )
        {
            instance = new DriveDistance();
        }
        return instance;
    }
    
    private DriveDistance()
    {
        mFollower = new TrajectoryFollower();
        loadProperties();
    }
    
    public void loadProfile(Trajectory profile, double direction, double heading)
    {
        reset();
        //mFollower.setTrajectory(profile);        
        this.direction = direction;
        this.heading = heading;
    }

    public void reset() 
    {
        loadProperties();
       // mFollower.reset();
        mDrive.resetEncoders();
    }

    public boolean onTarget() 
    {
        return mFollower.isFinishedTrajectory();// && mFollower.onTarget(distanceThreshold);
    }

    

    public void run() 
    {
        //System.out.println(this.onTarget() + " " + mFollower.isFinishedTrajectory() + " " + mFollower.onTarget(1.0));
        if( onTarget() )
        {
            mDrive.driveLeftRight(0.0, 0.0);
        }
        else
        {
            double distance = direction*mDrive.getAverageDistance();
            double angleDiff = DaisyMath.boundAngleNeg180to180Degrees(heading-mDrive.getGyroAngle());
            
            //double speed = direction*mFollower.calculate(distance);
            double turn = kTurn*angleDiff;
            //mDrive.driveSpeedTurn(speed, turn);
        }
    }

	@Override
	public void loadProperties() {
		// TODO Auto-generated method stub
		
	}
    
}
