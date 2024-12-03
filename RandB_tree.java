import java.util.*;

public class RandB_tree {

    // Реализация Красно-черного дерева
    static class RedBlackTree {
        static class Node {
            int value;
            boolean isRed;
            Node left, right, parent;

            Node(int value) {
                this.value = value;
                this.isRed = true; // Новый узел всегда красный
            }
        }

        Node root;

        void insert(int value) {
            Node newNode = new Node(value);
            root = bstInsert(root, newNode);
            fixViolations(newNode);
        }

        private Node bstInsert(Node root, Node newNode) {
            if (root == null) return newNode;

            if (newNode.value < root.value) {
                root.left = bstInsert(root.left, newNode);
                root.left.parent = root;
            } else if (newNode.value > root.value) {
                root.right = bstInsert(root.right, newNode);
                root.right.parent = root;
            }

            return root;
        }

        private void fixViolations(Node newNode) {
            while (newNode != root && newNode.parent.isRed) {
                Node parent = newNode.parent;
                Node grandparent = parent.parent;

                if (parent == grandparent.left) {
                    Node uncle = grandparent.right;
                    if (uncle != null && uncle.isRed) {
                        grandparent.isRed = true;
                        parent.isRed = false;
                        uncle.isRed = false;
                        newNode = grandparent;
                    } else {
                        if (newNode == parent.right) {
                            rotateLeft(parent);
                            newNode = parent;
                            parent = newNode.parent;
                        }
                        rotateRight(grandparent);
                        boolean temp = parent.isRed;
                        parent.isRed = grandparent.isRed;
                        grandparent.isRed = temp;
                        newNode = parent;
                    }
                } else {
                    Node uncle = grandparent.left;
                    if (uncle != null && uncle.isRed) {
                        grandparent.isRed = true;
                        parent.isRed = false;
                        uncle.isRed = false;
                        newNode = grandparent;
                    } else {
                        if (newNode == parent.left) {
                            rotateRight(parent);
                            newNode = parent;
                            parent = newNode.parent;
                        }
                        rotateLeft(grandparent);
                        boolean temp = parent.isRed;
                        parent.isRed = grandparent.isRed;
                        grandparent.isRed = temp;
                        newNode = parent;
                    }
                }
            }
            root.isRed = false;
        }

        private void rotateLeft(Node node) {
            Node temp = node.right;
            node.right = temp.left;
            if (temp.left != null) temp.left.parent = node;
            temp.parent = node.parent;
            if (node.parent == null) root = temp;
            else if (node == node.parent.left) node.parent.left = temp;
            else node.parent.right = temp;
            temp.left = node;
            node.parent = temp;
        }

        private void rotateRight(Node node) {
            Node temp = node.left;
            node.left = temp.right;
            if (temp.right != null) temp.right.parent = node;
            temp.parent = node.parent;
            if (node.parent == null) root = temp;
            else if (node == node.parent.right) node.parent.right = temp;
            else node.parent.left = temp;
            temp.right = node;
            node.parent = temp;
        }

        int height(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(height(node.left), height(node.right));
        }

        int height() {
            return height(root);
        }

        public void printTree() {
            printTree(root, "", true);
        }

        private void printTree(Node node, String indent, boolean last) {
            if (node != null) {
                String nodeInfo = node.isRed ? "[R]" + node.value : "[B]" + node.value;
                System.out.println(indent + (last ? "└── " : "├── ") + nodeInfo);

                indent += last ? "    " : "│   ";

                printTree(node.right, indent, false);
                printTree(node.left, indent, true);
            }
        }

        // Функция для поиска элемента
        public boolean search(Node node, int value) {
            if (node == null) return false;

            if (value < node.value) return search(node.left, value);
            else if (value > node.value) return search(node.right, value);
            else return true;
        }

        public boolean search(int value) {
            return search(root, value);
        }

        // Функция для удаления узла
        public void delete(int value) {
            Node nodeToDelete = findNode(root, value);
            if (nodeToDelete != null) {
                deleteNode(nodeToDelete);
            }
        }

