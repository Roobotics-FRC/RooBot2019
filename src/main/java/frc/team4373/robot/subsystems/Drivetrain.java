package frc.team4373.robot.subsystems;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.teleop.drivetrain.JoystickCommand;

/**
 * A programmatic representation of the robot's drivetrain, which controls robot movement.
 */
public class Drivetrain extends Subsystem {
    public enum TalonID {
        RIGHT_1, RIGHT_2, LEFT_1, LEFT_2
    }

    private static Drivetrain instance;

    public static Drivetrain getInstance() {
        return instance == null ? instance = new Drivetrain() : instance;
    }

    private WPI_TalonSRX left1;
    private WPI_TalonSRX left2;
    private WPI_TalonSRX right1;
    private WPI_TalonSRX right2;
    private PigeonIMU pigeon;

    private Drivetrain() {
        this.left1 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_FRONT);
        this.left2 = new WPI_TalonSRX(RobotMap.LEFT_DRIVE_MOTOR_REAR);
        this.right1 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_FRONT);
        this.right2 = new WPI_TalonSRX(RobotMap.RIGHT_DRIVE_MOTOR_REAR);
        this.pigeon = new PigeonIMU(this.left2);

        this.left1.setNeutralMode(NeutralMode.Brake);
        this.left2.setNeutralMode(NeutralMode.Brake);
        this.right1.setNeutralMode(NeutralMode.Brake);
        this.right2.setNeutralMode(NeutralMode.Brake);

        this.right2.follow(right1);
        this.left2.follow(left1);

        // this.right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
        //         RobotMap.TALON_TIMEOUT_MS);
        // this.right1.setSensorPhase(false);
        // this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0,
        //         RobotMap.TALON_TIMEOUT_MS);
        // this.left1.setSensorPhase(false);
        // this.right1.config_kF(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kF);
        // this.right1.config_kP(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kP);
        // this.right1.config_kI(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kI);
        // this.right1.config_kD(RobotMap.DRIVETRAIN_PID_IDX, RobotMap.DRIVETRAIN_PID_GAINS.kD);

        // Configure left encoder and publish it on Remote Sensor 0
        this.left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                RobotMap.DRIVETRAIN_POSITION_PID_IDX, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configRemoteFeedbackFilter(this.left1.getDeviceID(),
                RemoteSensorSource.TalonSRX_SelectedSensor, RobotMap.REMOTE_SENSOR_0,
                RobotMap.TALON_TIMEOUT_MS);

        // Configure Pigeon on Remote Sensor 1
        this.right1.configRemoteFeedbackFilter(this.pigeon.getDeviceID(),
                RemoteSensorSource.Pigeon_Yaw, RobotMap.REMOTE_SENSOR_1,
                RobotMap.TALON_TIMEOUT_MS);

        // Configure SensorSum
        this.right1.configSensorTerm(SensorTerm.Sum0, FeedbackDevice.RemoteSensor0,
                RobotMap.TALON_TIMEOUT_MS);
        this.right1.configSensorTerm(SensorTerm.Sum1, FeedbackDevice.QuadEncoder,
                RobotMap.TALON_TIMEOUT_MS);

        // Set sum signal as distance sensor input
        this.right1.configSelectedFeedbackSensor(FeedbackDevice.SensorSum,
                RobotMap.DRIVETRAIN_POSITION_PID_IDX, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configSelectedFeedbackCoefficient(0.5, RobotMap.DRIVETRAIN_POSITION_PID_IDX,
                RobotMap.TALON_TIMEOUT_MS);

        // Add Pigeon as feedback sensor for heading
        this.right1.configSelectedFeedbackSensor(FeedbackDevice.RemoteSensor1,
                RobotMap.DRIVETRAIN_HEADING_PID_IDX, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configSelectedFeedbackCoefficient(
                RobotMap.RESOLUTION_UNITS_PER_ROTATION / RobotMap.PIGEON_UNITS_PER_ROTATION,
                RobotMap.DRIVETRAIN_HEADING_PID_IDX, RobotMap.TALON_TIMEOUT_MS);

        // Set inversions and sensor phases
        this.left1.setInverted(false);
        this.left2.setInverted(false);
        this.right1.setInverted(true);
        this.right2.setInverted(true);
        this.right1.setSensorPhase(RobotMap.DRIVETRAIN_RIGHT_ENCODER_PHASE);
        this.left1.setSensorPhase(RobotMap.DRIVETRAIN_LEFT_ENCODER_PHASE);

        // Configuration we don't understand from the sample code
        this.right1.setStatusFramePeriod(StatusFrame.Status_12_Feedback1, 20,
                RobotMap.TALON_TIMEOUT_MS);
        this.right1.setStatusFramePeriod(StatusFrame.Status_13_Base_PIDF0, 20,
                RobotMap.TALON_TIMEOUT_MS);
        this.right1.setStatusFramePeriod(StatusFrame.Status_14_Turn_PIDF1, 20,
                RobotMap.TALON_TIMEOUT_MS);
        this.left1.setStatusFramePeriod(StatusFrame.Status_2_Feedback0, 5,
                RobotMap.TALON_TIMEOUT_MS);
        this.right1.configNeutralDeadband(0.001, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configNeutralDeadband(0.001, RobotMap.TALON_TIMEOUT_MS);

        // Set peak and nominal outputs
        this.right1.configNominalOutputForward(0, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configNominalOutputReverse(0, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configPeakOutputForward(1, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configPeakOutputReverse(-1, RobotMap.TALON_TIMEOUT_MS);

        this.left1.configNominalOutputForward(0, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configNominalOutputReverse(0, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configPeakOutputForward(1, RobotMap.TALON_TIMEOUT_MS);
        this.left1.configPeakOutputReverse(-1, RobotMap.TALON_TIMEOUT_MS);

        // Set PID gains
        this.right1.config_kF(RobotMap.DRIVETRAIN_HEADING_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kF);
        this.right1.config_kP(RobotMap.DRIVETRAIN_HEADING_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kP);
        this.right1.config_kI(RobotMap.DRIVETRAIN_HEADING_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kI);
        this.right1.config_kD(RobotMap.DRIVETRAIN_HEADING_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kD);

        this.right1.config_kF(RobotMap.DRIVETRAIN_POSITION_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kF);
        this.right1.config_kP(RobotMap.DRIVETRAIN_POSITION_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kP);
        this.right1.config_kI(RobotMap.DRIVETRAIN_POSITION_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kI);
        this.right1.config_kD(RobotMap.DRIVETRAIN_POSITION_PID_IDX,
                RobotMap.DRIVETRAIN_PID_GAINS.kD);

        // Set time per PID loop iteration for both loops
        int closedLoopTimeMs = 1;
        this.right1.configClosedLoopPeriod(0, closedLoopTimeMs, RobotMap.TALON_TIMEOUT_MS);
        this.right1.configClosedLoopPeriod(1, closedLoopTimeMs, RobotMap.TALON_TIMEOUT_MS);

        // Polarity
        this.right1.configAuxPIDPolarity(RobotMap.DRIVETRAIN_AUX_PID_POLARITY,
                RobotMap.TALON_TIMEOUT_MS);

        // Set starting position to 0 on sensors
        zeroSensors();
    }

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
     * Sets setpoints for position and heading.
     * @param positionSetpoint the position to achieve relative to the start.
     * @param headingSetpoint the heading to maintain.
     */
    public void setPositionHeadingSetpoints(double positionSetpoint, double headingSetpoint) {
        this.right1.set(ControlMode.Position, positionSetpoint,
                DemandType.AuxPID, headingSetpoint);
        this.left1.follow(this.right1, FollowerType.AuxOutput1);
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

    /**
     * Resets sensor values to 0.
     */
    public void zeroSensors() {
        this.left1.getSensorCollection().setQuadraturePosition(0, RobotMap.TALON_TIMEOUT_MS);
        this.right1.getSensorCollection().setQuadraturePosition(0, RobotMap.TALON_TIMEOUT_MS);
        this.pigeon.setYaw(0, RobotMap.TALON_TIMEOUT_MS);
        this.pigeon.setAccumZAngle(0, RobotMap.TALON_TIMEOUT_MS);
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
     * Gets the position of the sensor associated with the specified Talon.
     * @param talonID the Talon whose sensor position to fetch.
     * @return the position of the specified sensor.
     */
    public int getSensorPosition(TalonID talonID) {
        return getTalon(talonID).getSelectedSensorPosition();
    }

    /**
     * Gets the position of the sensor of the specified Talon on the specified PID loop.
     * @param talonID the Talon whose sensor position to fetch.
     * @param pidIdx the PID loop on which to fetch.
     * @return the position of the specified sensor.
     */
    public int getSensorPosition(TalonID talonID, int pidIdx) {
        return getTalon(talonID).getSelectedSensorPosition(pidIdx);
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
