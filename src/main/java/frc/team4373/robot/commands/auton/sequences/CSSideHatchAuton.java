package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.drive.ApproachVisionTargetAuton;
import frc.team4373.robot.commands.auton.drive.DriveDistanceAuton;
import frc.team4373.robot.commands.auton.drive.MiddleWheelAdjusterAuton;
import frc.team4373.robot.commands.auton.drive.TurnToAngleAuton;
import frc.team4373.robot.commands.auton.elemental.CollectHatchPanelAuton;
import frc.team4373.robot.commands.auton.elemental.DeployIntakeAuton;
import frc.team4373.robot.commands.auton.elemental.ReleaseHatchPanelAuton;
import frc.team4373.robot.commands.auton.elemental.SetLiftAuton;

public class CSSideHatchAuton extends CommandGroup {
    /**
     * Constructs a new CSSideHatchAuton command group.
     * @param side the side on which the robot is starting (right or left ONLY).
     * @param portLoc the hatch (near, middle, far) to go for.
     */
    public CSSideHatchAuton(RobotMap.Side side, RobotMap.CargoShipPort portLoc) {
        double angleTurn = 90;
        double driveDistance;
        switch (side) {
            case RIGHT:
                angleTurn *= -1;
                break;
            default:
                angleTurn *= 1;
                break;
        }
        switch (portLoc) {
            case NEAR:
                driveDistance = 156;
                break;
            case MIDDLE:
                driveDistance = 177;
                break;
            case FAR:
                driveDistance = 198;
                break;
            default:
                return;
        }
        addParallel(new CollectHatchPanelAuton());
        addSequential(new DriveDistanceAuton(driveDistance, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new TurnToAngleAuton(angleTurn));
        addSequential(new MiddleWheelAdjusterAuton());
        addSequential(new ApproachVisionTargetAuton(42));
        addSequential(new SetLiftAuton(SetLiftAuton.Position.HATCH_1));
        addSequential(new DeployIntakeAuton());
        addSequential(new ReleaseHatchPanelAuton());
    }
}
