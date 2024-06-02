package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.IMU.Parameters;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Base;


public class teleDrive {
    private boolean reset = false;
    private IMU imu;
    Base dt = new Base();
    private boolean rc;

    public void init(HardwareMap hardwareMap,boolean rc1){
        rc=rc1;
        // Declare our motors
        // Make sure your ID's match your configuration


        dt.init(hardwareMap);

        // Retrieve the IMU from the hardware map
        imu = hardwareMap.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        Parameters parameters = new Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.LEFT));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);

    }

    public void resetHeading(){imu.resetYaw();}

    public void runsqr(double y1, double x1, double rx1, double topS){

        double y = -y1; // Remember, this is reversed!
        double x = x1 * 1.1; // Counteract imperfect strafing
        double rx = rx1;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.


        y=y*y*y;
        x=x*x*x;
        rx=rx*rx*rx;

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        if(rc) {
            botHeading=0;
        }
        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;
        frontRightPower= Range.scale(frontRightPower,-1,1,-topS,topS);
        frontLeftPower= Range.scale(frontLeftPower,-1,1,-topS,topS);
        backRightPower= Range.scale(backRightPower,-1,1,-topS,topS);
        backLeftPower= Range.scale(backLeftPower,-1,1,-topS,topS);
        dt.setPower(frontLeftPower,backLeftPower,frontRightPower,backRightPower);
    }

    public void run(double y1, double x1, double rx1, double topS){
        double y = -y1; // Remember, this is reversed!
        double x = x1 * 1.1; // Counteract imperfect strafing
        double rx = rx1;

        // This button choice was made so that it is hard to hit on accident,
        // it can be freely changed based on preference.
        // The equivalent button is start on Xbox-style controllers.


        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
        if(rc) {
            botHeading=0;
        }
        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio, but only when
        // at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;
        frontRightPower= Range.scale(frontRightPower,-1,1,-topS,topS);
        frontLeftPower= Range.scale(frontLeftPower,-1,1,-topS,topS);
        backRightPower= Range.scale(backRightPower,-1,1,-topS,topS);
        backLeftPower= Range.scale(backLeftPower,-1,1,-topS,topS);
        dt.setPower(frontLeftPower,backLeftPower,frontRightPower,backRightPower);
    }
}
