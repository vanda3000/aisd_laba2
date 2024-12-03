import java.util.*;

public class AVL_tree {

    // Узел АВЛ-дерева
    static class AVLNode {
        int value, height;
        AVLNode left, right;

        AVLNode(int value) {
            this.value = value;
            this.height = 1; // Высота нового узла равна 1
        }
    }

    // Реализация АВЛ-дерева
    static class AVLTree {
        AVLNode root;

        int height(AVLNode node) {
            return node == null ? 0 : node.height;
        }

        int getBalance(AVLNode node) {
            return node == null ? 0 : height(node.left) - height(node.right);
        }

        AVLNode rotateRight(AVLNode y) {
            AVLNode x = y.left;
            AVLNode T2 = x.right;

            x.right = y;
            y.left = T2;

            y.height = Math.max(height(y.left), height(y.right)) + 1;
            x.height = Math.max(height(x.left), height(x.right)) + 1;

            return x;
        }

        AVLNode rotateLeft(AVLNode x) {
            AVLNode y = x.right;
            AVLNode T2 = y.left;

            y.left = x;
            x.right = T2;

            x.height = Math.max(height(x.left), height(x.right)) + 1;
            y.height = Math.max(height(y.left), height(y.right)) + 1;

            return y;
        }

        AVLNode insert(AVLNode node, int value) {
            if (node == null) return new AVLNode(value);

            if (value < node.value) {
                node.left = insert(node.left, value);
            } else if (value > node.value) {
                node.right = insert(node.right, value);
            } else {
                return node; // Дубликаты игнорируются
            }

            node.height = Math.max(height(node.left), height(node.right)) + 1;

            int balance = getBalance(node);

            // Балансировка
            if (balance > 1 && value < node.left.value) {
                return rotateRight(node);
            }
            if (balance < -1 && value > node.right.value) {
                return rotateLeft(node);
            }
            if (balance > 1 && value > node.left.value) {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            if (balance < -1 && value < node.right.value) {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }

            return node;
        }

        void insert(int value) {
            root = insert(root, value);
        }

        int height() {
            return height(root);
        }

        AVLNode delete(AVLNode root, int value) {
            if (root == null) return root;

            // Удаление
            if (value < root.value) {
                root.left = delete(root.left, value);
            } else if (value > root.value) {
                root.right = delete(root.right, value);
            } else {
                if ((root.left == null) || (root.right == null)) {
                    AVLNode temp = root.left != null ? root.left : root.right;
                    root = temp;
                } else {
                    AVLNode temp = minValueNode(root.right);
                    root.value = temp.value;
                    root.right = delete(root.right, temp.value);
                }
            }

            if (root == null) return root;

            // Обновляем высоту
            root.height = Math.max(height(root.left), height(root.right)) + 1;

            // Балансировка
            int balance = getBalance(root);

            if (balance > 1 && getBalance(root.left) >= 0) {
                return rotateRight(root);
            }
            if (balance > 1 && getBalance(root.left) < 0) {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
            if (balance < -1 && getBalance(root.right) <= 0) {
                return rotateLeft(root);
            }
            if (balance < -1 && getBalance(root.right) > 0) {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }

            return root;
        }

        void delete(int value) {
            root = delete(root, value);
        }

        AVLNode minValueNode(AVLNode node) {
            AVLNode current = node;
            while (current.left != null) current = current.left;
            return current;
        }

        boolean search(AVLNode node, int value) {
            if (node == null) return false;

            if (value < node.value) {
                return search(node.left, value);
            } else if (value > node.value) {
                return search(node.right, value);
            } else {
                return true;
            }
        }

        boolean search(int value) {
            return search(root, value);
        }

        public void printTree() {
            printTree(root, "", true);
        }

        private void printTree(AVLNode node, String indent, boolean last) {
            if (node != null) {
                System.out.println(indent + (last ? "└── " : "├── ") + node.value);

                indent += last ? "    " : "│   ";

                printTree(node.right, indent, false);
                printTree(node.left, indent, true);
            }
        }

    }
    public static void picTree_AVL(AVLTree tree) {
        System.out.println("\nАВЛ-дерево:");
        tree.printTree();
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        int[] keys = {2, 6, 3, 8, 5, 9, 10, 1, 14, 7};

        for (int k : keys) {
            tree.insert(k);
        }

        picTree_AVL(tree);

        System.out.println("\nПоиск элемента 10: " + tree.search(10));
        System.out.println("Поиск элемента 100: " + tree.search(100));

        tree.delete(8);
        System.out.println("\nДерево после удаления 8:");
        tree.printTree();
    }
}

