package frc.team4373.robot.input;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.filters.FineGrainedPiecewiseFilter;

/**
 * OI provides access to operator interface devices.
 */
public class OI {
    private static OI oi = null;
    private RooJoystick<FineGrainedPiecewiseFilter> driveJoystick;
    private RooJoystick operatorJoystick;

    // lift buttons
    private JoystickButton operatorLiftCargoL3;
    private JoystickButton operatorLiftCargoL2;
    private JoystickButton operatorLiftCargoL1;
    private JoystickButton operatorLiftCargoShip;
    private JoystickButton operatorLiftGround;
    private JoystickButton operatorStowIntake;

    // climb buttons
    private JoystickButton driverClimbRaiseBot;
    private JoystickButton driverClimbRetractFront;
    private JoystickButton driverClimbRetractRear;

    // drive buttons
    private JoystickButton driverVisionAlignment;
    private JoystickButton killAllAuton;

    private OI() {
        this.driveJoystick =
                new RooJoystick<>(RobotMap.DRIVE_JOYSTICK_PORT, new FineGrainedPiecewiseFilter());
        this.operatorJoystick =
                new RooJoystick<>(RobotMap.OPERATOR_JOYSTICK_PORT,
                        new FineGrainedPiecewiseFilter());
    }

    /**
     * The getter for the OI singleton.
     *
     * @return The static OI singleton object.
     */
    public static OI getOI() {
        if (oi == null) {
            synchronized (OI.class) {
                if (oi == null) {
                    oi = new OI();
                }
            }
        }
        return oi;
    }

    /**
     * Gets the drive joystick controlling the robot.
     * @return The drive joystick controlling the robot.
     */
    public RooJoystick getDriveJoystick() {
        return this.driveJoystick;
    }

    /**
     * Gets the operator joystick controlling the robot.
     * @return The operator joystick controlling the robot.
     */
    public RooJoystick getOperatorJoystick() {
        return this.operatorJoystick;
    }
}