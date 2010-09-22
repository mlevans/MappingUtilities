package maptilestitching;

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
        Main tileStitcher = new Main();
        tileStitcher.run(args);

    }

    public void run(String[] args) {
        File inDir = new File("input/SampleZoomLevel");
        File outFile = new File("output/SampleZoomLevel/map.png");
        File[] fileList = inDir.listFiles();

        int i;
	//Stitching a map with 4 rows and 4 columns; the program is generalized,
	//so you can use higher multiples of 4, such as 8 by 8 and 16 by 16.
        int rows = 4;
        int cols = 4;
        int total = rows * cols;

        BufferedImage images[] = new BufferedImage[total];

        try {
            for (i = 0; i < total; i++){
                File inFile = fileList[i];

                int c1;
                int c2;
		//The code below deals with the naming convention we use
		//for each map tile.
		//See the ReadMe for more on the naming convention.
                String s = inFile.getName().substring(0,2);
                c1 = Integer.parseInt(s);
                s = inFile.getName().substring(3,5);
                c2 = Integer.parseInt(s);

                BufferedImage image = null;

                image = ImageIO.read(inFile);
                images[c1*cols + c2] = image;
            }

        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }

        //Our map tiles are square, with dimensions of 256 by 256 pixels.  With 4 rows and 	//4 columns, we create a 1024 by 1024 image.

        BufferedImage outputImage = new BufferedImage(256*cols,256*rows,BufferedImage.TYPE_INT_ARGB_PRE);
        Graphics2D g = outputImage.createGraphics();
	
	//Loop through the rows and columns
        for (i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                g.drawImage(images[i*cols+j], j*256, i*256, 256, 256, null);
            }
        }

        try {
            ImageIO.write(outputImage, "png", outFile);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(1);
        }
    }

}