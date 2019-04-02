package part2;

public class DecisionNode implements Node {
    protected String bestAttribute;
    protected Node left;
    protected Node right;

    public DecisionNode(String bestAttribute, Node left, Node right) {
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

    @Override
    public String toString() {
        return bestAttribute;
    }

    public void report(String indent){
        System.out.format("%s%s = True:\n",
                indent, bestAttribute);
        left.report(indent+"    ");
        System.out.format("%s%s = False:\n",
                indent, bestAttribute);
        right.report(indent+"   ");
    }

}
