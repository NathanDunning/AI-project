package part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Instance {

    protected int category;
    protected List<Boolean> vals;

    public Instance(int cat, Scanner s){
        category = cat;
        vals = new ArrayList<>();
        while (s.hasNextBoolean()) vals.add(s.nextBoolean());
    }

    public boolean getAtt(int index){
        return vals.get(index);
    }

    public int getCategory(){
        return category;
    }

    public String toString(){
        StringBuilder ans = new StringBuilder(DecisionTree.categoryNames.get(category));
        ans.append(" ");
        for (Boolean val : vals)
            ans.append(val?"true  ":"false ");
        return ans.toString();
    }

}
