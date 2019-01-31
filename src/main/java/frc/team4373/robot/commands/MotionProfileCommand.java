package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.commands.profiles.MotionProfile;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A command that executes one or more motion profiles concurrently on specified motors.
 *
 * @author aaplmath
 */
public class MotionProfileCommand extends Command {

    private Drivetrain drivetrain;

    private Drivetrain.TalonID[] talonIDs;
    private MotionProfileFeeder[] feeders;

    private boolean initialized = false;
    private boolean illegalInitialization;

    /**
     * Instantiates a new MotionProfileCommand.
     * @param talonIDs the array of IDs of the motors to use.
     * @param profs the profiles to use with the motors at the corresponding indices.
     */
    public MotionProfileCommand(Drivetrain.TalonID[] talonIDs, MotionProfile[] profs) {
        illegalInitialization = talonIDs.length != profs.length;
        requires(this.drivetrain = Drivetrain.getInstance());
        this.talonIDs = talonIDs;
        this.feeders = new MotionProfileFeeder[talonIDs.length];
        for (int i = 0; i < talonIDs.length; ++i) {
            this.feeders[i] = new MotionProfileFeeder(
                    this.drivetrain.getTalon(this.talonIDs[i]), profs[i]);
        }
    }

    @Override
    public void execute() {
        for (int i = 0; i < feeders.length; ++i) {
            if (!initialized) {
                this.feeders[i].reset();
                this.feeders[i].start();
            } else {
                this.drivetrain.setMotionProfileValue(talonIDs[i],
                        feeders[i].getSetValue());
            }
            feeders[i].control();
            SmartDashboard.putNumber("Feeder " + i + " state",
                    this.feeders[i].getSetValue().value);
            SmartDashboard.putNumber(i + " right pt vel", this.drivetrain
                    .getTalon(Drivetrain.TalonID.RIGHT_1).getActiveTrajectoryVelocity());
            SmartDashboard.putNumber(i + " left pt vel", this.drivetrain
                    .getTalon(Drivetrain.TalonID.LEFT_1).getActiveTrajectoryVelocity());
        }
        initialized = true;

        SmartDashboard.putNumber("Left1/Power",
                this.drivetrain.getOutputPercent(Drivetrain.TalonID.LEFT_1));
        SmartDashboard.putNumber("Left2/Power",
                this.drivetrain.getOutputPercent(Drivetrain.TalonID.LEFT_2));
        SmartDashboard.putNumber("Right1/Power",
                this.drivetrain.getOutputPercent(Drivetrain.TalonID.RIGHT_1));
        SmartDashboard.putNumber("Right2/Power",
                this.drivetrain.getOutputPercent(Drivetrain.TalonID.RIGHT_2));
    }

    @Override
    protected boolean isFinished() {
        boolean finished = true;
        for (MotionProfileFeeder feeder : feeders) {
            finished = finished && feeder.isComplete();
        }
        finished = finished || illegalInitialization;
        if (finished) initialized = false; // this lets us reset everything next time
        return finished;
    }

    @Override
    protected void end() {
        this.drivetrain.zeroMotors();
    }

    @Override
    protected void interrupted() {
        end();
    }
}
