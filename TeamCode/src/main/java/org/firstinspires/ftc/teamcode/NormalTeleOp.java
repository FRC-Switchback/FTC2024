package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class NormalTeleOp extends LinearOpMode {
    private final ArmSubsystem arm=new ArmSubsystem();
    private final teleDrive drive=new teleDrive();
    private final ClawSubsystem claw=new ClawSubsystem();
    private boolean lastPress=false;
    @Override
    public void runOpMode(){
        GamepadEx ijustneedonetrigger=new GamepadEx(gamepad2);
        Servo drone=hardwareMap.get(Servo.class, "drone");
        TriggerAnalogButton rightTrigger=new TriggerAnalogButton(ijustneedonetrigger, GamepadKeys.Trigger.RIGHT_TRIGGER,0.5);
        claw.init(hardwareMap);
        drive.init(hardwareMap,false);
        arm.init(hardwareMap,telemetry);
        arm.setState(ArmSubsystem.ArmStates.STOW);
        drone.setPosition(0);
        waitForStart();
        while(opModeIsActive()) {
            if(gamepad2.start)drone.setPosition(1);
            arm.printPosistions();
            if (gamepad1.left_trigger > 0.3|| gamepad1.left_bumper) drive.runsqr(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 0.5);
            else drive.runsqr(gamepad1.left_stick_y, gamepad1.left_stick_x, gamepad1.right_stick_x, 1);
            //if left trigger or bumper is pressed, use slowmode

            if(gamepad2.a)arm.setState(ArmSubsystem.ArmStates.MID);//arm and claw pos
            else if(gamepad2.b)arm.setState(ArmSubsystem.ArmStates.LOW);
            else if(gamepad2.dpad_up)arm.setState(ArmSubsystem.ArmStates.STOW);
            else if(gamepad2.y)arm.setState(ArmSubsystem.ArmStates.HIGH);
            else if(gamepad2.left_bumper){arm.setState(ArmSubsystem.ArmStates.INTAKE);claw.setOpen();}
            else if(gamepad2.dpad_left)arm.setState(ArmSubsystem.ArmStates.HANG_PLACE);
            else if(gamepad2.dpad_right)arm.setState(ArmSubsystem.ArmStates.HANG);

            if(rightTrigger.get()&&!lastPress)claw.toggle();//claw toggle
            lastPress= rightTrigger.get();
            telemetry.update();
        }
    }
}

