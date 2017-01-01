package missdaisy;

import missdaisy.autonomous.AutonomousParser;
import missdaisy.autonomous.StateMachine;
import missdaisy.commands.*;
import missdaisy.fileio.*;
import missdaisy.loops.FastLoopTimer;
import missdaisy.subsystems.*;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Miss Daisy 2015 Robot
 * 
 * @author Pratik341
 */
public class Daisy2015 extends IterativeRobot 
{
    
    Drive mDrive;
    Intake mIntake;
    Elevator mElevator;
    OI mOI;
    SmartDashboard ds;
    PropertyReader mPropertyReader;
    PropertySet mProperties;
    StateMachine mAutonomousStateMachine;
    String autonomousName = "";
    int buttonCounter;
    SendableChooser autoChooser;
    String autoName;
    Command auto;
   
    boolean mLastIterationButtonState = false;
   
    
    private static Daisy2015 instance = null;
    
    public static Daisy2015 getInstance()
    {
        return instance;
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit()
    {
        instance = this;
        mDrive = Drive.getInstance();
        mIntake = Intake.getInstance();
        mElevator = Elevator.getInstance();
        mOI = OI.getInstance();
        ds = new SmartDashboard();
        
        mPropertyReader = new PropertyReader();
        mProperties = PropertySet.getInstance();
        mPropertyReader.parseFile("/home/lvuser/properties.txt");
        
        buttonCounter = 0;
        autoChooser = new SendableChooser();
        autoChooser.addDefault("DriveForeward", new AutoDriveForeward());
        autoChooser.addObject("3Tote", new Auto3Tote());
        
        loadAllProperties();
        
        //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser2, 1, "AUTO: Shoot in place  ");
        //DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser3, 1, "                      ");
    }
    
    public void disabledInit()
    {
    	logToDashboard();
    	mAutonomousStateMachine = new StateMachine((new AutonomousParser()).parseStates());
    }
    
    public void disabledPeriodic()
    {
    	/*auto = (Command) autoChooser.getSelected();
    	autoName = auto.getName();
    	
    	if(autoName.equals("3Tote")) 
    	{
    		mPropertyReader.parseAutonomousFile("/home/lvuser/3Tote.txt");
			autonomousName = "3 Tote";
    	}
    	else if (autoName.equals("DriveForeward"))
    	{
    		mPropertyReader.parseAutonomousFile("/home/lvuser/DriveForeward.txt");
			autonomousName = "DriveForeward";
			
    	}
    	else //DEFAULT
    	{
    		mPropertyReader.parseAutonomousFile("/home/lvuser/DriveForeward.txt");
			autonomousName = "DriveForeward";
    	}
    	*/
    	
    	
    	if(mOI.mDriverGamepad.getAButton() && !mLastIterationButtonState)
    	{
    		buttonCounter++;
    		readAutoMode(buttonCounter);
    		
    		
	    	if(buttonCounter > 6)
	    	{
	    		buttonCounter = 0;
	    	}
	    	
	    	mAutonomousStateMachine = new StateMachine((new AutonomousParser()).parseStates());
    	}
    	
    	 if(mOI.mDriverGamepad.getBButton())
    	 {
    		 mDrive.reset();
    		 mPropertyReader.parseFile("/home/lvuser/properties.txt");
    		 loadAllProperties();
    	 }
    	 
    	 mLastIterationButtonState = mOI.mDriverGamepad.getAButton();
    	 
    	 
    	 logToDashboard();
    }
    
    private void readAutoMode(int lWhich)
    {
    	switch(lWhich)
    	{
    		case 1:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/DriveForeward.txt");
    			autonomousName = "DriveForeward";
    			break;
    		case 2:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/3Tote.txt");
    			autonomousName = "3 Tote";
    			break;
    		case 3:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/1Tote.txt");
    			autonomousName = "1 Tote";
    			break;
    		case 4:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/DoNothing.txt");
    			autonomousName = "Do Nothing";
    			break;
    		case 5:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/KnockContainer.txt");
    			autonomousName = "Knock Container";
    			break;
    		case 6:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/GrabContainer.txt");
    			autonomousName = "Grab Container";
    			break;
    		default:
    			mPropertyReader.parseAutonomousFile("/home/lvuser/DriveForeward.txt");
    			autonomousName = "DriveForeward";
    			break;
    	}
    }

    public void autonomousInit() 
    {
    	//mPropertyReader.parseAutonomousFile("/home/lvuser/1Tote.txt");
    }
    
    public void autonomousPeriodic()
    {
        mAutonomousStateMachine.run();
        logToDashboard();

    }
    
    public void teleopInit()
    {
        mElevator.setOpenLoop(true);
    }

    public void teleopPeriodic()
    {
        mOI.processDriverInputs();
        logToDashboard();
    }
    
    public void testInit()
    {
        
    }
    
    public void testPeriodic() 
    {
        
    }
    
    private void resetAllSubsystems()
    {
        
    }
    
    private void logToDashboard()
    {
    	ds.putNumber("Right Distance", mDrive.getRightDistance());     
    	ds.putNumber("Left Distance", mDrive.getLeftDistance());
    	ds.putNumber("Right Speed", mDrive.getRightSpeed());
    	ds.putNumber("Left Speed" , mDrive.getLeftSpeed());
    	ds.putBoolean("Intake Open" , mIntake.isOpen());
    	ds.putNumber("Gyro" , mDrive.getGyroAngle());
    	ds.putBoolean("Elevator Bottom Limit" , mElevator.getBottomLimit());
    	ds.putBoolean("Elevator Top Limit" , mElevator.getTopLimit());
    	ds.putString("Autonomous Mode" , autonomousName);
    	ds.putNumber("Total Current", mDrive.getTotalCurrent());
    	ds.putNumber("ElevatorCurrent", mElevator.getCurrent());
    	ds.putNumber("Elevator Height", mElevator.getHeight());
    	ds.putBoolean("At Taget", mElevator.atTarget);
    	//ds.putData("Autonomous Chooser", autoChooser);
    	
    }
    
    private void loadAllProperties()
    {
        mDrive.loadProperties();
        mElevator.loadProperties();
    }
}
