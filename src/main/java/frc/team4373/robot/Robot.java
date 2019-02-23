package frc.team4373.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.team4373.robot.input.OI;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    DoubleSolenoid[][] solenoids = new DoubleSolenoid[4][8];

    /**
     * Constructor for the Robot class. Variable initialization occurs here;
     * WPILib-related setup should occur in {@link #robotInit}.
     */
    public Robot() {
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     *
     * <p>All SmartDashboard fields should be initially added here.</p>
     */
    @Override
    public void robotInit() {
        int port = 0;
        while (port < 8) {
            solenoids[2][port] = new DoubleSolenoid(2, port++, port++);
        }
        boolean secondBot = true;
        if (!secondBot) {
            solenoids[3][0] = new DoubleSolenoid(3, 0, 2);
            solenoids[3][2] = new DoubleSolenoid(3, 1, 7);
        } else {
            solenoids[3][0] = new DoubleSolenoid(3, 0, 1);
            solenoids[3][2] = new DoubleSolenoid(3, 2, 3);
        }
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want run during disabled,
     * autonomous, teleoperated, and test.
     *
     * <p>This runs after the mode-specific periodic functions but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
//        int pcm = (int) SmartDashboard.getNumber("pcm", 2);
//        int forwardPort = (int) SmartDashboard.getNumber("forwardPort", 0);
//        int backwardPort = (int) SmartDashboard.getNumber("backwardPort", 0);
//        int power = (int) SmartDashboard.getNumber("power", 0);
////        (new DoubleSolenoid(pcm, forwardPort, backwardPort)).set(power > 0 ? DoubleSolenoid.Value.kForward : (power < 0 ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kOff));
//        solenoids[pcm][forwardPort].set(power > 0 ? DoubleSolenoid.Value.kForward : (power < 0 ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kOff));
        if (OI.getOI().getDriveJoystick().getRawButton(1)) {
            solenoids[2][0].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: H-WHEEL
            solenoids[2][0].set(DoubleSolenoid.Value.kReverse);
        }

        if (OI.getOI().getDriveJoystick().getRawButton(2)) {
            solenoids[2][2].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: INTAKE_DEPLOY
            solenoids[2][2].set(DoubleSolenoid.Value.kReverse);
        }

        if (OI.getOI().getDriveJoystick().getRawButton(3)) {
            solenoids[2][4].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: CLIMB
            solenoids[2][4].set(DoubleSolenoid.Value.kReverse);
        }

        if (OI.getOI().getDriveJoystick().getRawButton(4)) {
            solenoids[2][6].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: CLIMB
            solenoids[2][6].set(DoubleSolenoid.Value.kReverse);
        }

        if (OI.getOI().getDriveJoystick().getRawButton(5)) {
            solenoids[3][0].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: INTAKE
            solenoids[3][0].set(DoubleSolenoid.Value.kReverse);
        }

        if (OI.getOI().getDriveJoystick().getRawButton(6)) {
            solenoids[3][2].set(DoubleSolenoid.Value.kForward);
        } else { //FIXME: 1+7
            solenoids[3][2].set(DoubleSolenoid.Value.kReverse);
        }
    }

    /**
     * This function is called once when Sandstorm mode starts.
     */
    @Override
    public void autonomousInit() {

    }

    /**
     * This function is called once when teleoperated mode starts.
     */
    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during Sandstorm mode.
     */
    @Override
    public void autonomousPeriodic() {
//        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
//        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * This function runs periodically in disabled mode.
     * It is used to verify that the selected auton configuration is legal.
     * <br>
     * <b>UNDER NO CIRCUMSTANCES SHOULD SUBSYSTEMS BE ACCESSED OR ENGAGED IN THIS METHOD.</b>
     */
    @Override
    public void disabledPeriodic() {

    }

    /**
     * Constrains a percent output to [-1, 1].
     * @param output the percent output value to constrain.
     * @return the input percent output constrained to the safe range.
     */
    public static double constrainPercentOutput(double output) {
        if (output > 1) {
            return 1;
        }
        if (output < -1) {
            return -1;
        }
        return output;
    }
}
