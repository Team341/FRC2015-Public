package missdaisy;

import missdaisy.commands.*;
import missdaisy.subsystems.*;
import missdaisy.utilities.DaisyMath;
import missdaisy.utilities.TrajectoryFollower.*;
import missdaisy.utilities.TrajectoryFollower;
import missdaisy.utilities.XboxController;
import missdaisy.utilities.TrajectoryFollower.TrajectorySetpoint;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Operator interface.
 * 
 * @author Jared341
 */
public class OI
{
    private final Drive mDrive;
    private final Intake mIntake;
    private final Elevator mElevator;
  
    final XboxController mDriverGamepad;
    final XboxController mOperatorGamepad;
   
    private static OI instance = null;
    
    private long mLoopCounter = 0;
    private double setpoint;

    public static OI getInstance()
    {
        if( instance == null )
        {
            instance = new OI();
        }
        return instance;
    }

    private OI()
    {
    	mDrive = Drive.getInstance();
        mIntake = Intake.getInstance();
        mElevator = Elevator.getInstance();

        //set up OI
        mDriverGamepad = new XboxController(Constants.DriverGamepadPort);
        mOperatorGamepad = new XboxController(Constants.OperatorGamepadPort);
       
    }

    public void resetState()
    {
        
    }

    public void processDriverInputs()
    {
    	//A = new JoystickButton((Joystick) mOperatorGamepad, XboxController.A_Button);
        /***** DRIVER *****/
        /*
         * Driver functions:
         * 1. Manual driving (Uses both analog sticks)
         * 2. Shifting from low to high gear [Software controlled, no shifter] (LB/RB)
         * 
         */
        mDrive.setOpenLoop();
            
            
        // Manual driving
        double speedCommand = DaisyMath.applyDeadband(-mDriverGamepad.getLeftYAxis(), Constants.GamepadDeadband);
        double turnCommand = DaisyMath.applyDeadband(-mDriverGamepad.getRightXAxis(), Constants.GamepadDeadband);
        mDrive.driveSpeedTurn(speedCommand, turnCommand);

        // shift for low/high gear
        if (mDriverGamepad.getRB())
            mDrive.highGear(); // normal speed
        else if ( mDriverGamepad.getLB())
            mDrive.lowGear(); // go slower for when carrying stack
        
        //mElevator.elevatorEncoder.getRate() < 0.0 || 
        if(mDriverGamepad.getAButton())
        	mDrive.lowerGrabber();
        else if (mDriverGamepad.getBButton())
        	mDrive.raiseGrabber();
        /******* OPERATOR *******/
        /*
         * Operator functions:
         * 1. Run intake
         * 2. Open/Close intake
         * 3. Drive elevator up/down
         */
        
        //INTAKE COMMANDS
        if(mOperatorGamepad.getRightTrigger())
        {
        	mIntake.set(-mOperatorGamepad.getRawAxis(3)*.55); // intake
        }
        else if(mOperatorGamepad.getLeftTrigger())
        {
        	mIntake.set(mOperatorGamepad.getRawAxis(2)*.55); // spit
        }
        else
        {
        	mIntake.set(0.0);
        }
        
        
        if(mOperatorGamepad.getRB())
        {
        	mIntake.close();
        }
        else if(mOperatorGamepad.getLB())
        {
        	mIntake.open();
        }
        
        
       
        
        
        //Manual override for the elevator
        if( Math.abs(mOperatorGamepad.getLeftYAxis()) > .2)
        {
        	mElevator.setOpenLoop(true);
        	double elevatorCommand = DaisyMath.applyDeadband(-mOperatorGamepad.getLeftYAxis(), .2);
            mElevator.runManual(elevatorCommand);
        }
        else //pre programmed set points 
        {
        	if(mOperatorGamepad.getAButton()) // floor the elevator
        	{
        		mElevator.setOpenLoop(false);
        		mElevator.controller.setGoal(new TrajectoryFollower.TrajectorySetpoint(), -1.0);
        	}
        	else if (mOperatorGamepad.getXButton()) // level to obtain a tote
        	{
        		mElevator.setOpenLoop(false);
        		mElevator.controller.setGoal(new TrajectoryFollower.TrajectorySetpoint(), 2.0);
        	}
        	else if(mOperatorGamepad.getBButton()) // level to raise a tote
        	{
        		mElevator.setOpenLoop(false);
        		mElevator.controller.setGoal(new TrajectoryFollower.TrajectorySetpoint(), 37.0);
        		mIntake.open();
        	}
        	else if(mOperatorGamepad.getYButton()) // level to raise a container
        	{
        		mElevator.setOpenLoop(false);
        		mElevator.controller.setGoal(new TrajectoryFollower.TrajectorySetpoint(), 25.0);
        		mIntake.open();
        	}
        	
        	mElevator.goToPosition();
        	
        }  
       
        
        
        
        
        mLoopCounter++;
    }
    
}
