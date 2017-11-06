import java.util.ArrayList;
import java.util.LinkedList;

public class kth_AVL {
    interface structure_Tree<E> extends Iterable<E> {

        public boolean search(E e);
        public boolean insert(E e);
        public boolean delete(E e);
        public void in_order();
        public void post_order();
        public void previous_order();
        public int getSize();
        public boolean isEmpty();
        public java.util.Iterator<E> iterator();

    static class AVLTree<E extends Comparable<E>> extends BST<E> {

        public AVLTree() {
        }


        public AVLTree(E[] objects) {
            super(objects);
        }

        public E find(int k) {
            if ((k < 0) || (k > size)) {
                return null;
            } else {
                return find(k, (AVLTreeNode<E>) root);
            }
        }

        public E find(int k, AVLTreeNode<E> node) {
            AVLTreeNode<E> A = (AVLTreeNode<E>)node.left;
            AVLTreeNode<E> B = (AVLTreeNode<E>)node.right;

            if ((A == null)&&(k == 1)) {
                return node.element;
            } else if ((A == null)&&(k == 2)) {
                return B.element;
            } else if(k <= A.size) {
                return find(k, A);
            } else if(k == A.size + 1) {
                return node.element;
            } else {
                return find(k - A.size - 1, B);
            }
        }

        public void updateSize() {
            updateSize((AVLTreeNode<E>) root);
        }

        private int updateSize(AVLTreeNode<E> node) {
            if (node == null) {
                return 0;
            } else {
                node.size = 1 + updateSize((AVLTreeNode<E>)(node.left)) + updateSize((AVLTreeNode<E>)(node.right));
                return node.size;
            }
        }

        @Override

        protected AVLTreeNode<E> createNewNode(E e) {
            return new AVLTreeNode<E>(e);
        }


        private void updateHeight(AVLTreeNode<E> node) {

            if (node.left == null && node.right == null)

                node.height = 0;

            else if (node.left == null)

                node.height = 1 + ((AVLTreeNode<E>) (node.right)).height;

            else if (node.right == null)

                node.height = 1 + ((AVLTreeNode<E>) (node.left)).height;

            else

                node.height = 1 + Math.max(

                        ((AVLTreeNode<E>) (node.right)).height,

                        ((AVLTreeNode<E>) (node.left)).height);
        }

        @Override
        public boolean insert(E e) {

            boolean successful = super.insert(e);

            if (!successful)

                return false;

            else {

                balancePath(e);

                updateSize();

            }

            return true;
        }


        private void balancePath(E e) {

            java.util.ArrayList<tree_Node<E>> path = path(e);

            for (int i = path.size() - 1; i >= 0; i--) {

                AVLTreeNode<E> A = (AVLTreeNode<E>) (path.get(i));

                updateHeight(A);

                AVLTreeNode<E> parentOfA = (A == root) ? null : (AVLTreeNode<E>) (path.get(i - 1));

                switch (balanceFactor(A)) {

                    case -2:

                        if (balanceFactor((AVLTreeNode<E>) A.left) <= 0) {

                            balanceLL(A, parentOfA);

                        } else {

                            balanceLR(A, parentOfA);

                        }
                        break;

                    case +2:

                        if (balanceFactor((AVLTreeNode<E>) A.right) >= 0) {

                            balanceRR(A, parentOfA);

                        } else {

                            balanceRL(A, parentOfA);
                        }
                }
            }
        }


        private int balanceFactor(AVLTreeNode<E> node) {

            if (node.right == null)

                return -node.height;

            else if (node.left == null)

                return +node.height;

            else

                return ((AVLTreeNode<E>) node.right).height - ((AVLTreeNode<E>) node.left).height;
        }


        private void balanceLL(tree_Node<E> A, tree_Node<E> parentOfA) {

            tree_Node<E> B = A.left;

            if (A == root) {

                root = B;

            } else {

                if (parentOfA.left == A) {

                    parentOfA.left = B;

                } else {

                    parentOfA.right = B;
                }
            }

            A.left = B.right;

            B.right = A;

            updateHeight((AVLTreeNode<E>) A);

            updateHeight((AVLTreeNode<E>) B);
        }


        private void balanceLR(tree_Node<E> A, tree_Node<E> parentOfA) {

            tree_Node<E> B = A.left;

            tree_Node<E> C = B.right;

            if (A == root) {

                root = C;

            } else {

                if (parentOfA.left == A) {

                    parentOfA.left = C;

                } else {

                    parentOfA.right = C;
                }
            }

            A.left = C.right;

            B.right = C.left;

            C.left = B;

            C.right = A;


            updateHeight((AVLTreeNode<E>) A);

            updateHeight((AVLTreeNode<E>) B);

            updateHeight((AVLTreeNode<E>) C);
        }


