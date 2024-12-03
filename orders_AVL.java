import java.util.*;

public class orders_AVL {
    // Прямой обход (Pre-order)
    static void preOrder(AVL_tree.AVLNode node, List<Integer> result) {
        if (node == null) return;
        result.add(node.value);
        preOrder(node.left, result);
        preOrder(node.right, result);
    }

    // Симметричный обход (In-order)
    static void inOrder(AVL_tree.AVLNode node, List<Integer> result) {
        if (node == null) return;
        inOrder(node.left, result);
        result.add(node.value);
        inOrder(node.right, result);
    }

    // Обратный обход (Post-order)
    static void postOrder(AVL_tree.AVLNode node, List<Integer> result) {
        if (node == null) return;
        postOrder(node.left, result);
        postOrder(node.right, result);
        result.add(node.value);
    }

    // Обход в ширину (BFS)
    static List<Integer> bfs(AVL_tree.AVLNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;

        Queue<AVL_tree.AVLNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            AVL_tree.AVLNode current = queue.poll();
            result.add(current.value);

            if (current.left != null) queue.add(current.left);
            if (current.right != null) queue.add(current.right);
        }

        return result;
    }

    public static void main(String[] args) {

        int[] keys = {2, 6, 3, 8, 5, 9, 10, 1, 14, 7};
        AVL_tree.AVLTree tree = new AVL_tree.AVLTree();
        for (int k : keys) {
            tree.insert(k);
        }
        AVL_tree.picTree_AVL(tree);
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
