package missdaisy.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import missdaisy.Constants;

public class Intake extends DaisySubsystem
{
	//Actuators
	Talon intake;
	DoubleSolenoid intakePiston;
	Solenoid intakeExtender;
	
	//Variables
	boolean isOpen;
	boolean isExtended;
	
	
	
	private static Intake instance = null;
	
	public Intake()
	{
		intake = new Talon (Constants.IntakePWM);
		intakePiston = new DoubleSolenoid (Constants.intakeSolenoidA, Constants.intakeSolenoidB);
		intakeExtender = new Solenoid (Constants.intakeExtenderSolenoid);
		isOpen = true;
		isExtended = false;
		
	}
	
	public static Intake getInstance()
    {
        if( instance == null )
        {
        	instance = new Intake();
        }
        return instance;
    }
	
	public void set(double speed)
	{
		intake.set(speed);
	}
	
	public void open()
	{
		intakePiston.set(Value.kReverse);;
		isOpen = true;
	}
	
	public void close()
	{
		intakePiston.set(Value.kForward);
		isOpen = false;
	}
	
	
	
	public boolean isOpen()
	{
		return isOpen;
	}
}
