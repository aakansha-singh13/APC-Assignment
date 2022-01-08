import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class LegoTrees {
    /**
     * Find all the permutations for the list elements passed
     * @param combinations
     * @param result
     * @param trees
     * @param used
     */
    private static void permute(List<List<Integer>> combinations, List<Integer> result, int [] trees, boolean [] used) {
        if (result.size() == trees.length) {
            combinations.add(new ArrayList<Integer>(result));
        } else {
            for (int i = 0; i < trees.length; i++) {
                if (used[i] || i > 0 && trees[i] == trees[i - 1] && !used[i - 1]) {
                    continue;
                }
                used[i] = true;
                result.add(trees[i]);
                permute(combinations, result, trees, used);
                used[i] = false;
                result.remove(result.size() - 1);
            }
        }
    }

    /**
     * Inital array sort before we move ahead to calculate all permutations
     * @param innerTrees
     * @return
     */
    public static List<List<Integer>> allCombinations(int[] innerTrees) {
        List <List<Integer>> combinations = new ArrayList<List<Integer>>();
        Arrays.sort(innerTrees);
        permute(combinations, new ArrayList<Integer>(), innerTrees,new boolean[innerTrees.length]);
        return combinations;
    }

    /**
     * Returns the profit calculated for the order of the trees received
     * @param perm
     * @return
     */
    private static int returnProfit(List<Integer> perm) {
        int cutStatus = 0;
        int profit = 0;
        for(int i=0;i<perm.size();i++){
            int width = perm.get(i);
            if((width <= 3) && (cutStatus == 0)) {
                cutStatus = 3-width;
                if (width == 2) {profit+=3;}
                else if (width == 1) {profit+=-1;}
                else if (width == 3) {profit+=1;}
            }
            else if ((width <= 3) && (cutStatus > 0)) {
                if (width > cutStatus) {
                    int newLength = width-cutStatus;
                    if (newLength == 2) {profit+=3;}
                    else if (newLength == 1) {profit+=-1;}
                    else if (newLength == 3) {profit+=1;}
                    if (cutStatus == 2) {profit+=3;}
                    else if (cutStatus == 1) {profit+=-1;}
                    else if (cutStatus == 3) {profit+=1;}
                    cutStatus = 3 - newLength;
                }
                else if (width <= cutStatus) {
                    if (width == 2) {profit+=3;}
                    else if (width == 1) {profit+=-1;}
                    else if (width == 3) {profit+=1;}
                    cutStatus = cutStatus-width;
                }
            }
            else if((width > 3) && (cutStatus > 0)) {
                int newLength = width-cutStatus;
                if (cutStatus == 1) {profit+=-1;}
                else if (cutStatus == 2) {profit+=3;}
                int n = newLength / 3;
                newLength = newLength%3;
                profit += n*1;
                cutStatus = 0;
                if (newLength == 1) {profit+=-1; cutStatus = 3-newLength;}
                else if (newLength == 2) {profit+=3; cutStatus = 3-newLength;}
            }
            else if ((width >3) && (cutStatus == 0)){
                int n = width / 3;
                width = width%3;
                profit += n*1;
                cutStatus = 0;
                if (width == 1) {profit+=-1; cutStatus = 3-width;}
                else if (width == 2) {profit+=3; cutStatus = 3-width;}
            }

        }
        return profit;
    }

    public static void main(String args[])throws IOException {
        Scanner sc = new Scanner(System.in);
        int case_ = 0;
        while(true) {
            case_+=1;
            ArrayList<List<List<Integer>>> trees = new ArrayList<List<List<Integer>>>();
            int maxProfit = 0;
            int numberOfSawMills = sc.nextInt();
            if (numberOfSawMills == 0) break;
            for (int j = 0; j < numberOfSawMills; j++) {
                int numberOfTrees = sc.nextInt();
                int[] innerTrees = new int[numberOfTrees];
                for (int i = 0; i < numberOfTrees; i++) {
                    int tree = sc.nextInt();
                    innerTrees[i] = tree;
                }
                List<List<Integer>> permute = allCombinations(innerTrees);
                int sawMillMaxProfit = Integer.MIN_VALUE;
                List<List<Integer>> finalResult = new ArrayList<List<Integer>>();
                for(List<Integer> perm:permute)
                {
                    int profit = returnProfit(perm);
                    if (profit > sawMillMaxProfit) {
                        sawMillMaxProfit = profit;
                        finalResult = new ArrayList<List<Integer>>();
                        finalResult.add(perm);
                    }
                    else if (profit == sawMillMaxProfit) {
                        finalResult.add(perm);
                    }
                }
                maxProfit += sawMillMaxProfit;
                trees.add(finalResult);
            }

            System.out.println("Case " + case_);
            System.out.println("Max Profit " + maxProfit);
            System.out.println("Order ");
            for(List<List<Integer>> pattern:trees)
            {
                System.out.println(pattern);
            }

        }
    }
}
