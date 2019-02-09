package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.*;

public class CSSideCargoAuton extends CommandGroup {
    /**
     * Constructs a new CSSideCargoAuton command group.
     * @param side the side on which the robot is starting (right or left ONLY).
     * @param portLoc the port (near, middle, far) to go for.
     */
    public CSSideCargoAuton(RobotMap.Side side, RobotMap.CargoShipPort portLoc) {
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
                driveDistance = 180;
                break;
            case MIDDLE:
                driveDistance = 201;
                break;
            case FAR:
                driveDistance = 222;
                break;
            default:
                return;
        }
        addSequential(new DriveDistanceAuton(driveDistance, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new TurnToAngleAuton(angleTurn, RobotMap.VISION_ROTATOR_SPEED));
        addSequential(new VisionRotatorAuton());
        addSequential(new ApproachVisionTargetAuton(36));
    }
}
