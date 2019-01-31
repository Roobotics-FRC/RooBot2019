package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
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
    public enum TalonID {
        RIGHT_1, RIGHT_2, LEFT_1, LEFT_2
    }

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

        this.right2.follow(right1);
        this.left2.follow(left1);

        this.left1.setInverted(false);
        this.left2.setInverted(false);
        this.right1.setInverted(true);
        this.right2.setInverted(true);

        this.right1.configNominalOutputForward(0, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configNominalOutputReverse(0, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configPeakOutputForward(1, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configPeakOutputReverse(-1, RobotMap.TALON_TIMEOUT_MS);

        this.left1.configNominalOutputForward(0, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configNominalOutputReverse(0, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configPeakOutputForward(1, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configPeakOutputReverse(-1, RobotMap.TALON_TIMEOUT_MS);

        this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                RobotMap.TALON_TIMEOUT_MS);
        this.right1.setSensorPhase(false);
        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
                RobotMap.TALON_TIMEOUT_MS);
        this.left1.setSensorPhase(false);

        this.right1.config_kF(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kF);
        this.right1.config_kP(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kP);
        this.right1.config_kI(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kI);
        this.right1.config_kD(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kD);

        this.left1.config_kF(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kF);
        this.left1.config_kP(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kP);
        this.left1.config_kI(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kI);
        this.left1.config_kD(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kD);
    }

    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;

    public void setLeft(double power) {
        power = safetyCheckSpeed(power);
        this.left1.set(ControlMode.PercentOutput, power);
        this.left2.set(ControlMode.PercentOutput, power);
    }

    public void setRight(double power) {
        power = safetyCheckSpeed(power);
        this.right1.set(ControlMode.PercentOutput, power);
        this.right2.set(ControlMode.PercentOutput, power);
    }

    public void setBoth(double power) {
        this.setLeft(power);
        this.setRight(power);
    }

    /**
     * Sets motion profile control mode on a primary motor using auxiliary output.
     * @param motor the primary motor (must be a "1" motor).
     * @param svmpValue the SetValueMotionProfile value to set.
     */
    public void setMotionProfileValue(TalonID motor, SetValueMotionProfile svmpValue) {
        switch (motor) {
            case RIGHT_1:
                this.right1.set(ControlMode.MotionProfile, svmpValue.value);
                break;
            case LEFT_1:
                this.left1.set(ControlMode.MotionProfile, svmpValue.value);
                break;
            default:
                break;
        }
    }

    public double safetyCheckSpeed(double power) {
        return power > 1 ? 1 : (power < -1 ? -1 : power);
    }

    /**
     * Gets current percent output of Talon.
     * @param talonID Talon to query.
     * @return percent output of talon.
     */
    public double getOutputPercent(TalonID talonID) {
        return getTalon(talonID).getMotorOutputPercent();
    }

    /**
     * Gets a motor controller with the specified ID.
     * @param talonID the ID of the Talon to fetch.
     * @return the specified Talon.
     */
    public WPI_TalonSRX getTalon(TalonID talonID) {
        switch (talonID) {
            case RIGHT_1:
                return this.right1;
            case RIGHT_2:
                return this.right2;
            case LEFT_1:
                return this.left1;
            case LEFT_2:
                return this.left2;
            default: // this case should NEVER be reached; it is just used to prevent NPE warnings
                return this.right1;
        }
    }

    /**
     * Sets motor output to 0.
     */
    public void zeroMotors() {
        this.setBoth(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new JoystickCommand());
    }
}
