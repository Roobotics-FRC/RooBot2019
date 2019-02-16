package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

public class ToggleLightRingCommand extends Command {
    private Drivetrain drivetrain;

    public ToggleLightRingCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        this.drivetrain.enableLightRing(!this.drivetrain.getLightRingEnabled());
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }
}
