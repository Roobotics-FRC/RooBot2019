package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * A command group that performs the passed command after the given amount of time.
 */
public class DoLaterAuton extends CommandGroup {
    public DoLaterAuton(double secondsToDelay, Command followingCommand) {
        addSequential(new DelayAuton(secondsToDelay));
        addSequential(followingCommand);
    }
}
