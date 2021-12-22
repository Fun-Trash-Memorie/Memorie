package application.game.engine;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class findPicture {


    private Mat source=null;
    private Mat template=null;

    public findPicture(String sourceURL, String templateURL) {
        //Load image file
        source=Imgcodecs.imread("temp/" + sourceURL);
        template=Imgcodecs.imread("img/" + templateURL);

        Mat outputImage=new Mat();
        int machMethod= Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);


        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc=mmr.maxLoc;
        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        Imgcodecs.imwrite("gefunden.jpg", source);
        System.out.println("Complated.");
    }


}
