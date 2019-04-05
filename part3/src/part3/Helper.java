package part3;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Helper {

    static List<Image> readData(String filename) {
        List<Image> images = new ArrayList<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(filename));
            String currentLine = in.readLine();

            while (currentLine != null) {
                //Reading image types
                String type = currentLine;

                //Reading image category
                currentLine = in.readLine();
                assert currentLine.startsWith("#");
                String category = currentLine;

                //Reading size of the image
                currentLine = in.readLine();
                String[] splitDimension = currentLine.split("\\s+");
                int row = Integer.parseInt(splitDimension[0]);
                int col = Integer.parseInt(splitDimension[1]);
                int[][] pixels = new int[row][col];

                //Concatenating the string of pixels
                currentLine = in.readLine();
                currentLine += in.readLine();

                //Adding the pixels to the image array
                char[] imagePix = currentLine.toCharArray();
                assert imagePix.length == row*col;
                int idx = 0;
                for (int i=0; i<row; i++) {
                    for (int j=0; j<col; j++) {
                        if (imagePix[idx] == 48){
                            pixels[i][j] = 0;
                            idx++;
                        }
                        else if (imagePix[idx] == 49){
                            pixels[i][j] = 1;
                            idx++;
                        }
                    }
                }

                //Add image to list of images
                Image image = new Image(type, col, row, pixels, category);
                images.add(image);

                currentLine = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return images;
    }

}