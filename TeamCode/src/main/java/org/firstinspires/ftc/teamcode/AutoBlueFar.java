package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.TeamElementPipeline;
import org.firstinspires.ftc.teamcode.Vision;

@Autonomous(group="auto", name="Auto_Blue_Far_side")
public class AutoBlueFar extends LinearOpMode
{
    private static final double TILE = 24.0;
    @Override
    public void runOpMode() throws InterruptedException {
        ClawSubsystem clawSubsystem = new ClawSubsystem();
        clawSubsystem.init(hardwareMap);
        clawSubsystem.setOpen();
        Pose2d startPose =  new Pose2d(-TILE*1.5, 2.5*TILE, Math.toRadians(-90));
        SampleMecanumDrive drive=new SampleMecanumDrive(hardwareMap);


        TrajectorySequence Center = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-TILE*1.5, 1.457*TILE))
                .addTemporalMarker(1.8, () -> {

                })
                .lineToLinearHeading(new Pose2d(TILE*-1.5, 0.515*TILE, Math.toRadians(-90)))
                .turn(Math.toRadians(180))
                .waitSeconds(3)
                .lineToLinearHeading(new Pose2d(TILE*-1.5, (0.515*TILE)-3, Math.toRadians(-90)))
                .turn(Math.toRadians(90))
                .waitSeconds(9)
                .lineToLinearHeading(new Pose2d(TILE*1.5, (0.5*TILE)-1, Math.toRadians(0)))
                .addDisplacementMarker(() -> {

                })
                .lineToLinearHeading(new Pose2d((TILE*2.245)+1.25, 1.7*TILE, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(TILE*1.8, 0.5*TILE))
                .addDisplacementMarker(() -> {

                })
                .lineToConstantHeading(new Vector2d(TILE*1.8, 0.499*TILE))
                .build();
        /////////////////


        TrajectorySequence Left = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-TILE*1.5, 1.45*TILE))
                .turn(Math.toRadians(95))
                .waitSeconds(2)
                .turn(Math.toRadians(-95))
                .addTemporalMarker(1.4, () -> {

                })
                .lineToLinearHeading(new Pose2d(TILE*-1.5, (0.5*TILE)-3, Math.toRadians(0)))
                .waitSeconds(13)
                .lineToLinearHeading(new Pose2d(TILE*1.5, (0.5*TILE)-3, Math.toRadians(0)))
                .addDisplacementMarker(() -> {

                })
                .lineToLinearHeading(new Pose2d((TILE*2.242)+1.25, (1.9*TILE)+3, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(TILE*1.8, 0.5*TILE))
                .addDisplacementMarker(() -> {

                })
                .lineToConstantHeading(new Vector2d(TILE*1.8, 0.499*TILE))
                .build();

        TrajectorySequence Right = drive.trajectorySequenceBuilder(startPose)
                .lineToConstantHeading(new Vector2d(-TILE*1.44, 1.45*TILE))
                .turn(Math.toRadians(-110))
                .waitSeconds(2)
                .turn(Math.toRadians(110))
                .addTemporalMarker(1.5, () -> {

                })
                .lineToLinearHeading(new Pose2d(TILE*-1.5, (0.5*TILE)-3, Math.toRadians(0)))
                .waitSeconds(13)
                .lineToLinearHeading(new Pose2d(TILE*1.5, (0.5*TILE)-3, Math.toRadians(0)))
                .addDisplacementMarker(() -> {

                })
                .lineToLinearHeading(new Pose2d((TILE*2.245)+1.25, 1.25*TILE, Math.toRadians(0)))
                .lineToConstantHeading(new Vector2d(TILE*1.8, (0.5*TILE)-1))
                .addDisplacementMarker(() -> {

                })
                .lineToConstantHeading(new Vector2d(TILE*1.8, 0.499*TILE))
                .build();



        TeamElementPipeline.MarkerPosistion markerPosistion;
        Vision.startStreaming(hardwareMap, telemetry);
        markerPosistion = TeamElementPipeline.MarkerPosistion.CENTER;
        while(opModeInInit()) {
            markerPosistion = Vision.determineMarkerPosistion();
            if(gamepad1.a) {

            }

        }
        ////////////////////////////////////////////


        waitForStart();
        Vision.webcam.stopStreaming();
        switch (markerPosistion) {
            case CENTER:
            case UNKNOWN:
                drive.followTrajectorySequenceAsync(Center);
                break;
            case RIGHT:
                drive.followTrajectorySequenceAsync(Right);
                break;
            case LEFT:
                drive.followTrajectorySequenceAsync(Left);
                break;
        }

        /*telemetry.addData("DONE 2",0);
        telemetry.update();*/
        while(!isStopRequested()) {
            drive.update();
        }
    }
}