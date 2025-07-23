package org.firstinspires.ftc.teamcode;

import org.opencv.core.Mat;
import org.wolfsonrobotics.RobotWebServer.communication.CommunicationLayer;
import org.wolfsonrobotics.RobotWebServer.server.RobotWebServer;
import org.wolfsonrobotics.RobotWebServer.server.ServerOpMode;

import java.io.IOException;

public class RobotWithServer extends RobotBase implements ServerOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        RobotWebServer server = new RobotWebServer(this);
        try {
            server.start();
        } catch (IOException e) {
            server.stop();
            throw new RuntimeException(e);
        }
        waitForStart();
        while(opModeIsActive()) {
            telemetry.addLine("Test!");
        }
        server.stop();
        camera.closeCamera();
    }

    @Override
    public String[] getExcludedMethods() {
        return new String[] {"runOpMode", "registerHandlers"};
    }

    @Override
    public Mat getCameraFeed() {
       return camera.getCurrentFrame();
    }
}