        private void balanceRR(tree_Node<E> A, tree_Node<E> parentOfA) {

            tree_Node<E> B = A.right;

            if (A == root) {

                root = B;

            } else {

                if (parentOfA.left == A) {

                    parentOfA.left = B;

                } else {

                    parentOfA.right = B;
                }
            }

            A.right = B.left;

            B.left = A;

            updateHeight((AVLTreeNode<E>) A);

            updateHeight((AVLTreeNode<E>) B);
        }


        private void balanceRL(tree_Node<E> A, tree_Node<E> parentOfA) {

            tree_Node<E> B = A.right;

            tree_Node<E> C = B.left;

            if (A == root) {

                root = C;

            } else {

                if (parentOfA.left == A) {

                    parentOfA.left = C;

                } else {

                    parentOfA.right = C;
                }
            }

            A.right = C.left;

            B.left = C.right;

            C.left = A;

            C.right = B;


            updateHeight((AVLTreeNode<E>) A);

            updateHeight((AVLTreeNode<E>) B);

            updateHeight((AVLTreeNode<E>) C);
        }

        @Override

        public boolean delete(E element) {

            if (root == null)

                return false;


            tree_Node<E> parent = null;

            tree_Node<E> current = root;

            while (current != null) {

                if (element.compareTo(current.element) < 0) {

                    parent = current;

                    current = current.left;

                } else if (element.compareTo(current.element) > 0) {

                    parent = current;

                    current = current.right;

                } else

                    break;
            }

            if (current == null)

                return false;


            if (current.left == null) {

                if (parent == null) {

                    root = current.right;

                } else {

                    if (element.compareTo(parent.element) < 0)

                        parent.left = current.right;

                    else

                        parent.right = current.right;


                    balancePath(parent.element);
                }

            } else {

                tree_Node<E> parentOfRightMost = current;

                tree_Node<E> rightMost = current.left;

                while (rightMost.right != null) {

                    parentOfRightMost = rightMost;

                    rightMost = rightMost.right;
                }


                current.element = rightMost.element;


                if (parentOfRightMost.right == rightMost)

                    parentOfRightMost.right = rightMost.left;

                else

                    parentOfRightMost.left = rightMost.left;


                balancePath(parentOfRightMost.element);
            }

            size--;

            updateSize();

            return true;
        }

        protected static class AVLTreeNode<E extends Comparable<E>> extends BST.tree_Node<E> {

            protected int height = 0;

            private int size = 0;

            public AVLTreeNode(E o) {

                super(o);
            }

            public void setSize(int size) {

                this.size = size;
            }

            public int getSize() {

                return size;
            }
        }
    }

    static class BST<E extends Comparable<E>> extends AbstractTree<E> {

        protected tree_Node<E> root;

        protected int size = 0;

        public void inorder2() {

            if (root == null) {

                return;
            }

            LinkedList<tree_Node<E>> list = new LinkedList<>();

            LinkedList<tree_Node<E>> stack = new LinkedList<>();

            stack.add(root);

            while (!stack.isEmpty()) {

                tree_Node<E> node = stack.getFirst();

                if ((node.left != null) && (!list.contains(node.left))) {

                    stack.push(node.left);

                } else {

                    stack.removeFirst();

                    list.add(node);

                    if (node.right != null) {

                        stack.addFirst(node.right);
                    }
                }
            }
            for (tree_Node<E> treeNode : list) {

                System.out.print(treeNode.element + " ");
            }
        }

        public boolean full_BST() {

            return size == Math.round(Math.pow(2, height()) - 1);
        }


        public int height() {

            return height(root);
        }

        public int height(tree_Node<E> node) {

            if (node == null) {

                return 0;

            } else {

                return 1 + Math.max(height(node.left), height(node.right));
            }
        }


        public BST() {
        }


        public BST(E[] objects) {

            for (int i = 0; i < objects.length; i++)

                insert(objects[i]);
        }


        public ArrayList<E> searchPath(E e) {

            tree_Node<E> current = root;

            ArrayList<E> result = new ArrayList<>();

            while (current != null) {

                result.add(current.element);

                if (e.compareTo(current.element) < 0) {

                    current = current.left;

                } else if (e.compareTo(current.element) > 0) {

                    current = current.right;

                } else {

                    return result;
                }
            }
            return result;
        }

        @Override

        public boolean search(E e) {

            tree_Node<E> current = root;

            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    current = current.right;
                } else

                    return true;
            }

            return false;
        }

        @Override

