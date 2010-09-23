package maptilepieces;

/**
 *
 * @author Michael Lawrence Evans :: michael@longliveman.com
 *
 */

import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

public class Main {

    public static void main(String[] args) {
        Main tilePiecer = new Main();
        tilePiecer.run(args);
    }

    public void run(String[] args) {
        File inDir = new File("input/SampleZoomLevel");
        File outFile;
        File[] fileList = inDir.listFiles();

        int i;
        BufferedImage image = null;

        try {
                File inFile = fileList[0];

                image = ImageIO.read(inFile);
          
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        //Create 256 by 256 tiles
        
        int nCols = image.getWidth()/256;
        int nRows = image.getHeight()/256;

        BufferedImage outputImage = new BufferedImage(256,256,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = outputImage.createGraphics();

        for (i = 0; i < nRows; i++){
            for (int j = 0; j < nCols; j++){

                BufferedImage tempImage = image.getSubimage(j*256, i*256, 256, 256);
                g.drawImage(tempImage, 0, 0, 256, 256, null);
                
                outFile = new File("output/SampleZoomLevel/" + i +'_'+ j + ".png");

                try {
                    ImageIO.write(outputImage, "png", outFile);
                } catch (IOException e) {
                    System.err.println(e);
                    System.exit(1);
                }
            }
        }

    }

}