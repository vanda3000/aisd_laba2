class Node {
    int key;
    Node left, right;

    public Node(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
    }
}

class BinarySearchTree {
    public Node root;

    public BinarySearchTree() {
        root = null;
    }

    // Вставка узла
    public void insert(int key) {
        root = insertRec(root, key);
    }

    private Node insertRec(Node root, int key) {
        if (root == null) {
            root = new Node(key);
            return root;
        }
        if (key < root.key) {
            root.left = insertRec(root.left, key);
        } else if (key > root.key) {
            root.right = insertRec(root.right, key);
        }
        return root;
    }

    // Поиск узла
    public boolean search(int key) {
        return searchRec(root, key) != null;
    }

    private Node searchRec(Node root, int key) {
        if (root == null || root.key == key) {
            return root;
        }
        if (key < root.key) {
            return searchRec(root.left, key);
        }
        return searchRec(root.right, key);
    }

    // Удаление узла
    public void delete(int key) {
        root = deleteRec(root, key);
    }

    private Node deleteRec(Node root, int key) {
        if (root == null) {
            return root;
        }
        if (key < root.key) {
            root.left = deleteRec(root.left, key);
        } else if (key > root.key) {
            root.right = deleteRec(root.right, key);
        } else {
            // Узел с одним или без детей
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }
            // Узел с двумя детьми: получить наименьший узел из правого поддерева
            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;

    }

    private int minValue(Node root) {
        int minValue = root.key;
        while (root.left != null) {
            root = root.left;
            minValue = root.key;
        }
        return minValue;
    }

    public void inorder() {
        inorderRec(root);
        System.out.println();
    }

    private void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.key + " ");
            inorderRec(root.right);
        }
    }

    public void printTree() {
        printTree(root, "", true);
    }

    private void printTree(Node node, String indent, boolean last) {
        if (node != null) {
            System.out.println(indent + (last ? "└── " : "├── ") + node.key);

            indent += last ? "    " : "│   ";

            printTree(node.right, indent, false);
            printTree(node.left, indent, true);
        }
    }

    public static void picTree_bst(BinarySearchTree tree) {
        System.out.println("\nБинарное дерево:");
        tree.printTree();
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] keys = {2, 6, 3, 8, 5, 9, 10, 1, 14, 7};

        for (int k : keys) {
            bst.insert(k);
        }

        picTree_bst(bst);

        // Поиск узла
        int key = 5;
        System.out.println("Найти " + key + ": " + (bst.search(key) ? "Найден" : "Такого ключа нет"));

        // Удаление узла
        int delkey = 9;
        bst.delete(delkey);
        System.out.println("Упорядоченный массив после удаления "+delkey+":");
        bst.inorder();
        picTree_bst(bst);
    }


}