        public boolean insert(E e) {
            if (root == null)
                root = createNewNode(e);
            else {

                tree_Node<E> parent = null;
                tree_Node<E> current = root;
                while (current != null)
                    if (e.compareTo(current.element) < 0) {
                        parent = current;
                        current = current.left;
                    } else if (e.compareTo(current.element) > 0) {
                        parent = current;
                        current = current.right;
                    } else
                        return false;


                if (e.compareTo(parent.element) < 0)

                    parent.left = createNewNode(e);

                else

                    parent.right = createNewNode(e);
            }

            size++;

            return true;
        }

        protected tree_Node<E> createNewNode(E e) {

            return new tree_Node<E>(e);
        }

        @Override

        public void in_order() {

            in_order(root);
        }


        protected void in_order(tree_Node<E> root) {

            if (root == null)

                return;

            in_order(root.left);

            System.out.print(root.element + " ");

            in_order(root.right);

        }

        @Override

        public void post_order() {

            post_order(root);
        }


        protected void post_order(tree_Node<E> root) {

            if (root == null)

                return;

            post_order(root.left);

            post_order(root.right);

            System.out.print(root.element + " ");
        }

        @Override

        public void previous_order() {

            previous_order(root);
        }


        protected void previous_order(tree_Node<E> root) {

            if (root == null)

                return;

            System.out.print(root.element + " ");

            previous_order(root.left);

            previous_order(root.right);
        }


        public static class tree_Node<E extends Comparable<E>> {

            protected E element;

            protected tree_Node<E> left;

            protected tree_Node<E> right;

            public tree_Node(E e) {

                element = e;
            }
        }

        @Override

        public int getSize() {
            return size;
        }

        public tree_Node<E> getRoot() {
            return root;
        }

        public java.util.ArrayList<tree_Node<E>> path(E e) {

            java.util.ArrayList<tree_Node<E>> list = new java.util.ArrayList<tree_Node<E>>();

            tree_Node<E> current = root;

            while (current != null) {

                list.add(current);

                if (e.compareTo(current.element) < 0) {

                    current = current.left;

                } else if (e.compareTo(current.element) > 0) {

                    current = current.right;

                } else

                    break;
            }

            return list;
        }

        @Override

        public boolean delete(E e) {

            tree_Node<E> parent = null;

            tree_Node<E> current = root;

            while (current != null) {

                if (e.compareTo(current.element) < 0) {

                    parent = current;

                    current = current.left;

                } else if (e.compareTo(current.element) > 0) {

                    parent = current;

                    current = current.right;

                } else

                    break;
            }

            if (current == null)

                return false;



            if (current.left == null) {

                if (parent == null) {

                    root = current.right;

                } else {

                    if (e.compareTo(parent.element) < 0)

                        parent.left = current.right;

                    else

                        parent.right = current.right;
                }

            } else {

                tree_Node<E> parentOfRightMost = current;

                tree_Node<E> rightMost = current.left;

                while (rightMost.right != null) {

                    parentOfRightMost = rightMost;

                    rightMost = rightMost.right;
                }


                current.element = rightMost.element;


                if (parentOfRightMost.right == rightMost)

                    parentOfRightMost.right = rightMost.left;

                else

                    parentOfRightMost.left = rightMost.left;
            }

            size--;

            return true;
        }

        @Override

        public java.util.Iterator<E> iterator() {

            return new InorderIterator();

        }


        private class InorderIterator implements java.util.Iterator<E> {

            private java.util.ArrayList<E> list = new java.util.ArrayList<E>();

            private int current = 0;

            public InorderIterator() {

                in_order();
            }


            private void in_order() {

                in_order(root);
            }


            private void in_order(tree_Node<E> root) {

                if (root == null)

                    return;

                in_order(root.left);

                list.add(root.element);

                in_order(root.right);
            }

            @Override

            public boolean hasNext() {

                if (current < list.size())

                    return true;

                return false;
            }

            @Override

            public E next() {

                return list.get(current++);
            }

            @Override

            public void remove() {

                delete(list.get(current));

                list.clear();

                in_order();
            }
        }


        public void clear() {

            root = null;

            size = 0;
        }
    }

    static abstract class AbstractTree<E> implements structure_Tree<E> {
        @Override
        public void in_order() {
        }
        @Override
        public void post_order() {
        }
        @Override
        public void previous_order() {
        }
        @Override
        public boolean isEmpty() {

            return getSize() == 0;
        }
        @Override
        public java.util.Iterator<E> iterator() {

            return null;
        }
    }



        public static void main(String[] args) {
            AVLTree<Integer> tree = new AVLTree<Integer>();
            for (int i = 1; i <= 1000; i++) {
                tree.insert(i);
            }
            System.out.println(tree.find(1));
            System.out.println(tree.find(20));
            System.out.println(tree.find(25));
            System.out.println(tree.find(30));
            System.out.println(tree.find(35));
            System.out.println(tree.find(40));
            System.out.println(tree.find(45));
            System.out.println(tree.find(50));

        }
    }
}