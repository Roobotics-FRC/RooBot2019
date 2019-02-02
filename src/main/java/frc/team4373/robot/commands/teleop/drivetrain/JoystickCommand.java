package frc.team4373.robot.commands.teleop.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A command that controls the drivetrain via the joystick.
 *
 * @author Samasaur
 */
public class JoystickCommand extends Command {
    private Drivetrain drivetrain;

    public JoystickCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
//        double x = OI.getOI().getDriveJoystick().rooGetX(); //not used due to removal of H-wheel
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = Math.signum(OI.getOI().getDriveJoystick().rooGetZ())
                * Math.sqrt(Math.abs(OI.getOI().getDriveJoystick().rooGetZ())) / 2;
        drivetrain.setRight(y + z);
        drivetrain.setLeft(y - z);

        SmartDashboard.putNumber("Pigeon Ang",
                drivetrain.getSensorPosition(Drivetrain.TalonID.LEFT_2,
                        RobotMap.DRIVETRAIN_HEADING_PID_IDX));
        SmartDashboard.putNumber("Right Pos",
                drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1,
                        RobotMap.DRIVETRAIN_POSITION_PID_IDX));
        SmartDashboard.putNumber("Left Pos",
                drivetrain.getSensorPosition(Drivetrain.TalonID.LEFT_1,
                        RobotMap.DRIVETRAIN_POSITION_PID_IDX));
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
