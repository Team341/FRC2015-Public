package missdaisy.commands;

import missdaisy.OI;
import missdaisy.subsystems.Elevator;
import edu.wpi.first.wpilibj.command.Command;

public abstract class CommandBase extends Command 
{
	
	public static Elevator mElevator;
	public static void init()
	{
		
		mElevator = Elevator.getInstance();
	}
	
	public CommandBase(String name)
	{
		super(name);
	}
	
	public CommandBase()
	{
		super();
	}

}
