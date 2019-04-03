package part2;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Class for the decision tree
 */
//286/370 Accuracy
public class DecisionTree {
    //Fields
    protected static List<Instance> allInstances;
    protected static ArrayList<String> categoryNames;
    protected static ArrayList<String> attNames;
    protected static Node root;

    @SuppressWarnings("Duplicates")
    protected static Node buildTree(List<Instance> instances, List<String> attributes) {
        Node n = null;

        if (instances.isEmpty()) {
            //Return a leaf node containing the name and probability of the most
            //probable class across the whole dataset(i.e., the ‘‘baseline’’ predictor)
            int live = 0;
            int dead = 0;

            //Calculating count
            for (Instance i : allInstances) {
                if (i.getCategory() == 0) {
                    live++;
                }
                else {
                    dead++;
                }
            }

            //Return nodes based on number of count
            if (live > dead) {
                double probability = live/allInstances.size();
                return new LeafNode(categoryNames.get(0), probability, live+dead);
            }
            else if (dead > live) {
                double probability = dead/allInstances.size();
                return new LeafNode(categoryNames.get(1), probability,live+dead);
            }
            else {
                //Most impure (0.5)
                //live == die
                double rand = Math.random();
                if (rand <= 0.4) {
                    return new LeafNode(categoryNames.get(0), 0.5,live+dead);
                }
                else {
                    return new LeafNode(categoryNames.get(1), 0.5,live+dead);
                }
            }

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
            if(instances.get(0).getCategory() == 0) {
                return new LeafNode(categoryNames.get(0), 1.0, instances.size());
            }
            else {
                return new LeafNode(categoryNames.get(1), 1.0, instances.size());
            }

        }
        if (attributes.isEmpty()) {
            //Return a leaf node containing the name and probability of the
            //majority class of the instances in the node (choose randomly
            //if classes are equal)
            int live = 0;
            int dead = 0;

            for (Instance i : instances) {
                if (i.getCategory() == 0){
                    live++;
                }
                else {
                    dead++;
                }
            }

            if (live > dead) {
                double probability = live/instances.size();
                return new LeafNode(categoryNames.get(0), probability,live+dead);
            }
            else if (dead > live) {
                double probability = dead/instances.size();
                return new LeafNode(categoryNames.get(1), probability, live+dead);
            }
            else {
                double rand = Math.random();
                if (rand <= 0.4) {
                    return new LeafNode(categoryNames.get(0), 0.5, live+dead);
                }
                else {
                    return new LeafNode(categoryNames.get(1), 0.5, live+dead);
                }
            }

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
            ArrayList<String> newAttributesT = new ArrayList<>(attributes);


            int removeIndex = newAttributesT.indexOf(bestAtt);
            newAttributesT.remove(removeIndex);
            for (Instance i : newInstance) {
                i.vals.remove(removeIndex);
            }

            ArrayList<String> newAttributesF = new ArrayList<>(newAttributesT);

            n = new DecisionNode(bestAtt, buildTree(bestInstTrue, newAttributesT), buildTree(bestInstFalse, newAttributesF));

        }

        return n;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("No files given, please specify data files");
        }
        if (args.length == 1) {
            if (args[0].contains("training")){
                Helper.readDataFile("./data/part2/" + args[0]);
                root = buildTree(allInstances, attNames);
                Helper.printTree(root);
            }
            else {
                System.out.println("Please ensure first file contains 'training'");
            }
        }
        if (args.length >= 2) {
            if (args[0].contains("training")) {
                if (args[1].contains("test")) {
                    //Read Training File
                    Helper.readDataFile("./data/part2/" + args[0]);
                    root = buildTree(allInstances, attNames);
                    Helper.printTree(root);
                    System.out.println("\n");

                    //Read Test File
                    ArrayList<Map<String, Boolean>> dataset = Helper.testToList(args[1]);
                    int correctPredictions = 0;

                    for (Map<String, Boolean> instance : dataset) {
                        Node node = root;
                        //Traverse down to leaf node
                        while (!(node instanceof LeafNode)) {
                            DecisionNode currentNode = (DecisionNode) node;
                            if (instance.get(currentNode.getBestAttribute())) {
                                node = currentNode.getLeft();
                            }
                            else {
                                node = currentNode.getRight();
                            }
                        }

                        LeafNode cat = (LeafNode) node;
                        String predict = cat.getCat();

                        //If category is true then actual cat is live
                        if (instance.get("Category")) {
                            if (predict.equals(categoryNames.get(0))){
                                correctPredictions++;
                            }
                        }
                        //Category is die
                        else if (!instance.get("Category")) {
                            if (predict.equals(categoryNames.get(1))){
                                correctPredictions++;
                            }
                        }
                    }

                    System.out.println("Correct predictions: " + correctPredictions + " out of: " + dataset.size() + " for " + args[1]);
                }
            }
        }

    }
}
