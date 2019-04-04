package part3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {
    private static final int MAX_ITERS = 1000;   //Maximum number of iterations allowed
    private static final double LEARNING_RATE = 0.1;
    private static final double BIAS = 0.5;

    private static int featureNum = 50; //Number of features (excluding the bias)
    private static List<Feature> feature;   //All features (featureNum number of features)
    private static List<Image> images;  //All images from the data file
    private static double[] weights;    //Weights to modify and train


    private static List<Feature> generateFeatures(int n){
        List<Feature> features = new ArrayList<>();

        for (int j=0; j<n; j++) {
            //Initiate feature values
            int[] row = new int[4];
            int[] col = new int[4];
            boolean[] sign = new boolean[4];

            //Assign to random pixels and random sign
            Random rand = new Random();
            for (int i = 0; i < 4; i++) {
                row[i] = rand.nextInt(10);
                col[i] = rand.nextInt(10);
                sign[i] = rand.nextBoolean();
            }
            //Add to list of feature
            features.add(new Feature(row, col, sign));
        }

        return features;
    }

    private static void setImageFeatures() {
        for (Image i : images) {
            i.setFeatureValues(i.calculateFeatureValues(feature));
        }
    }


    private static void buildPerceptron(){
        Random rand = new Random();
        //Create number of weights equal to n + 1 features
        weights = new double[featureNum + 1];

        //Initialise weights to 0
        //Weight[0] will equal bias of 1
        weights[0] = BIAS;
        for (int i=1; i<weights.length; i++) {
            weights[i] = -1.0 + (2) * rand.nextDouble();
        }
    }

    private static int predict(double[] inputs) {
        //Going through all inputs and weights to compute the prediction
        double totalSum = 0;
        for (int i=0; i<inputs.length; i++) {
            totalSum += weights[i]*inputs[i];
        }

        //Applying the threshold function
        if (totalSum > 0) {
            return 1;
        }
        else {
            return 0;
        }
    }

    public static void train(double[] inputs, int actual) {
        //Exclude bias
        //Find direction of change (+ve or -ve)
        for (int i=0; i<inputs.length; i++) {
            int predict;
            double sum = weights[i]*inputs[i];
            if (sum > 0) {predict = 1;}
            else { predict = 0; }

            if (predict != actual) {
                weights[i] = weights[i] + LEARNING_RATE*(actual - predict)*inputs[i];
            }
        }


    }

    public static void printWeights() {
        for (int i=0; i<weights.length; i++) {
            System.out.println("w" +i +": " + weights[i]);
        }
        System.out.println("\n\n");
    }


    public static void main(String[] args) {
        assert args.length == 1;

        //Reading images and setting feature values/inputs
        images = Helper.readData(args[0]);
        feature = generateFeatures(featureNum);
        setImageFeatures();

        //Setting up perceptron
        buildPerceptron();
        System.out.println("Initial Weights:");
        printWeights();

        int hit;    //How many images it guesses correctly
        int loop = 0;   //How many times it has looped through
        int train = 0;
        int bestHit = 0;
        double accuracy = 0;

        while (true) {
            if (loop == MAX_ITERS) {
                System.out.println("\n\n");
                System.out.println("Reached max number or iterations");
                System.out.println("Best accuracy is " +bestHit +"/" +images.size() + " : " + accuracy + "%");
                break;
            }
            hit = 0;
            int currentImage = 0;

            for (Image img : images) {
                //Getting inputs from the n feature values from the image
                double[] inputs = img.getFeatureValues();
                assert inputs.length == weights.length;

                //Useful Print statements for info
                int category = -1;
                if (img.getCategory().equals("#Yes")) {
                    category = 1;
                }
                else if (img.getCategory().equals("#other")){
                    category = 0;
                }

                //Prediction value
                int prediction = predict(inputs);
       //         System.out.println("Current Image: " +currentImage +", Actual Category: " + category);
       //         System.out.println("Prediction: " +prediction);

                //Compare with actual data category
                if (prediction == 1) {  //1 corresponds to yes = 1
                    if (img.getCategory().equals("#Yes")) {
                        hit++;
       //                 System.out.println("Correctly guessed");
                    }
                    else {
        //                System.out.println("Over-predicted, training weights");
                        train(inputs, 0);
       //                 System.out.println("New trained weights");
      //                  printWeights();
                        train++;
                    }
                }
                if (prediction == 0) {  //0 corresponds to other = 0
                    if (img.getCategory().equals("#other")) {
                        hit++;
     //                   System.out.println("Correctly guessed");
                    }
                    else {
         //               System.out.println("Under-predicted, training weights");
                        train(inputs, 1);
         //               System.out.println("New trained weights");
        //                printWeights();
                        train++;
                    }
                }
                currentImage++;
            }

            //Setting best accuracy
            if (hit > bestHit) {
                bestHit = hit;
                accuracy = ((double) bestHit)/((double) images.size()) * 100;
            }

            if (hit == images.size()) {
                System.out.println("\n\n");
                System.out.println("Guessed all images correctly");
                System.out.println("Took " +loop +" generations");
                System.out.println("Re-trained weights " + train + " times");
                break;
            }
            System.out.println("Current hits out of 100: " + hit);
            loop++;
        }


    }
}
