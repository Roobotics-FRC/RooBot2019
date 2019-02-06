package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * Turns to a given angle using PID closed-loop control.
 */
public class TurnToAngleAuton extends PIDCommand {
    private double setpoint;
    private Drivetrain drivetrain;
    private double speed;

    private boolean finished = false;
    private boolean coolingDown = false;
    private long cooldownStart = 0;
    private static final long COOLDOWN_TIME = 500;
    private static final double COOLDOWN_THRESHOLD = RobotMap.AUTON_TURN_SPEED * 0.25;

    /**
     * Constructs a TurnToPosition command.
     * @param angle A position, from -180 to 180°, to which to turn.
     */
    public TurnToAngleAuton(double angle, double speed) {
        super("TurnToAngleAuton", RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD);
        this.setpoint = angle;
        this.speed = speed;
        requires(this.drivetrain = Drivetrain.getInstance());
        setInterruptible(true);
    }

    @Override
    protected void initialize() {
        this.setSetpoint(setpoint);
        this.setInputRange(-180, 180);
        this.getPIDController().setOutputRange(-RobotMap.AUTON_TURN_SPEED, RobotMap.AUTON_TURN_SPEED);
        this.getPIDController().setPID(RobotMap.DRIVETRAIN_ANG_PID_GAINS.kP,
                RobotMap.DRIVETRAIN_ANG_PID_GAINS.kI, RobotMap.DRIVETRAIN_ANG_PID_GAINS.kD);

        // timeout fallback if finished checking is unreliable
        // this.setTimeout(2);
    }

    @Override
    protected double returnPIDInput() {
        return drivetrain.getPigeonYaw();
    }

    @Override
    protected void usePIDOutput(double output) {
        if (Math.abs(output) < COOLDOWN_THRESHOLD) {
            this.coolingDown = true;
            this.cooldownStart = System.currentTimeMillis();
        }
        if (coolingDown) {
            if (System.currentTimeMillis() - COOLDOWN_TIME > this.cooldownStart) {
                this.finished = true;
                this.drivetrain.setBoth(0);
                return;
            }
        }
        this.drivetrain.setLeft(-output);
        this.drivetrain.setRight(output);
    }

    @Override
    protected boolean isFinished() {
        return this.finished;

        // timeout fallback if finished checking is unreliable
        // return this.finished || this.isTimedOut();
    }

    @Override
    protected void end() {
        this.getPIDController().reset();
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