        private Node findNode(Node root, int value) {
            if (root == null || root.value == value) return root;
            if (value < root.value) return findNode(root.left, value);
            return findNode(root.right, value);
        }

        private void deleteNode(Node node) {
            Node child, parent;
            boolean nodeColor;

            if (node.left == null || node.right == null) {
                parent = node.parent;
                child = (node.left != null) ? node.left : node.right;
                nodeColor = node.isRed;

                if (child != null) child.parent = parent;

                if (parent == null) root = child;
                else if (node == parent.left) parent.left = child;
                else parent.right = child;

                if (!nodeColor) {
                    fixDeletion(child, parent);
                }
            } else {
                Node successor = minValueNode(node.right);
                node.value = successor.value;
                deleteNode(successor);
            }
        }

        private Node minValueNode(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        private void fixDeletion(Node node, Node parent) {
            while (node != root && (node == null || !node.isRed)) {
                if (node == parent.left) {
                    Node sibling = parent.right;
                    if (sibling != null && sibling.isRed) {
                        sibling.isRed = false;
                        parent.isRed = true;
                        rotateLeft(parent);
                        sibling = parent.right;
                    }
                    if ((sibling.left == null || !sibling.left.isRed) &&
                            (sibling.right == null || !sibling.right.isRed)) {
                        sibling.isRed = true;
                        node = parent;
                        parent = node.parent;
                    } else {
                        if (sibling.right == null || !sibling.right.isRed) {
                            if (sibling.left != null) sibling.left.isRed = false;
                            sibling.isRed = true;
                            rotateRight(sibling);
                            sibling = parent.right;
                        }
                        sibling.isRed = parent.isRed;
                        parent.isRed = false;
                        if (sibling.right != null) sibling.right.isRed = false;
                        rotateLeft(parent);
                        node = root;
                    }
                } else {
                    Node sibling = parent.left;
                    if (sibling != null && sibling.isRed) {
                        sibling.isRed = false;
                        parent.isRed = true;
                        rotateRight(parent);
                        sibling = parent.left;
                    }
                    if ((sibling.left == null || !sibling.left.isRed) &&
                            (sibling.right == null || !sibling.right.isRed)) {
                        sibling.isRed = true;
                        node = parent;
                        parent = node.parent;
                    } else {
                        if (sibling.left == null || !sibling.left.isRed) {
                            if (sibling.right != null) sibling.right.isRed = false;
                            sibling.isRed = true;
                            rotateLeft(sibling);
                            sibling = parent.left;
                        }
                        sibling.isRed = parent.isRed;
                        parent.isRed = false;
                        if (sibling.left != null) sibling.left.isRed = false;
                        rotateRight(parent);
                        node = root;
                    }
                }
            }
            if (node != null) node.isRed = false;
        }
    }

    public static void grafik_RandB() {
        int[] sizes = {100, 500, 1000, 2000, 4000, 6000, 8000, 10000, 12000, 14000, 16000, 18000, 20000};
        System.out.printf("%-10s %-10s\n", "Keys", "AVL");
        for (int n : sizes) {
            RandB_tree.RedBlackTree rbTree = new RandB_tree.RedBlackTree();

            for (int i = 1; i <= n; i++) {
                rbTree.insert(i);
            }
            System.out.printf("%-10d %-10d\n", n, rbTree.height());
        }
    }

    public static void picTree_RB(RandB_tree.RedBlackTree tree) {
        System.out.println("\nКрасно-черное дерево:");
        tree.printTree();
    }

    public static void main(String[] args) {

        grafik_RandB();

        RandB_tree.RedBlackTree tree = new RandB_tree.RedBlackTree();

        int[] keys = {2, 6, 3, 8, 5, 9, 10, 1, 14, 7};

        for (int k : keys) {
            tree.insert(k);
        }

        picTree_RB(tree);

        System.out.println("\nНайти 1: " + tree.search(1));
        System.out.println("Найти 100: " + tree.search(100));

        tree.delete(6);
        System.out.println("\nДерево после удаления 6:");
        tree.printTree();
    }
}


