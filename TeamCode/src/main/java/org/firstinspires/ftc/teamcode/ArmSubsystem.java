package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ArmSubsystem {
    private DcMotor jointOne;
    private DcMotor jointTwo;
    private Servo wrist;
    public enum ArmStates{
        INTAKE,
        STOW,
        LOW,
        MID,
        HIGH
    }
    public static int joint1IntakePos=0;
    public static int joint2IntakePos=0;
    public static int wristIntakePos=0;

    public static int joint1StowPos=0;
    public static int joint2StowPos=0;
    public static int wristStowPos=0;

    public static int joint1PlaceLow=0;
    public static int joint2PlaceLow=0;
    public static int wristPLaceLow=0;

    public static int joint1PlaceMid=0;
    public static int joint2PlaceMid=0;
    public static int wristPLaceMid=0;

    public static int joint1PlaceHigh=0;
    public static int joint2PlaceHigh=0;
    public static int wristPLaceHigh=0;
    Telemetry telemetry;

    public void init(HardwareMap hwmap, Telemetry telemetry){
        this.telemetry=telemetry;//fun hardware mapping
        jointOne=hwmap.get(DcMotor.class, "firstJoint");
        jointTwo=hwmap.get(DcMotor.class,"secondJoint");
        wrist=hwmap.get(Servo.class,"wrist");
        jointOne.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        jointOne.setTargetPosition(0);
        jointOne.setPower(0);
        jointOne.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        jointTwo.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        jointTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        jointTwo.setTargetPosition(0);
        jointTwo.setPower(0);
        jointTwo.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //i rly dont wanna tune pid so im hoping to make built in work
        //if its just a little jiggle ill tune the built in bc easier
    }
    public void setState(ArmStates state){
        jointOne.setPower(1);
        jointTwo.setPower(1);
        switch (state){//massive state machine, not fsm tho
            case STOW:
                jointOne.setTargetPosition(joint1StowPos);
                jointTwo.setTargetPosition(joint2StowPos);
                wrist.setPosition(wristStowPos);
                break;
            case INTAKE:
                jointOne.setTargetPosition(joint1IntakePos);
                jointTwo.setTargetPosition(joint2IntakePos);
                wrist.setPosition(wristIntakePos);
                break;
            case LOW:
                jointOne.setTargetPosition(joint1PlaceLow);
                jointTwo.setTargetPosition(joint2PlaceLow);
                wrist.setPosition(wristPLaceLow);
                break;
            case MID:
                jointOne.setTargetPosition(joint1PlaceMid);
                jointTwo.setTargetPosition(joint2PlaceMid);
                wrist.setPosition(wristPLaceMid);
                break;
            case HIGH:
                jointOne.setTargetPosition(joint1PlaceHigh);
                jointTwo.setTargetPosition(joint2PlaceHigh);
                wrist.setPosition(wristPLaceHigh);
                break;

        }
    }
    public void printPosistions(){
        int[] pos={jointOne.getCurrentPosition(),jointTwo.getCurrentPosition()};//too lazy to make it not an array
        telemetry.addData("first joint", pos[0]);
        telemetry.addData("second joint", pos[1]);
    }
}
