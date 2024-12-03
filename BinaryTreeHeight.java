import java.util.*;

public class BinaryTreeHeight {

    static class TreeNode {
        int value;
        TreeNode left, right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    static class BinarySearchTree {
        TreeNode root;

        void insert(int value) {
            root = insertRec(root, value);
        }

        private TreeNode insertRec(TreeNode node, int value) {
            if (node == null) return new TreeNode(value);
            if (value < node.value) node.left = insertRec(node.left, value);
            else node.right = insertRec(node.right, value);
            return node;
        }

        int height() {
            return calculateHeight(root);
        }

        private int calculateHeight(TreeNode node) {
            if (node == null) return 0;
            return 1 + Math.max(calculateHeight(node.left), calculateHeight(node.right));
        }
    }

    // Лучший случай: создаем сбалансированное дерево
    static BinarySearchTree createBalancedTree(int[] keys) {
        BinarySearchTree bst = new BinarySearchTree();
        addBalanced(bst, keys, 0, keys.length - 1);
        return bst;
    }

    private static void addBalanced(BinarySearchTree bst, int[] keys, int start, int end) {
        if (start > end) return;
        int mid = (start + end) / 2;
        bst.insert(keys[mid]);
        addBalanced(bst, keys, start, mid - 1);
        addBalanced(bst, keys, mid + 1, end);
    }

    // Худший случай: последовательная вставка
    static BinarySearchTree createDegenerateTree(int[] keys) {
        BinarySearchTree bst = new BinarySearchTree();
        for (int key : keys) {
            bst.insert(key);
        }
        return bst;
    }

    public static void main(String[] args) {
        int[] sizes = {100, 500, 1000, 2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000};
        Random random = new Random();

        System.out.printf("%-10s %-10s %-10s %-10s\n", "Keys", "Best", "Average", "Worst");

        for (int n : sizes) {
            int[] keys = new int[n];
            for (int i = 0; i < n; i++) keys[i] = random.nextInt(1000000);

            // Лучший случай
            Arrays.sort(keys); // Сортируем для создания сбалансированного дерева
            BinarySearchTree bestTree = createBalancedTree(keys);
            int bestHeight = bestTree.height();

            // Средний случай
            Integer[] shuffledKeys = Arrays.stream(keys).boxed().toArray(Integer[]::new);
            Collections.shuffle(Arrays.asList(shuffledKeys)); // Перемешиваем массив для случайной вставки
            BinarySearchTree avgTree = new BinarySearchTree();
            for (int key : shuffledKeys) avgTree.insert(key);
            int avgHeight = avgTree.height();

            // Худший случай
            BinarySearchTree worstTree = createDegenerateTree(keys); // Используем отсортированный массив
            int worstHeight = worstTree.height();

            System.out.printf("%-10d %-10d %-10d %-10d\n", n, bestHeight, avgHeight, worstHeight);
        }
    }
}
