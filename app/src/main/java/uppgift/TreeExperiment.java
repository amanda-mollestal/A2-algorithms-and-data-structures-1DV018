package uppgift;

import java.util.Random;
import java.util.concurrent.Callable;

public class TreeExperiment {

    public static void runExperiment() throws Exception {
     int[] TREE_SIZES = { 250, 500, 1000, 2000, 4000 };


        for (int size : TREE_SIZES) {
            for (int cycle = 0; cycle < 5; cycle++) {
                boolean isSkewed = (cycle % 2 == 0);
                Integer[] dataSet = prepareDataSet(size, isSkewed);

                BinarySearchTree<Integer> bst = new BinarySearchTree<>();
                AVLTree<Integer> avl = new AVLTree<>();

                long bstAddTime = evaluateTreePerformance(bst, dataSet, true);
                long avlAddTime = evaluateTreePerformance(avl, dataSet, true);

                int bstHeight = bst.height();
                int avlHeight = avl.getHeight();

                long bstRemoveTime = evaluateTreePerformance(bst, dataSet, false);
                long avlRemoveTime = evaluateTreePerformance(avl, dataSet, false);

                displayResults(size, isSkewed, bstAddTime, avlAddTime, bstRemoveTime, avlRemoveTime, bstHeight,
                        avlHeight);
            }
        }
    }

    private static Integer[] prepareDataSet(int size, boolean skewed) {
        Integer[] values = new Integer[size];
        Random randomGenerator = new Random();
        for (int i = 0; i < size; i++) {
            values[i] = skewed ? i : randomGenerator.nextInt();
        }
        return values;
    }

    private static long evaluateTreePerformance(Object tree, Integer[] values, boolean isAddOperation)
            throws Exception {
        MethodTimer timer = new MethodTimer();
        Callable<Void> task = () -> {
            if (tree instanceof BinarySearchTree) {
                BinarySearchTree<Integer> bst = (BinarySearchTree<Integer>) tree;
                for (Integer value : values) {
                    if (isAddOperation)
                        bst.add(value);
                    else
                        bst.remove(value);
                }
            } else if (tree instanceof AVLTree) {

                AVLTree<Integer> avl = (AVLTree<Integer>) tree;
                for (Integer value : values) {
                    if (isAddOperation)
                        avl.add(value);
                    else
                        avl.remove(value);
                }
            }
            return null;
        };
        return timer.measure(task, 1000, 1).median;
    }

    private static void displayResults(int size, boolean skewed, long bstAddTime, long avlAddTime, long bstRemoveTime,
            long avlRemoveTime, int bstHeight, int avlHeight) {
        String skewType = skewed ? "Skewed" : "Random";
        System.out.println("Tree Size: " + size + ", Data Pattern: " + skewType);
        System.out.println(
                "BST - Add Time: " + bstAddTime + " ns, Remove Time: " + bstRemoveTime + " ns, Height: " + bstHeight);
        System.out.println(
                "AVL - Add Time: " + avlAddTime + " ns, Remove Time: " + avlRemoveTime + " ns, Height: " + avlHeight);
        System.out.println("-------------------------------------------------");
        System.out.println("");
    }
}