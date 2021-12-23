package application.game.engine;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class PictureFinder {

    /**
     * @Source https://riptutorial.com/opencv/example/22915/template-matching-with-java
     *  es ist etwas abgewandelt aber die Idee und der Ansatz kommt von dort
     */


    private Mat source = null;
    private Mat template = null;

    public static Point matchLoc = null;
    public static int matchX, matchY;

    public PictureFinder(String sourceURL, String templateURL) {

        // Laden der Dateien
        source=Imgcodecs.imread(sourceURL);
        template=Imgcodecs.imread(templateURL);

        Mat outputImage = new Mat();
        int matchMethod = Imgproc.TM_CCOEFF;

        // Die Template-Matching Methode
        Imgproc.matchTemplate(source, template, outputImage, matchMethod);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        matchLoc = mmr.maxLoc;
        matchX = (int)matchLoc.x;
        matchY = (int)matchLoc.y;

        // Ein Rechteck wird um das gefundene Bild im screenshot gezeichnet
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        // Das Bild mit dem Ergebnis wir abgespeichert im Standardverzeichnis des Projektes
        Imgcodecs.imwrite("gefunden.jpg", source);
        System.out.println("Completed.");




    }


}
