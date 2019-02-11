package frc.team4373.robot;

/**
 * Holds various mappings and constants.
 */

public class RobotMap {
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    public static final int LEFT_DRIVE_MOTOR_FRONT = 3;
    public static final int LEFT_DRIVE_MOTOR_REAR = 4;
    public static final int RIGHT_DRIVE_MOTOR_FRONT = 1;
    public static final int RIGHT_DRIVE_MOTOR_REAR = 2;

    public static final int GYRO_CHANNEL = 0;
    public static final double AUTON_LONG_DRIVE_SPEED = 0.5; //Was 1, but was too fast.

    public enum CargoShipPort {
        NEAR, MIDDLE, FAR
    }

    public enum Side {
        LEFT, MIDDLE, RIGHT
    }

    public static class PID {
        public double kF;
        public double kP;
        public double kI;
        public double kD;

        PID(double kF, double kP, double kI, double kD) {
            this.kF = kF;
            this.kP = kP;
            this.kI = kI;
            this.kD = kD;
        }
    }

    public static final int DRIVETRAIN_PID_IDX = 0;
    public static final int DRIVETRAIN_POSITION_PID_IDX = 0;
    public static final int DRIVETRAIN_HEADING_PID_IDX = 1;
    public static final PID DRIVETRAIN_ANG_PID_GAINS = new PID(0, 0.01,0, 0);
    public static final PID DRIVETRAIN_DIST_PID_GAINS = new PID(0, 0.001,0, 0);
    public static final PID DRIVETRAIN_VIS_APPR_PID_GAINS = new PID(0, 0.03,0.001, 0);
//    public static final PID DRIVETRAIN_DIST_PID_GAINS = new PID(0, 0.1,0, 0);
    public static final double AUTON_VISION_APPROACH_SPEED = 0.2;
    public static final int ALLOWABLE_VISION_ERRORS = 3;
    public static final boolean DRIVETRAIN_AUX_PID_POLARITY = false;
    public static final int MOTION_PROFILE_BASE_TRAJ_TIMEOUT = 0;
    public static final boolean DRIVETRAIN_RIGHT_ENCODER_PHASE = true;
    public static final boolean DRIVETRAIN_LEFT_ENCODER_PHASE = true;

    public static final double ENCODER_UNITS_PER_ROTATION = 4096;
    public static final double PIGEON_UNITS_PER_ROTATION = 8192;
    public static final double RESOLUTION_UNITS_PER_ROTATION = 3600;

    public static final double AUTON_TURN_SPEED = 0.25;

    public static final int REMOTE_SENSOR_0 = 0;
    public static final int REMOTE_SENSOR_1 = 1;

    public static final int TALON_TIMEOUT_MS = 1000;

    public static final double VISION_ROTATOR_SPEED = 0.1;
}
