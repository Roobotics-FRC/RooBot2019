package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update VisionRotatorAuton Javadoc.
 */
public class VisionRotatorAuton extends Command {
    private Drivetrain drivetrain;
    private boolean finished;

    public VisionRotatorAuton() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {

    }

    @Override
    protected void execute() {
        double offset = SmartDashboard.getNumber("vision_angle_offset", 0);
        if (offset < -3) {
            this.drivetrain.setRight(RobotMap.VISION_ROTATOR_SPEED);
            this.drivetrain.setLeft(-RobotMap.VISION_ROTATOR_SPEED);
        } else if (offset > 3) {
            this.drivetrain.setRight(-RobotMap.VISION_ROTATOR_SPEED);
            this.drivetrain.setLeft(RobotMap.VISION_ROTATOR_SPEED);
        } else {
            this.finished = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.finished;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        this.end();
    }
}
