package  org.firstinspires.ftc.teamcode;

import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;
import org.firstinspires.ftc.teamcode.handlers.DcMotorExHandler;
import org.firstinspires.ftc.teamcode.handlers.HandlerMap;
import org.firstinspires.ftc.teamcode.handlers.HardwareComponentHandler;
import org.firstinspires.ftc.teamcode.handlers.ServoHandler;
import org.firstinspires.ftc.teamcode.handlers.camera.CameraHandler;
import org.firstinspires.ftc.teamcode.handlers.camera.OpenCvCameraHandler;
import org.firstinspires.ftc.teamcode.handlers.camera.VisionPortalCameraHandler;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

import java.lang.reflect.Field;

public abstract class RobotBase extends LinearOpMode {

    protected final DcMotorExHandler lf_drive;
    protected final DcMotorExHandler lb_drive;
    protected final DcMotorExHandler rf_drive;
    protected final DcMotorExHandler rb_drive;

    protected final DcMotorExHandler lift;

    // Temporarily carry over components from the Into the Deep season
    // for testing for now
    protected final ServoHandler arm;
    protected final ServoHandler claw;

    // Second intake servos
    protected final DcMotorExHandler slide1;
    protected final DcMotorExHandler slide2;
    protected final ServoHandler slideArm;
    protected final CRServo leftRoller;
    protected final CRServo rightRoller;

    protected OpenCvCameraHandler camera;

    protected final String storagePath = Environment.getExternalStorageDirectory().getPath();
    protected final String logsPath = storagePath + "/Logs/";



    public RobotBase() {

        this.rf_drive = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "rf_drive"));
        this.rb_drive = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "rb_drive"));
        this.lf_drive = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "lf_drive"));
        this.lb_drive = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "lb_drive"));

        this.lift = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "lift"));
        this.arm = new ServoHandler(hardwareMap.get(Servo.class, "arm"));
        this.claw = new ServoHandler(hardwareMap.get(Servo.class, "claw"));

        this.slide1 = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "slide1"));
        this.slide2 = new DcMotorExHandler(hardwareMap.get(DcMotorEx.class, "slide2"));

        this.slideArm = new ServoHandler(hardwareMap.get(Servo.class, "slidearm"));
        this.slideArm.setDirection(Servo.Direction.REVERSE);

        this.leftRoller = hardwareMap.get(CRServo.class, "leftroller");
        this.rightRoller = hardwareMap.get(CRServo.class, "rightroller");

        this.lf_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        this.lb_drive.setDirection(DcMotorSimple.Direction.REVERSE);
        this.lift.setDirection(DcMotorSimple.Direction.REVERSE);

        this.arm.scaleRange(0.75, 1);
        this.arm.min();

        this.claw.scaleRange(0.36, 0.46);
        this.claw.max();

        this.registerHandlers();

        //Camera
        //Doesn't work with registerHandlers() currently
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        OpenCvCamera camera = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        this.camera = new OpenCvCameraHandler(camera);
    }

    public void registerHandlers() {
        Field[] fields = getClass().getDeclaredFields();
        for (Field field : fields) {
            if (!HardwareComponentHandler.class.isAssignableFrom(field.getType())) {
                continue;
            }
            HardwareComponentHandler<?> handler;
            try {
                handler = (HardwareComponentHandler<?>) field.get(this);
            } catch (IllegalAccessException e) {
                continue;
            }
            HandlerMap.put(handler.getName(), handler);
        }
    }

    protected boolean isControlled(double control) {
        return Math.abs(control) > 0.1;
    }


}