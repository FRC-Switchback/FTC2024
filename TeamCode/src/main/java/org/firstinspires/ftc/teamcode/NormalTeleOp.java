package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class NormalTeleOp extends LinearOpMode {
    public ArmSubsystem arm=new ArmSubsystem();
    public teleDrive drive=new teleDrive();
    @Override
    public void runOpMode(){
        drive.init(hardwareMap,false);
        arm.init(hardwareMap,telemetry);
        arm.setState(ArmSubsystem.ArmStates.STOW);

        waitForStart();
        while(opModeIsActive()) {
            arm.printPosistions();
            if (gamepad1.left_trigger > 0.3|| gamepad1.left_bumper) drive.runsqr(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.5);
            else drive.runsqr(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1);
            //if left trigger or bumper is pressed, use slowmode

            if(gamepad2.a)arm.setState(ArmSubsystem.ArmStates.MID);//arm and claw pos
            else if(gamepad2.b)arm.setState(ArmSubsystem.ArmStates.LOW);
            else if(gamepad2.right_bumper)arm.setState(ArmSubsystem.ArmStates.STOW);
            else if(gamepad2.y)arm.setState(ArmSubsystem.ArmStates.HIGH);
            else if(gamepad2.left_bumper)arm.setState(ArmSubsystem.ArmStates.INTAKE);
            telemetry.update();
        }
    }
}

