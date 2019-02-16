package frc.team4373.robot.input;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.ClearSubsystemCommand;
import frc.team4373.robot.commands.ToggleLightRingCommand;
import frc.team4373.robot.commands.auton.VisionRotatorAuton;
import frc.team4373.robot.commands.auton.sequences.*;
import frc.team4373.robot.input.filters.FineGrainedPiecewiseFilter;

/**
 * OI provides access to operator interface devices.
 */
public class OI {
    private static OI oi = null;
    private JoystickButton csrightcargo;
    private JoystickButton csleftcargo;
    private JoystickButton csrighthatch;
    private JoystickButton cslefthatch;
    private RooJoystick<FineGrainedPiecewiseFilter> driveJoystick;
    private RooJoystick operatorJoystick;
    private JoystickButton CSFrontHatchAutonButton;
    private JoystickButton lightRingButton;
    private JoystickButton killSwitch;

    private OI() {
        this.driveJoystick =
                new RooJoystick<>(RobotMap.DRIVE_JOYSTICK_PORT, new FineGrainedPiecewiseFilter());
        this.operatorJoystick =
                new RooJoystick<>(RobotMap.OPERATOR_JOYSTICK_PORT,
                        new FineGrainedPiecewiseFilter());
        this.CSFrontHatchAutonButton = new JoystickButton(driveJoystick, 2);
        this.CSFrontHatchAutonButton.whenPressed(new CSFrontHatchAuton());

        this.cslefthatch = new JoystickButton(driveJoystick, 11);
        this.cslefthatch.whenPressed(new CSSideHatchAuton(RobotMap.Side.LEFT, RobotMap.CargoShipPort.NEAR));

        this.csrighthatch = new JoystickButton(driveJoystick, 12);
        this.csrighthatch.whenPressed(new CSSideHatchAuton(RobotMap.Side.RIGHT, RobotMap.CargoShipPort.NEAR));

        this.csleftcargo = new JoystickButton(driveJoystick, 9);
        this.csleftcargo.whenPressed(new CSSideCargoAuton(RobotMap.Side.LEFT, RobotMap.CargoShipPort.NEAR));

        this.csrightcargo = new JoystickButton(driveJoystick, 10);
        this.csrightcargo.whenPressed(new CSSideCargoAuton(RobotMap.Side.RIGHT, RobotMap.CargoShipPort.NEAR));

        this.killSwitch = new JoystickButton(driveJoystick, 5);
        this.killSwitch.whenPressed(new ClearSubsystemCommand());

        this.lightRingButton = new JoystickButton(driveJoystick, 3);
        this.lightRingButton.whenPressed(new ToggleLightRingCommand());
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