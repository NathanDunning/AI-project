package part1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * The class that will deal with part one of the assignment
 */
public class NearestNeighbour {

    private static List<Iris> trainingData;
    private static List<Iris> testData;

    private static double sLengthRange;
    private static double sWidthRange;
    private static double pLengthRange;
    private static double pWidthRange;

    private static int k = 3;



    public static Distance calculateDistance(Iris test, Iris train) {
        double sLenCalc = Math.pow(train.getSepalLength() - test.getSepalLength(), 2) / Math.pow(sLengthRange, 2);
        double sWidCalc = Math.pow(train.getSepalWidth() - test.getSepalWidth(), 2) / Math.pow(sWidthRange, 2);
        double pLenCalc = Math.pow(train.getPetalLength() - test.getPetalLength(), 2) / Math.pow(pLengthRange, 2);
        double pWidCalc = Math.pow(train.getPetalWidth() - test.getPetalWidth(), 2) / Math.pow(pWidthRange, 2);
        double dist = Math.sqrt(sLenCalc + sWidCalc + pLenCalc + pWidCalc);
        return new Distance(dist, train);
    }

    public static void outputToFile() {
        try {
            PrintWriter writer = new PrintWriter("Prediction data.txt", "UTF-8");
            int count = 1;
            int correct = 0;
            for (Iris iris : testData) {
                writer.println(iris.getPrediction());
                if(count >= 1 && count <= 25) {
                    if (iris.getPrediction().equals("Iris-setosa")) {
                        correct++;
                    }
                }
                if(count >= 26 && count <= 50){
                    if(iris.getPrediction().equals("Iris-versicolor")){
                        correct++;
                    }
                }
                if(count >= 51 && count <= 75){
                    if(iris.getPrediction().equals("Iris-virginica")){
                        correct++;
                    }
                }
                count++;
            }
            writer.println("Accuracy = " + correct +"/75");

            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }


    /**
     * Reads the file and returns a list containing points of all the Iris
     * @param file
     * @return
     */
    public static List<Iris> readData(String file){
        List<Iris> list = new ArrayList<>();

        //Variables for calculating range
        double SLMax = Double.MIN_VALUE;
        double SLMin = Double.MAX_VALUE;
        double SWMax = Double.MIN_VALUE;
        double SWMin = Double.MAX_VALUE;
        double PLMax = Double.MIN_VALUE;
        double PLMin = Double.MAX_VALUE;
        double PWMax = Double.MIN_VALUE;
        double PWMin = Double.MAX_VALUE;

        try {
            //Reading the data
            BufferedReader in = new BufferedReader(new FileReader(file));
            String currentLine;
            while((currentLine = in.readLine()) != null) {
                String[] data = currentLine.split("\\s+");
                if(data[0].isEmpty()) {break;}

                double sepalLength = Double.parseDouble(data[0]);
                if(sepalLength > SLMax) {SLMax = sepalLength;}
                if(sepalLength < SLMin) {SLMin = sepalLength;}

                double sepalWidth = Double.parseDouble(data[1]);
                if(sepalWidth > SWMax) {SWMax = sepalWidth;}
                if(sepalWidth < SWMin) {SWMin = sepalWidth;}

                double petalLength = Double.parseDouble(data[2]);
                if(petalLength > PLMax) {PLMax = petalLength;}
                if(petalLength < PLMin) {PLMin = petalLength;}

                double petalWidth = Double.parseDouble(data[3]);
                if(petalWidth > PWMax) {PWMax = petalWidth;}
                if(petalWidth < PWMin) {PWMin = petalWidth;}

                list.add(new Iris(sepalLength, sepalWidth,
                       petalLength , petalWidth, data[4]));
            }

            if (file.contains("training")) {
                sLengthRange = SLMax - SLMin;
                sWidthRange = SWMax - SWMin;
                pLengthRange = PLMax - PLMin;
                pWidthRange = PWMax - PWMin;
            }

            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * The method that is used to run the program, the program will require two arguments being filenames.
     * The first argument is the test set as a .txt file
     * The second argument is the training set as a .txt file
     * @param args - Text files to of data sets
     */
    public static void main(String[] args) {
        //Ensure the given files are correct or exist
        try {
            //Try to read to the file
            if (args[0] == null || args[1] == null) {
                throw new FileNotFoundException();
            }
            if ((!args[1].contains("test")) && (!args[0].contains("training"))) {
                throw new AssertionError();
            }
        } catch (FileNotFoundException e) {
            System.out.println("The specified file is not found");
        } catch (AssertionError e) {
            System.out.println("Ensure the first file contains %\"Test\"%.txt and the second file contains %\"Training\"%.txt");
        }

        //Reads the file
        testData = readData(args[1]);
        trainingData = readData(args[0]);

        //Finding the nearest neighbours
        for (Iris testIris : testData) {
            PriorityQueue<Distance> nearestDistance = new PriorityQueue<>((o1, o2) -> {
                if(o1.getDistance() < o2.getDistance()){
                    return -1;
                } else if (o1.getDistance() > o2.getDistance()) {
                    return 1;
                } else {
                    return 0;
                }
            });

            for (Iris trainingIris : trainingData) {
                nearestDistance.offer(calculateDistance(testIris, trainingIris));
            }

            //Get k nearest neighbours to make prediction
            int setosa = 0;
            int versicolor = 0;
            int virginica = 0;

            for (int i=0; i<k; i++) {
                Iris node = nearestDistance.poll().getNode();
                if(node.getFlowerName().equals("Iris-setosa")){
                    setosa++;
                }
                if(node.getFlowerName().equals("Iris-versicolor")){
                    versicolor++;
                }
                if(node.getFlowerName().equals("Iris-virginica")){
                    virginica++;
                }
            }

            if ((setosa > versicolor) && (setosa > virginica)){
                testIris.setPrediction("Iris-setosa");
            }
            if ((versicolor > setosa) && (versicolor > virginica)){
                testIris.setPrediction("Iris-versicolor");
            }
            if ((virginica > setosa) && (virginica > versicolor)){
                testIris.setPrediction("Iris-virginica");
            }
            if ((setosa == versicolor) && (versicolor == virginica)) {
                testIris.setPrediction("Iris-setosa");
            }
        }

        outputToFile();
    }
}
