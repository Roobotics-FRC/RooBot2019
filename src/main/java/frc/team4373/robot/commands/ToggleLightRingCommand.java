package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

public class ToggleLightRingCommand extends Command {
    private Drivetrain drivetrain;
    private boolean state = true;

    public ToggleLightRingCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        this.drivetrain.enableLightRing(state);
        if (OI.getOI().getDriveJoystick().getPOV() == 90) {
            state = !state;
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {

    }
}
