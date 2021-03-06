package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.ApproachVisionTargetAuton;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;
import frc.team4373.robot.commands.auton.intake.CollectCargoAuton;
import frc.team4373.robot.commands.auton.intake.DeployIntakeAuton;
import frc.team4373.robot.commands.auton.intake.ReleaseCargoAuton;
import frc.team4373.robot.commands.auton.lift.SetLiftAuton;

public class RCargoAuton extends CommandGroup {

    /**
     * Constructs a new rocket cargo auton command.
     * @param side the side on which the robot is starting.
     * @param height the height of the cargo port to go for.
     */
    public RCargoAuton(RobotMap.Side side, RobotMap.RocketHeight height) {
        SetLiftAuton.Position pos;
        switch (height) {
            case MIDDLE:
                pos = SetLiftAuton.Position.CARGO_2;
                break;
            default:
                pos = SetLiftAuton.Position.CARGO_1;
                break;
        }
        int angle = 90;
        if (side == RobotMap.Side.LEFT) angle *= -1;
        addParallel(new CollectCargoAuton());
        addSequential(new DriveDistanceAuton(167, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new TurnToAngleAuton(angle));
        addSequential(new DriveDistanceAuton(28, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new MiddleWheelAdjusterAuton());
        addSequential(new SetLiftAuton(pos));
        addSequential(new DeployIntakeAuton());
        addSequential(new ApproachVisionTargetAuton(42));
        addSequential(new ReleaseCargoAuton());
    }
}
