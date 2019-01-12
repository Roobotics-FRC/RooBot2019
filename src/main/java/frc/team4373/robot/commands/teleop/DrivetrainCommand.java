package frc.team4373.robot.commands.teleop;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A command that controls the drivetrain via the joystick.
 *
 * @author Samasaur
 */
public class DrivetrainCommand extends Command {
    private Drivetrain drivetrain;

    public DrivetrainCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing Command");
    }

    @Override
    protected void execute() {
//        double x = OI.getOI().getDriveJoystick().rooGetX(); //not used due to removal of H-wheel
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = Math.signum(OI.getOI().getDriveJoystick().rooGetZ())
                * Math.sqrt(Math.abs(OI.getOI().getDriveJoystick().rooGetZ())) / 2;
        drivetrain.setRight(y + z);
        drivetrain.setLeft(y - z);
    }

    @Override
    protected boolean isFinished() {
        return false;
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
