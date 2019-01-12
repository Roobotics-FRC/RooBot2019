package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;

/**
 * A command that simply delays for the given amount of time. Should only be used in command groups.
 *
 * @author Samasaur
 */
public class DelayAuton extends Command {
    public DelayAuton(double seconds) {
        setTimeout(seconds);
    }

    @Override
    protected boolean isFinished() {
        return this.isTimedOut();
    }
}
