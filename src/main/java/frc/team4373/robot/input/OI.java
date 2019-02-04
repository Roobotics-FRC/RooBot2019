package frc.team4373.robot.input;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.MotionProfileCommand;
import frc.team4373.robot.commands.PerformanceTestingCommand;
import frc.team4373.robot.commands.auton.VisionRotatorAuton;
import frc.team4373.robot.commands.profiles.MotionProfile;
import frc.team4373.robot.commands.profiles.StraightProfileLeft;
import frc.team4373.robot.commands.profiles.StraightProfileRight;
import frc.team4373.robot.commands.teleop.drivetrain.ShuffleboardCommand;
import frc.team4373.robot.input.filters.FineGrainedPiecewiseFilter;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * OI provides access to operator interface devices.
 */
public class OI {
    private static OI oi = null;
    private RooJoystick<FineGrainedPiecewiseFilter> driveJoystick;
    private RooJoystick operatorJoystick;
    private Button shuffleboardCommandButton;
    private Button mpButton;
    private Button perfButton;
    private JoystickButton visRotButton;

    private OI() {
        this.driveJoystick =
                new RooJoystick<>(RobotMap.DRIVE_JOYSTICK_PORT, new FineGrainedPiecewiseFilter());
        this.operatorJoystick =
                new RooJoystick<>(RobotMap.OPERATOR_JOYSTICK_PORT,
                        new FineGrainedPiecewiseFilter());
        shuffleboardCommandButton = new JoystickButton(driveJoystick, 11);
        shuffleboardCommandButton.whenPressed(new ShuffleboardCommand());
        this.mpButton = new JoystickButton(this.driveJoystick, 5);
        this.mpButton.whenPressed(new MotionProfileCommand(
                new Drivetrain.TalonID[] {Drivetrain.TalonID.RIGHT_1, Drivetrain.TalonID.LEFT_1},
                new MotionProfile[] {new StraightProfileRight(), new StraightProfileLeft()}));
        this.perfButton = new JoystickButton(driveJoystick, 12);
        this.perfButton.whenPressed(new PerformanceTestingCommand());
        this.visRotButton = new JoystickButton(driveJoystick, 9);
        this.visRotButton.whenPressed(new VisionRotatorAuton());
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