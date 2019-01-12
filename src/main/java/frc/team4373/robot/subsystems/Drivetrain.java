package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.drivetrain.JoystickCommand;

/**
 * A programmatic representation of the robot's drivetrain, which controls robot movement.
 *
 * @author Samasaur
 */
public class Drivetrain extends Subsystem {
    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        return instance == null ? instance = new Drivetrain() : instance;
    }

    private Drivetrain() {
        this.left1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_FRONT);
        this.left2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_REAR);
        this.right1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_FRONT);
        this.right2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_REAR);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);

        this.right1.setInverted(true);
        this.right2.setInverted(true);

        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.left1.setSensorPhase(false);
        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 1000);
        this.right1.setSensorPhase(false);
    }

    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;

    public void setLeft(double power) {
        power = safetyCheckSpeed(power);
        this.left1.set(power);
        this.left2.set(power);
    }

    public void setRight(double power) {
        power = safetyCheckSpeed(power);
        this.right1.set(power);
        this.right2.set(power);
    }

    public void setBoth(double power) {
        this.setLeft(power);
        this.setRight(power);
    }

    public double safetyCheckSpeed(double power) {
        return power > 1 ? 1 : (power < -1 ? -1 : power);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickCommand());
    }
}
