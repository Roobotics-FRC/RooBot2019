package frc.team4373.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team4373.robot.commands.auton.sequences.CSFrontHatchAuton;
import frc.team4373.robot.commands.auton.sequences.CSSideCargoAuton;
import frc.team4373.robot.commands.auton.sequences.CSSideHatchAuton;
import frc.team4373.robot.commands.auton.sequences.DriveForwardAuton;
import frc.team4373.robot.subsystems.Drivetrain;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    private SendableChooser<Integer> shuffleboardCommandMode;
    private SendableChooser<String> objectiveChooser = new SendableChooser<>();
    private SendableChooser<String> positionChooser = new SendableChooser<>();

    Map<String, String> autonEntries;

    /**
     * Constructor for the Robot class. Variable initialization occurs here;
     * WPILib-related setup should occur in {@link #robotInit}.
     */
    public Robot() {
        autonEntries = new LinkedHashMap<>();
        autonEntries.put("Drive Forward", "drive");
        autonEntries.put("CS Side Cargo 1", "cs.cargo.s1");
        autonEntries.put("CS Side Cargo 2", "cs.cargo.s2");
        autonEntries.put("CS Side Cargo 3", "cs.cargo.s3");
        autonEntries.put("CS Side Hatch 1", "cs.hatch.s1");
        autonEntries.put("CS Side Hatch 2", "cs.hatch.s2");
        autonEntries.put("CS Side Hatch 3", "cs.hatch.s3");
        autonEntries.put("CS Front Hatch", "cs.hatchF");
        autonEntries.put("R Cargo Low", "r.cargo.low");
        autonEntries.put("R Cargo Med", "r.cargo.med");
        autonEntries.put("R Cargo Hi", "r.cargo.hi");
        autonEntries.put("R Hatch 1 Low", "r.hatch.near.low");
        autonEntries.put("R Hatch 1 Mid", "r.hatch.near.mid");
        autonEntries.put("R Hatch 1 Hi", "r.hatch.near.hi");
        autonEntries.put("R Hatch 2 Low", "r.hatch.far.low");
        autonEntries.put("R Hatch 2 Mid", "r.hatch.far.mid");
        autonEntries.put("R Hatch 2 Hi", "r.hatch.far.hi");
    }

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit() {
        Drivetrain.getInstance();

        shuffleboardCommandMode = new SendableChooser<>();
        shuffleboardCommandMode.setDefaultOption("Off", 0);
        shuffleboardCommandMode.addOption("DualPID", 1);
        shuffleboardCommandMode.addOption("PositionOnly", 2);

        SmartDashboard.putData("shuffleboardCommandMode", shuffleboardCommandMode);

        SmartDashboard.putNumber("positionSetpointRelative", 0);
        SmartDashboard.putNumber("headingSetpoint", 0);
        SmartDashboard.putBoolean("shuffleboardCommandRunning", false);

        SmartDashboard.putNumber("kP", 0);
        SmartDashboard.putNumber("kI", 0);
        SmartDashboard.putNumber("kD", 0);

        // Populate dashboard
        boolean isFirstEntry = true;
        for (Map.Entry<String, String> entry: autonEntries.entrySet()) {
            if (isFirstEntry) {
                objectiveChooser.setDefaultOption(entry.getKey(), entry.getValue());
                isFirstEntry = false;
            } else {
                objectiveChooser.addOption(entry.getKey(), entry.getValue());
            }
        }
        SmartDashboard.putData("Objective", objectiveChooser);

        positionChooser.setDefaultOption("Center", "center");
        positionChooser.addOption("Left", "left");
        positionChooser.addOption("Right", "right");
        SmartDashboard.putData("Start Pos", positionChooser);

        SmartDashboard.putBoolean("Auton OK", true);
        SmartDashboard.putString("Activated Auton", "None");
    }

    /**
     * This function is called every robot packet, no matter the mode. Use
     * this for items like diagnostics that you want run during disabled,
     * autonomous, teleoperated, and test.
     *
     * <p>This runs after the mode-specific periodic functions but before
     * LiveWindow and SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        SmartDashboard.putNumber("R Pow",
                Drivetrain.getInstance().getOutputPercent(Drivetrain.TalonID.RIGHT_1));
        SmartDashboard.putNumber("L Pow",
                Drivetrain.getInstance().getOutputPercent(Drivetrain.TalonID.LEFT_1));
    }

    /**
     * This function is called once when Sandstorm mode starts.
     */
    @Override
    public void autonomousInit() {
        RobotMap.Side pos;
        switch (positionChooser.getSelected()) {
            case "left":
                pos = RobotMap.Side.LEFT;
                break;
            case "right":
                pos = RobotMap.Side.RIGHT;
                break;
            default:
                pos = RobotMap.Side.MIDDLE;
        }
        String objective = objectiveChooser.getSelected();
        String[] components = objective.split("\\.");

        if (pos == RobotMap.Side.MIDDLE) {
            if (objective.equals("cs.hatchF")) {
                SmartDashboard.putString("Activated Auton", "CS Front Hatch");
            } else if (objective.equals("drive")) {
                SmartDashboard.putString("Activated Auton", "Drive");
            }
        } else if (objective.equals("drive")) {
            SmartDashboard.putString("Activated Auton", "Drive");
        } else if (components.length > 2) {
            switch (components[0]) {
                case "cs":
                    RobotMap.CargoShipPort port;
                    switch (components[2]) {
                        case "s1":
                            port = RobotMap.CargoShipPort.NEAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Near");
                            break;
                        case "s2":
                            port = RobotMap.CargoShipPort.MIDDLE;
                            SmartDashboard.putString("Activated Auton", "CS Side Middle");
                            break;
                        case "s3":
                            port = RobotMap.CargoShipPort.FAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Far");
                            break;
                        default:
                            port = RobotMap.CargoShipPort.NEAR;
                            SmartDashboard.putString("Activated Auton", "CS Side Near");
                    }
                    if (components[1].equals("cargo")) {
                    } else if (components[1].equals("hatch")) {
                    }
                    break;
                case "r":
                    if (components[1].equals("cargo")) {
                        RobotMap.RocketHeight height;
                        switch (components[2]) {
                            case "hi":
                                height = RobotMap.RocketHeight.HIGH;
                                SmartDashboard.putString("Activated Auton", "R Cargo High");
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                SmartDashboard.putString("Activated Auton", "R Cargo Middle");
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                SmartDashboard.putString("Activated Auton", "R Cargo Low");
                                break;
                        }
                    } else if (components[1].equals("hatch") && components.length > 3) {
                        StringBuilder activatedAuton = new StringBuilder();
                        activatedAuton.append("R Hatch");

                        RobotMap.RocketHatchPanel panelLoc;
                        RobotMap.RocketHeight height;
                        if (components[2].equals("far")) {
                            panelLoc = RobotMap.RocketHatchPanel.FAR;
                            activatedAuton.append(" Far ");
                        } else {
                            panelLoc = RobotMap.RocketHatchPanel.NEAR;
                            activatedAuton.append(" Near ");
                        }
                        switch (components[3]) {
                            case "hi":
                                height = RobotMap.RocketHeight.HIGH;
                                activatedAuton.append("High");
                                break;
                            case "mid":
                                height = RobotMap.RocketHeight.MIDDLE;
                                activatedAuton.append("Middle");
                                break;
                            default:
                                height = RobotMap.RocketHeight.LOW;
                                activatedAuton.append("Low");
                                break;
                        }
                        SmartDashboard.putString("Activated Auton", activatedAuton.toString());
                    }
                    break;
                default:
                    SmartDashboard.putString("Activated Auton", "Drive");
            }
        }
    }

    /**
     * This function is called once when teleoperated mode starts.
     */
    @Override
    public void teleopInit() {
    }

    /**
     * This function is called periodically during Sandstorm mode.
     */
    @Override
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("Pidgeon Ang", Drivetrain.getInstance().getPigeonYaw());
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }

    /**
     * This function runs periodically in disabled mode.
     * It is used to verify that the selected auton configuration is legal.
     * <br>
     * <b>UNDER NO CIRCUMSTANCES SHOULD SUBSYSTEMS BE ACCESSED OR ENGAGED IN THIS METHOD.</b>
     */
    @Override
    public void disabledPeriodic() {
        String positionSelection = positionChooser.getSelected();
        RobotMap.Side pos;
        switch (positionSelection) {
            case "left":
                pos = RobotMap.Side.LEFT;
                break;
            case "right":
                pos = RobotMap.Side.RIGHT;
                break;
            case "center":
                pos = RobotMap.Side.MIDDLE;
                break;
            default:
                SmartDashboard.putBoolean("Auton OK", false);
                return;
        }

        String objective = objectiveChooser.getSelected();
        String[] components = objective.split("\\.");
        
        if (components.length == 0) {
            SmartDashboard.putBoolean("Auton OK", false);
            return;
        }

        if (pos == RobotMap.Side.MIDDLE) {
            if (objective.equals("cs.hatchF") || objective.equals("drive")) {
                SmartDashboard.putBoolean("Auton OK", true);
                return;
            } else {
                SmartDashboard.putBoolean("Auton OK", false);
                return;
            }
        } else if (pos == RobotMap.Side.LEFT || pos == RobotMap.Side.RIGHT) {
            if (!objective.equals("cs.hatchF") && autonEntries.containsValue(objective)) {
                SmartDashboard.putBoolean("Auton OK", true);
                return;
            } else {
                SmartDashboard.putBoolean("Auton OK", false);
                return;
            }
        }

        SmartDashboard.putBoolean("Auton OK", false);
    }
}
