package part2;

public class LeafNode implements Node{
    private String cat;
    private double prob;
    private int count;

    public LeafNode(String cat, double prob, int count) {
        this.cat = cat;
        this.prob = prob;
        this.count = count;
    }

    @Override
    public void report(String indent) {

        System.out.format("%sCategory %s, prob =%4.0f%% : /%d\n",
                indent, cat, prob*100, count);

    }

}
