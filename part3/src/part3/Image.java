package part3;


import java.util.List;

public class Image {
    protected String type;
    protected int width; //correspond to col
    protected int height; //correspond to row
    protected int[][] pixels; //row, cols
    protected String category;  //What the image is (#Yes, #Other)
    protected double[] featureValues;  //AKA Inputs

    protected Image(String type, int width, int height, int[][] pixels, String category) {
        this.type = type;
        this.width = width;
        this.height = height;
        this.pixels = pixels;
        this.category = category;
    }

    protected String getType() {
        return type;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected int[][] getPixels() {
        return pixels;
    }

    protected String getCategory() {
        return category;
    }

    public double[] getFeatureValues() {
        return featureValues;
    }

    protected void setFeatureValues(double[] featureValues) {
        this.featureValues = featureValues;
    }

    protected double[] calculateFeatureValues(List<Feature> feature){
        //First value will equal 1
        featureValues = new double[feature.size() + 1];
        featureValues[0] = 1;

        int index = 1;

        //Set feature value for each feature
        for (Feature f : feature) {
            featureValues[index] = findFeatureValue(f, this);
            index++;
        }

        return featureValues;
    }

    protected static int findFeatureValue(Feature feature, Image image) {
        int sum=0;
        for(int i=0; i < 4; i++) {
            //If sign is true, expecting 1
            if (feature.sign[i]) {
                if (image.pixels[feature.row[i]][feature.col[i]] == 1) {
                    sum++;
                }
            }
            //If sign is false, expecting 0
            else {
                if (image.pixels[feature.row[i]][feature.col[i]] == 0) {
                    sum++;
                }
            }
        }

        return (sum>=3)?1:0;
    }


    /**
     * For Testing Purposes
     */
    protected java.lang.String toPixelStream() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<getHeight(); i++) {
            for (int j=0; j<getWidth(); j++) {
                sb.append(pixels[i][j]);
            }
        }

        return sb.toString();
    }
}
