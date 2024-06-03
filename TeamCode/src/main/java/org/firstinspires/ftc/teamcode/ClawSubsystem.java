package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ClawSubsystem {
    private Servo rightClaw;
    private Servo leftClaw;
    private static boolean open=false;

    public void init(HardwareMap hardwareMap){
        rightClaw=hardwareMap.get(Servo.class, "rightServo");
        rightClaw=hardwareMap.get(Servo.class, "leftServo");
        set();
    }

    public void setOpen(){
        rightClaw.setPosition(1);
        leftClaw.setPosition(0);//open it
        open=true;
    }

    public void setClosed(){
        rightClaw.setPosition(0);//close it
        leftClaw.setPosition(1);
        open=false;
    }

    private void set(){
        if(open){
            setOpen();//do whatever its last state was
        }else{
            setClosed();
        }
    }
    public void toggle(){
        if(open)setClosed();//if it was open, make it not
        else setOpen();
    }
}
