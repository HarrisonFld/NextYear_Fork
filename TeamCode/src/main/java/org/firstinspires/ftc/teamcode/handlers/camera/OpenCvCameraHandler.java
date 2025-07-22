package org.firstinspires.ftc.teamcode.handlers.camera;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;

public class OpenCvCameraHandler extends CameraHandler<OpenCvCamera> {

    public OpenCvCameraHandler(OpenCvCamera device) {
        super(device);

        // TODO: Add an option to set a pipeline (when needed)
        //this.pixelDetection = new PixelDetection(this.blue);
        //camera.setPipeline(pixelDetection);

        device.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened() {
                device.startStreaming(432,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                telemetry.addData("Camera error code:", errorCode);
                telemetry.update();
            }
        });

    }

}
