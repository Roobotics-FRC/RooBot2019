package frc.team4373.robot.commands.auton.sequences;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team4373.robot.RobotMap;
import frc.team4373.robot.commands.auton.VisionRotatorAuton;
import frc.team4373.robot.commands.auton.*;

public class CSFrontHatchAuton extends CommandGroup {
    /**
     * Constructs a new auton command to place a hatch panel on the front cargo ship hatch.
     */
    public CSFrontHatchAuton() {
        addSequential(new DriveDistanceAuton(120, RobotMap.AUTON_LONG_DRIVE_SPEED));
        addSequential(new VisionRotatorAuton());
        addSequential(new ApproachVisionTargetAuton(24));
    }
}
