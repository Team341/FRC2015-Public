package missdaisy.commands;

import edu.wpi.first.wpilibj.command.Command;
import missdaisy.subsystems.*;

/**
 *
 */
public class FloorElevator extends CommandBase {

    public FloorElevator() 
    {
    	super("FloorElevator");
        requires(mElevator);
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	System.out.println("Test");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute()
    {
    	mElevator.runManual(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return mElevator.getHeight() == 0.0;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	mElevator.runManual(0.0);
    	mElevator.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() 
    {
    	end();
    }
}
