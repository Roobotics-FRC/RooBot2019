package frc.team4373.robot.commands.teleop.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update ShuffleboardCommand Javadoc.
 *
 * @author Samasaur
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
                this.drivetrain.setBoth(-SmartDashboard.getNumber("shuffleboardCommandPower", 0));
                break;
            case 2:
                this.drivetrain.setRight(SmartDashboard.getNumber("shuffleboardCommandPower", 0));
                this.drivetrain.setLeft(-SmartDashboard.getNumber("shuffleboardCommandPower", 0));
                break;
            default:
                break;
        }
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
