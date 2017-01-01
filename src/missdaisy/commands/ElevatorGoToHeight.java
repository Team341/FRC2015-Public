package missdaisy.commands;

import edu.wpi.first.wpilibj.command.Command;
import missdaisy.subsystems.*;

/**
 *
 */
public class ElevatorGoToHeight extends Command 
{

	Elevator mElevator;
	double mHeight;
	double startingHeight;
	boolean up;
    public ElevatorGoToHeight(double height) 
    {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	mElevator = Elevator.getInstance();
    	requires(mElevator);
    	mHeight = height;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() 
    {
    	startingHeight = mElevator.getHeight();
    	if(startingHeight < mHeight)
    		up = true;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() 
    {
    	if(up) 
    		mElevator.runManual(1.0);
    	else
    		mElevator.runManual(-1.0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (up)
        {
        	return mElevator.getHeight() >= mHeight;
        }
        else
        	return mElevator.getHeight() <= mHeight;
    }

    // Called once after isFinished returns true
    protected void end() 
    {
    	mElevator.runManual(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
