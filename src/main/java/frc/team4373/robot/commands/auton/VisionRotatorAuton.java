package frc.team4373.robot.commands.auton;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.subsystems.Drivetrain;

/**
 * A Javadoc template. TODO: Update VisionRotatorAuton Javadoc.
 */
public class VisionRotatorAuton extends Command {
    private Drivetrain drivetrain;
    private boolean awaitingFinish;
    private boolean finished;
    private long finishWaitStart;

    public VisionRotatorAuton() {
        requires(this.drivetrain = Drivetrain.getInstance());
    }

    @Override
    protected void initialize() {
        this.finished = false;
        this.awaitingFinish = false;
    }

    @Override
    protected void execute() {
        double offset = SmartDashboard.getNumber("vision_angle_offset", 0);
        this.drivetrain.setRight(RobotMap.VISION_ROTATOR_SPEED * Math.signum(offset));
        this.drivetrain.setLeft(-RobotMap.VISION_ROTATOR_SPEED * Math.signum(offset));
        if (Math.abs(offset) < 5) {
            if (awaitingFinish) {
                if (RobotController.getFPGATime() * 1000 - 3000000 > finishWaitStart) finished = true;
            } else {
                this.finishWaitStart = RobotController.getFPGATime() * 1000;
                this.awaitingFinish = true;
            }
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
