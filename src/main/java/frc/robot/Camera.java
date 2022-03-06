package frc.robot;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.CvSink;
import edu.wpi.first.cscore.CvSource;
import edu.wpi.first.cscore.UsbCamera;

public class Camera {

    private UsbCamera camera;

    private CvSink cvSink;
    private CvSource outputStream;

    private Mat source;
    private Mat output;

    private Thread cameraThread;

    private boolean runThread;


    private int GRAY = Imgproc.COLOR_BGR2GRAY;

    public void init() {

        camera = CameraServer.startAutomaticCapture();
        camera.setResolution(240, 135);

        cvSink = CameraServer.getVideo();
        outputStream = CameraServer.putVideo("Front Camera", 240, 135);

        source = new Mat();
        output = new Mat();

        cameraThread = new Thread(() -> thread1());

        runThread = true;

    }

    public void start(){
        runThread = true;
        cameraThread.start();

    }

    public void stop(){
        runThread = false;
        cameraThread = null;
    }

    public void thread1(){
        while (!Thread.interrupted() && runThread) {
            if (cvSink.grabFrame(source) == 0) {
                continue;
            }

            Imgproc.cvtColor(source, output, GRAY);
            outputStream.putFrame(output);

        }

    }
    

}
