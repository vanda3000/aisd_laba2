import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class orders_RB {
    // Прямой обход (Pre-order)
    static void preOrder(RandB_tree.RedBlackTree.Node node, List<Integer> result) {
        if (node == null) return;
        result.add(node.value);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    // Симметричный обход (In-order)
    static void inOrder(RandB_tree.RedBlackTree.Node node, List<Integer> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    // Обратный обход (Post-order)
    static void postOrder(RandB_tree.RedBlackTree.Node node, List<Integer> result) {
        if (node == null) return;
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.value);
    }

    // Обход в ширину (BFS)
    static List<Integer> bfs(RandB_tree.RedBlackTree.Node root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<RandB_tree.RedBlackTree.Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            RandB_tree.RedBlackTree.Node current = queue.poll();
            result.add(current.value);

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] keys = {2, 6, 3, 8, 5, 9, 10, 1, 14, 7};
        RandB_tree.RedBlackTree tree = new RandB_tree.RedBlackTree();
        for (int k : keys) {
            tree.insert(k);
        }
        RandB_tree.picTree_RB(tree);
        // Обходы
        List<Integer> preOrderResult = new ArrayList<>();
        preOrder(tree.root, preOrderResult);

        List<Integer> inOrderResult = new ArrayList<>();
        inOrder(tree.root, inOrderResult);

        List<Integer> postOrderResult = new ArrayList<>();
        postOrder(tree.root, postOrderResult);

        List<Integer> bfsResult = bfs(tree.root);

        // Вывод результатов
        System.out.println("Прямой обход: " + preOrderResult);
        System.out.println("Симметричный обход: " + inOrderResult);
        System.out.println("Обратный обход: " + postOrderResult);
        System.out.println("Обход в ширину: " + bfsResult);
    }
}
