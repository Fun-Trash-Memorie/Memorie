package application.camera;

public class CamThread extends Thread {

    public void run(){
        System.out.println("CamThread started.");

        Cam cam = new Cam();
        cam.startCam();

    }

}
