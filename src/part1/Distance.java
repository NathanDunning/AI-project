package part1;

public class Distance {
     private double distance;
     private Iris node;

    public Distance(double distance, Iris node) {
        this.distance = distance;
        this.node = node;
    }

    public double getDistance() {
        return distance;
    }

    public Iris getNode() {
        return node;
    }
}
