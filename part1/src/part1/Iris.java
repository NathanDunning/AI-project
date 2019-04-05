package part1;

/**
 * Class for storing information about each iris
 */
public class Iris {
    //Fields
    private final double sepalLength;
    private final double sepalWidth;
    private final double petalLength;
    private final double petalWidth;
    private final String flowerName;
    private String prediction;

    /**
     * Constructor that creates the Iris object
     * @param sepalLength
     * @param sepalWidth
     * @param petalLength
     * @param petalWidth
     * @param flowerName
     */
    public Iris(double sepalLength, double sepalWidth, double petalLength, double petalWidth, String flowerName) {
        this.sepalLength = sepalLength;
        this.sepalWidth = sepalWidth;
        this.petalLength = petalLength;
        this.petalWidth = petalWidth;
        this.flowerName = flowerName;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public String getPrediction() {
        return prediction;
    }

    /**
     * Getter to return length of sepal in cm
     * @return Length of Sepal - double
     */
    public double getSepalLength() {
        return sepalLength;
    }

    /**
     * Getter to return width of sepal in cm
     * @return Width of Sepal - double
     */
    public double getSepalWidth() {
        return sepalWidth;
    }

    /**
     * Getter to return length of petal in cm
     * @return Length of Petal - double
     */
    public double getPetalLength() {
        return petalLength;
    }

    /**
     * Getter to return width of petal in cm
     * @return Width of Petal - double
     */
    public double getPetalWidth() {
        return petalWidth;
    }

    /**
     * Getter to return the name of the flower
     * @return Name of flower - String
     */
    public String getFlowerName() {
        return flowerName;
    }


}
