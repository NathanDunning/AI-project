package part3;

public class Feature {
    protected int[] row;
    protected int[] col;
    protected boolean[] sign;

    Feature(int[] row, int[] col, boolean[] sign) {
        this.row = row;
        this.col = col;
        this.sign = sign;
    }

    protected int[] getRow() {
        return row;
    }

    protected int[] getCol() {
        return col;
    }

    protected boolean[] getSign() {
        return sign;
    }

}
