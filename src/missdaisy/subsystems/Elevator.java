package missdaisy.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.*;
import missdaisy.Constants;
import missdaisy.utilities.MA3Encoder;
import missdaisy.utilities.SynchronousPID;
import missdaisy.utilities.TrajectoryFollower;
import missdaisy.utilities.TrajectoryFollower.TrajectoryConfig;

public class Elevator extends DaisySubsystem
{  
	Talon elevator;
	public Encoder elevatorEncoder;
	DigitalInput bottomLimit, topLimit;
	double currentHeight;
    final static double toteHeight = 12.1;
    final static double heightOffFloor = .5;
    PowerDistributionPanel pdp;
    boolean up;
    final static double elevatorGainUp = 1.0; 
    final static double elevatorGainDown = 0.85;
    //By default, should be 1.0 to keep speed of elevator exactly equal to the controller's axis position
    //Make these less than 1.0 to decrease the speed of the elevator relative to the axis position
    
    boolean openLoop;
    public boolean atTarget;
    public TrajectoryFollower controller;
    
	double mTarget;
	
	private static Elevator instance= null;
	
	public Elevator()
	{
		elevator = new Talon (Constants.ElevatorPWM);
		elevatorEncoder = new Encoder(Constants.ElevatorDI1, Constants.ElevatorDI2);
		elevatorEncoder.setDistancePerPulse((Math.PI*1.989) / 250.0);
		//elevatorEncoder.setScalingFactor((1.989* Math.PI)/360);
		//elevatorEncoder.setDistancePerPulse((1.989*Math.PI) / 360);
		elevatorEncoder.reset();
		bottomLimit = new DigitalInput(Constants.ElevatorBottomLimit);
		topLimit = new DigitalInput(Constants.ElevatorTopLimit);
		currentHeight = 0.0;
		pdp= new PowerDistributionPanel();
		
		openLoop= true;
		controller = new TrajectoryFollower();
		
	}
	
	public static Elevator getInstance()
    {
        if( instance == null )
        {
        	instance = new Elevator();
        }
        return instance;
    }
	
	public double getCurrent()
	{
		return Math.max(pdp.getCurrent(3),pdp.getCurrent(12));
	}
	
	public void runManual(double speed)
	{
		
		//If it tries to go down past the bottom limit, 
		// or it hits the current limit in the range of a tote
		// or it tries to go up past the top limit
		
		if((speed < 0.0 && getBottomLimit()) || (speed < 0.0 && ((getHeight() > 5 && getHeight() < 7) && getCurrent() > 15)) || (speed > 0.0 && getTopLimit()))
		{
			elevator.set(0.0);
			openLoop = true;
		}
		else
		{
			if (elevator.getSpeed() < 0.0)
			{
				elevator.set(speed * elevatorGainDown);
			}
			else
			{
				elevator.set(speed * elevatorGainUp);
			}			
		}
		
		
		if(getBottomLimit())
		{
			reset();
		}
		
		if(speed == 0.0 && getSpeed() < 0.0)
		{
			elevator.set(-.15*getSpeed());
		}
		
		//elevator.set(-speed);
	}
	
	public synchronized void setTarget(double target)
	{
		if(mTarget!=target)
			atTarget = false;
		mTarget = target;
		
	}
	
	public void setOpenLoop(boolean input)
	{
		openLoop = input;
	}
	
	public synchronized void goToPosition()
	{
	
		if(!openLoop)
		{
			runManual(controller.calculate(getHeight(), getSpeed()));
		}
		else
		{
			runManual(0.0);
		}
		
		
		/*if(!openLoop && !atTarget)
		{
			
			if(up && (getHeight()) < mTarget)
			{
				runManual(1.0);
			}
			else if(!up && (getHeight()) > mTarget)
			{
				runManual(-0.75);
			}
			else
				runManual(0.0);
		}
		else
			runManual(0.0);*/
	}
	
	public void loadProperties()
	{
		controller.configure(
				m_propertySet.getDoubleValue("ElevatorKp" , 0.0),
				m_propertySet.getDoubleValue("ElevatorKi", 0.0),
				m_propertySet.getDoubleValue("ELevatorKd", 0.0),
				m_propertySet.getDoubleValue("ElevatorKv",  0.0),
				m_propertySet.getDoubleValue("ElevatorKa", 0.0),
				new TrajectoryFollower.TrajectoryConfig());
	}
	
	public double getHeight()
	{
		return elevatorEncoder.getDistance();
	}
	
	public double getSpeed()
	{
		return elevatorEncoder.getRate();
	}
	
	public boolean getBottomLimit()
	{ 
		return !bottomLimit.get();
	}
	
	public boolean getTopLimit()
	{
		return !topLimit.get();
	}
	
	
	
	public void reset()
	{
		elevatorEncoder.reset();
	}
}
