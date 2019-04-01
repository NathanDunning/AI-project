package part2;

public class Node {
    protected String bestAttribute;
    protected Node left;
    protected Node right;

    public Node(String bestAttribute, Node left, Node right) {
        this.bestAttribute = bestAttribute;
        this.left = left;
        this.right = right;
    }

    public String getBestAttribute() {
        return bestAttribute;
    }

    public void setBestAttribute(String bestAttribute) {
        this.bestAttribute = bestAttribute;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }
}
