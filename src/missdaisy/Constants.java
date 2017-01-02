package missdaisy;

/**
 * This class contains constants used throughout the code.  
 * 
 * Putting them here means all the other code you write should never care what
 * PWM a motor plugs into, or how large your wheels are.  If there is a case to
 * be made that the constant might need to be changed on the quicker than it
 * takes to build and download the code (ex. PID control loop gain), you should
 * instead use a Property.
 * 
 * @author Jared341
 */
public class Constants
{
    /***** ROBOT OUTPUTS *****/

    // PWM outputs
    public static final int LeftDrivePWM = 0; 
    public static final int RightDrivePWM = 1 ;
    public static final int ElevatorPWM = 4;
    public static final int IntakePWM = 3;
   
    
    //Solenoid outputs
    public static final int intakeSolenoidA = 5;
    public static final int intakeSolenoidB = 4;
    public static final int intakeExtenderSolenoid = 2;
    public static final int containerStabalizer = 3;

    // Relay outputs
    public static final int LightRelay = 2;
 
    /***** ROBOT INPUTS *****/

    // Digital Inputs
   
    public static final int LeftDriveDI1 = 0; // left Encoder A signal
    public static final int LeftDriveDI2 = 1;// Left encoder B signal
    public static final int RightDriveDI1 = 2;// right Encoder A signal
    public static final int RightDriveDI2 = 3;// right Encoder A signal
    public static final int ElevatorDI1 = 4;
    public static final int ElevatorDI2 = 5;
    
    public static final int ElevatorBottomLimit = 8;
    public static final int ElevatorTopLimit = 9;
    
    // Analog Inputs
    public static final int GyroAI = 1;
    public static final int ElevatorAI = 2;
    
    

    /***** DRIVER INPUTS *****/

    // Joysticks
    public static final int DriverGamepadPort = 0;
    public static final int OperatorGamepadPort = 1;
    
    
    /***** OTHER CONSTANTS *****/

    // Loop timer
    public static final long LoopPeriodMs = 10L;
    public static final double LoopPeriodS = (double)LoopPeriodMs/1000.0;
    
    // Misc quantities
    public static final double WheelSizeIn = 6.0;
    public static final double WheelReduction = 2.0;
    public static final double GamepadDeadband = 0.1;
}
