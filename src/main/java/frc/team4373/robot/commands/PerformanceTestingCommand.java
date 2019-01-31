package frc.team4373.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team4373.robot.input.OI;
import frc.team4373.robot.subsystems.Drivetrain;

import java.util.HashMap;

/**
 * Provides joystick-based control of the apparatus with a "ramping" input mechanism that caps
 * acceleration at a specific rate.
 *
 * @author aaplmath
 * @author Samasaur
 */
public class PerformanceTestingCommand extends Command {

    private Drivetrain drivetrain;

    private HashMap<Long, Integer> logs;
    private boolean logged = false;

    public PerformanceTestingCommand() {
        this.logs = new HashMap();
        requires(this.drivetrain = Drivetrain.getInstance());
    }


    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        double y = OI.getOI().getDriveJoystick().rooGetY();
        double z = OI.getOI().getDriveJoystick().rooGetZ();

        double curRight = drivetrain.getOutputPercent(Drivetrain.TalonID.RIGHT_1);
        double curLeft = drivetrain.getOutputPercent(Drivetrain.TalonID.LEFT_1);

        this.drivetrain.setBoth(y);
        if (OI.getOI().getDriveJoystick().getRawButton(7)) {
            this.drivetrain.setRight(y + z);
            this.drivetrain.setLeft(y - z);
        }


        this.logs.put(System.currentTimeMillis(),
                this.drivetrain.getSensorPosition(Drivetrain.TalonID.RIGHT_1));

        // Write out on press of button 10
        if (OI.getOI().getDriveJoystick().getRawButton(10) && !logged) {
            System.out.println("********************************");
            logged = true;
            StringBuilder sb = new StringBuilder();
            logs.forEach((Long time, Integer log) -> {
                sb.append(time.toString());
                sb.append(",");
                sb.append(log.toString());
                sb.append("\n");
            });
            System.out.println(sb.toString());
            System.out.println("********************************");
        }
    }

    @Override
    public void interrupted() {
        this.end();
    }

    @Override
    public void end() {
        this.drivetrain.setBoth(0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

