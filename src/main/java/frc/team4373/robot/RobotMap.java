package frc.team4373.robot;

/**
 * Holds various mappings and constants.
 *
 * @author aaplmath
 * @author Samasaur
 */

public class RobotMap {
    public static final int DRIVE_JOYSTICK_PORT = 0;
    public static final int OPERATOR_JOYSTICK_PORT = 1;

    public static final int LEFT_DRIVE_MOTOR_FRONT = 3;
    public static final int LEFT_DRIVE_MOTOR_REAR = 4;
    public static final int RIGHT_DRIVE_MOTOR_FRONT = 1;
    public static final int RIGHT_DRIVE_MOTOR_REAR = 2;

    public static final int GYRO_CHANNEL = 0;

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
    public static final PID DRIVETRAIN_PID_GAINS = new PID(0, 1,0, 0);
    public static final int MOTION_PROFILE_BASE_TRAJ_TIMEOUT = 0;
    public static final double ENCODER_UNITS_PER_ROTATION = 4096;

    public static final int TALON_TIMEOUT_MS = 1000;
}
