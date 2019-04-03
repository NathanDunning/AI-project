package part2;

import com.sun.media.sound.InvalidDataException;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.util.*;


public class Helper {


    protected static void readDataFile(String fname){
        /* format of names file:
         * names of categories, separated by spaces
         * names of attributes
         * category followed by true's and false's for each instance
         */
        System.out.println("Reading data from file "+fname);
        try {
            Scanner din = new Scanner(new File(fname));

            DecisionTree.categoryNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) DecisionTree.categoryNames.add(s.next());
            int numCategories=DecisionTree.categoryNames.size();
            System.out.println(numCategories +" categories");

            DecisionTree.attNames = new ArrayList<String>();
            for (Scanner s = new Scanner(din.nextLine()); s.hasNext();) DecisionTree.attNames.add(s.next());
            int numAtts = DecisionTree.attNames.size();
            System.out.println(numAtts +" attributes");

            DecisionTree.allInstances = readInstances(din);
            din.close();
        }
        catch (IOException e) {
            throw new RuntimeException("Data File caused IO exception");
        }
    }

    protected static ArrayList testToList(String filename) {
        ArrayList<Map<String, Boolean>> testSet = new ArrayList<>();
        try {
            String[] attributeNames = null;
            String[] categoryNames = null;
            BufferedReader in = new BufferedReader(new FileReader("./data/part2/" + filename));
            String s = in.readLine();

            //Checking categories are the same
            if (s != null) {
                categoryNames = s.split("\\s+");
                assert categoryNames[0].equals(DecisionTree.categoryNames.get(0));
                assert categoryNames[1].equals(DecisionTree.categoryNames.get(1));
            }

            //Checking attributes are the same
            s = in.readLine();
            if (s != null) {
                attributeNames = s.split("\\s+");
                assert attributeNames.length == DecisionTree.attNames.size();

                //Check that categories are the same
                for (int i = 0; i < attributeNames.length; i++) {
                    assert DecisionTree.attNames.contains(attributeNames[i]);
                }
            }

            //Add to create map of attributes and values and add to map
            s = in.readLine();
            while (s != null) {
                Map<String, Boolean> instance = new HashMap<>();
                String[] splitLine = s.split("\\s+");
                String category = splitLine[0];
                if(category.equals(categoryNames[0])) {
                    instance.put("Category", true);
                }
                else if (category.equals(categoryNames[1])) {
                    instance.put("Category", false);
                }

                for (int i = 0; i < 16; i++) {
                    instance.put(attributeNames[i], Boolean.parseBoolean(splitLine[i+1]));
                }
                testSet.add(instance);
                s = in.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return testSet;
    }

    protected static List<Instance> readInstances(Scanner din){
        /* instance = classname and space separated attribute values */
        List<Instance> instances = new ArrayList<>();

        while (din.hasNext()){
            Scanner line = new Scanner(din.nextLine());
            instances.add(new Instance(DecisionTree.categoryNames.indexOf(line.next()),line));
        }
        System.out.println("Read " + instances.size()+" instances");
        return instances;
    }

    public static double calculateImpurity(Collection<Instance> set) {
        int m = 0;
        int n = 0;
        //Assign values to m and n
        for (Instance i : set) {
            if (i.getCategory() == 1) {
                m++;
            }
            else {
                n++;
            }
        }

        return (m*n)/(Math.pow(m+n, 2));
    }

    public static double caluculateWAI(Collection<Instance> set1, Collection<Instance> set2) {
        //Total instances in set
        double total = set1.size() + set2.size();

        //Probabilities of the sets
        double set1Size = new Double(set1.size());
        double set2Size = new Double(set2.size());
        double P1 = (set1Size)/total;
        double P2 = (set2Size)/total;

        //Impurities of the set
        double imp1 = calculateImpurity(set1);
        double imp2 = calculateImpurity(set2);

        return (P1*imp1 + P2*imp2);
    }

    public static void printTree(Node node){
        node.report("");

    }
}

