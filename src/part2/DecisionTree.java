package part2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class for the decision tree
 */
public class DecisionTree {
    //Fields
    protected static List<Instance> allInstances;
    protected static ArrayList<String> categoryNames;
    protected static ArrayList<String> attNames;
    protected static Node root;

    protected static Node buildTree(List<Instance> instances, List<String> attributes) {
        Node n = null;

        //TODO: Base Cases + testing
        if (instances.isEmpty()) {
            //Return a leaf node containing the name and probability of the most
            //probable class across the whole dataset(i.e., the ‘‘baseline’’ predictor)
        }
        boolean pure = true;
        for (int i = 0; i < instances.size()-1; i++) {
            if(instances.get(i).getCategory() != instances.get(i+1).getCategory()) {
                pure = false;
                break;
            }
        }
        if (pure) {
            //Return a leaf node containing the name of the class of the instances
            //in the node and probability 1
        }
        if (attributes.isEmpty()) {
            //Return a leaf node containing the name and probability of the
            //majority class of the instances in the node (choose randomly
            //if classes are equal)
        }
        else {
            //Find best attribute
            double bestPurity = 1;
            String bestAtt = "";
            List<Instance> bestInstTrue = null;
            List<Instance> bestInstFalse = null;

            for (String s : attributes) {
                Set<Instance> trueSet = new HashSet<>();
                Set<Instance> falseSet = new HashSet<>();

                //Adding data to the different sets based on if the attribute is true or false
                for (Instance i : instances) {
                    if (i.getAtt(attributes.indexOf(s))) {
                        trueSet.add(i);
                    } else {
                        falseSet.add(i);
                    }
                }

                //Calculating the weighted impurities for the sets
                double WAI = Helper.caluculateWAI(trueSet, falseSet);

                //If best attributes are not set and comparing
                if (WAI < bestPurity) {
                    bestPurity = WAI;
                    bestAtt = s;
                    bestInstTrue = new ArrayList<>(trueSet);
                    bestInstFalse = new ArrayList<>(falseSet);

                }

            }

            //Build subtrees using the remaining attributes
            //Deleted best attribute and all data containing it
            ArrayList<Instance> newInstance = new ArrayList<>(instances);
            ArrayList<String> newAttributes = new ArrayList<>(attributes);

            int removeIndex = newAttributes.indexOf(bestAtt);
            attributes.remove(removeIndex);
            for (Instance i : newInstance) {
                i.vals.remove(removeIndex);
            }

            n = new Node(bestAtt, buildTree(bestInstTrue, newAttributes), buildTree(bestInstFalse, newAttributes));

        }

        return n;
    }

    public static void main(String[] args) {
        Helper.readDataFile("./data/part2/" + args[0]);
        DecisionTree DT = new DecisionTree();
        root = buildTree(allInstances, attNames);
    }
}
