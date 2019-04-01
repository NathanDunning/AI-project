package part2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;


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
        int total = set1.size() + set2.size();

        //Probabilities of the sets
        double P1 = set1.size()/total;
        double P2 = set2.size()/total;

        //Impurities of the set
        double imp1 = calculateImpurity(set1);
        double imp2 = calculateImpurity(set2);

        return (P1*imp1 + P2*imp2);
    }
}

