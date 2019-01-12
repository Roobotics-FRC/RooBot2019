package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update TimedDriveAuton Javadoc.
 *
 * @author Samasaur
 */
public class TimedDriveAuton extends Command {
    private double start;
    private double time;
    private double power;
    private boolean finished = false;
    private Drivetrain drivetrain;

    public TimedDriveAuton(double seconds, double power) {
        requires(this.drivetrain = Drivetrain.getInstance());
        this.time = seconds;
        this.power = power;
    }

    @Override
    protected void initialize() {
        this.start = Timer.getFPGATimestamp();
    }

    @Override
    protected void execute() {
        if (Timer.getFPGATimestamp() - start >= time) {
            drivetrain.setBoth(0);
            this.finished = true;
        } else {
            drivetrain.setBoth(power);
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
