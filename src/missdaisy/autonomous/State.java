package missdaisy.autonomous;

import missdaisy.Vision;
import missdaisy.subsystems.*;

/**
 * A state in autonomous mode.
 * 
 * You can think of these as states, tasks, actions...basically, discreet things
 * that can be chained together to make up an autonomous mode.
 * 
 * @author Jared341
 */
public abstract class State
{
	protected Drive mDrive;
	protected Intake mIntake;
	protected Elevator mElevator;
	
    private final String mName; 
    /**
     * Create a new state.
     * 
     * @param aName The name of the state (must be unique within the project)
     */
    protected State(String aName)
    {
        mName = aName;
        /*
        mDrive = Drive.getInstance();
        mIntake = Intake.getInstance();
        mElevator = Elevator.getInstance();
        */
    }

    public String toString()
    {
        return mName;
    }

    /**
     * Gets called ONCE when we begin this state.
     * 
     * Useful for things like setup that you don't want to do in the constructor
     * (since the constructor is called at the beginning of the program).
     */
    public void enter()
    {

    }
    
    /**
     * Gets called every cycle while this state is active.
     */
    public abstract void running();

    /**
     * Gets called every cycle to see if we should exit this state.
     * 
     * @return True if the state is finished, False if it should keep running.
     */
    public abstract boolean isDone();

    /**
     * Gets called ONCE when we exit the state.
     * Useful for things like cleanup.  MAKE SURE you put all subsystems back
     * into a known and sensible state...you don't want motors to continue to
     * spin in most cases.
     */
    public void exit()
    {

    }
    
    public String parametersToString()
    {
    	return "(you should override parametersToString in class State).";
    }
   

}
