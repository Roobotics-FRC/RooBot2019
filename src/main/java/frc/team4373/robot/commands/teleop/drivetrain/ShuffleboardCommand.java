package frc.team4373.robot.commands.teleop.drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update ShuffleboardCommand Javadoc.
 */
public class ShuffleboardCommand extends Command {
    private Drivetrain drivetrain;
    private SendableChooser<Integer> chooser;
    public ShuffleboardCommand() {
        requires(this.drivetrain = Drivetrain.getInstance());
        this.chooser = (SendableChooser<Integer>) SmartDashboard.getData("shuffleboardCommandMode");
        System.out.println("Creating shuffleboard command");
    }

    @Override
    protected void initialize() {
        System.out.println("Initializing");
    }

    @Override
    protected void execute() {
        System.out.println("Executing");
        System.out.println(chooser.getSelected());
        switch (chooser.getSelected()) {
            case 1:
                this.drivetrain.setPositionHeadingSetpoints(
                        SmartDashboard.getNumber("positionSetpointRelative", 0),
                        SmartDashboard.getNumber("headingSetpoint", 0));
                break;
            case 2:
                break;
            default:
                break;
        }

        SmartDashboard.putNumber("Pos Setpoint", drivetrain.getTalon(Drivetrain.TalonID.RIGHT_1)
        .getClosedLoopTarget(RobotMap.DRIVETRAIN_POSITION_PID_IDX));
        SmartDashboard.putNumber("Ang Setpoint", drivetrain.getTalon(Drivetrain.TalonID.RIGHT_1)
                .getClosedLoopTarget(RobotMap.DRIVETRAIN_HEADING_PID_IDX));
        SmartDashboard.putNumber("Pos Error", drivetrain.getTalon(Drivetrain.TalonID.RIGHT_1)
                .getClosedLoopError(RobotMap.DRIVETRAIN_HEADING_PID_IDX));
        SmartDashboard.putNumber("Pigeon Ang",
                drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1,
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
        return !SmartDashboard.getBoolean("shuffleboardCommandRunning", false);
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
