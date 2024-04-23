package uppgift;

import java.util.ArrayList;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> {
  private Node root;

  private class Node {
    T data;
    Node left;
    Node right;
    int size;

    Node(T data) {
      this.data = data;
      this.size = 1;
    }
  }

  public int size() {
    return sizeOf(root);
  }

  private int sizeOf(Node node) {
    if (node == null) {
      return 0;
    } else {
      return node.size;
    }
  }

  public void add(T data) {
    root = add(root, data);
  }

  private Node add(Node node, T newNodeData) {

    if (node == null) {
      return new Node(newNodeData);
    }

    int result = newNodeData.compareTo(node.data);
    if (result < 0) {
      node.left = add(node.left, newNodeData);
    } else if (result > 0) {
      node.right = add(node.right, newNodeData);
    }

    node.size = sizeOf(node.left) + sizeOf(node.right) + 1;
    return node;
  }

  public boolean contains(T data) {
    return contains(root, data);
  }

  private boolean contains(Node node, T dataToFind) {
    if (node == null) {
      return false;
    }

    int result = dataToFind.compareTo(node.data);

    if (result < 0) {
      return contains(node.left, dataToFind);
    } else if (result > 0) {
      return contains(node.right, dataToFind);
    } else {
      return true;
    }
  }

  public void remove(T data) {
    root = remove(root, data);
  }

  private Node remove(Node node, T data) {
    if (node == null) {
      return null;
    }

    int cmp = data.compareTo(node.data);
    if (cmp < 0) {
      node.left = remove(node.left, data);
    } else if (cmp > 0) {
      node.right = remove(node.right, data);
    } else {
      if (node.right == null) {
        return node.left;
      }
      if (node.left == null) {
        return node.right;
      }

      Node temp = node;
      node = smallest(temp.right);
      node.right = removeMin(temp.right);
      node.left = temp.left;
    }

    node.size = sizeOf(node.left) + sizeOf(node.right) + 1;
    return node;
  }

  private Node smallest(Node node) {
    if (node.left == null) {
      return node;
    } else {
      return smallest(node.left);
    }
  }

  private Node removeMin(Node node) {
    if (node.left == null) {
      return node.right;
    }
    node.left = removeMin(node.left);
    node.size = sizeOf(node.left) + sizeOf(node.right) + 1;
    return node;
  }

  public int height() {
    return height(root);
  }

  private int height(Node node) {
    if (node == null) {
      return -1;
    }
    return Math.max(height(node.left), height(node.right)) + 1;
  }

  public void inorderIterator() {
    System.out.print(inorder(root));
  }

  private String inorder(Node node) {
    if (node == null) {
      return "";
    } else {
      String leftSubtree = inorder(node.left);
      String current = node.data + " ";
      String rightSubtree = inorder(node.right);
      return leftSubtree + current + rightSubtree;
    }
  }

  public void preorderIterator() {
    System.out.print(preorder(root));
  }

  private String preorder(Node node) {
    if (node == null) {
      return "";
    } else {
      String current = node.data + " ";
      String leftSubtree = preorder(node.left);
      String rightSubtree = preorder(node.right);
      return current + leftSubtree + rightSubtree;
    }
  }

  public void postorderIterator() {
    System.out.print(postorder(root));
  }

  private String postorder(Node node) {
    if (node == null) {
      return "";
    } else {
      String leftSubtree = postorder(node.left);
      String rightSubtree = postorder(node.right);
      String current = node.data + " ";
      return leftSubtree + rightSubtree + current;
    }
  }

  public void removeKLargest(int k) {
    if (k <= 0 || k > size()) {
      throw new IllegalArgumentException("Invalid k value");
    }

    int[] count = { 0 };
    remove(findKLargest(root, k, count));
  }

  private T findKLargest(Node node, int k, int[] count) {
    if (node == null) {
      return null;
    }

    T rightValue = findKLargest(node.right, k, count);

    if (count[0] == k) {
      return rightValue;
    }

    if (++count[0] == k) {
      return node.data;
    }

    T leftValue = findKLargest(node.left, k, count);

    return rightValue != null ? rightValue : leftValue;
  }

  public Node getRoot() {
    return root;
  }

  public int balanceFactor(Node node) {
    if (node == null)
      return 0;
    return height(node.left) - height(node.right);
  }

  private void depthOfLeaves(Node node, int currentDepth, List<Integer> depths) {
    if (node == null)
      return;
    if (node.left == null && node.right == null) {
      depths.add(currentDepth);
    }
    depthOfLeaves(node.left, currentDepth + 1, depths);
    depthOfLeaves(node.right, currentDepth + 1, depths);
  }

  public List<Integer> getDepthsOfLeaves() {
    List<Integer> depths = new ArrayList<>();
    depthOfLeaves(root, 0, depths);
    return depths;
  }

  private int countNodes(Node node) {
    if (node == null)
      return 0;
    return 1 + countNodes(node.left) + countNodes(node.right);
  }



  // SKALL TAS BORT INNAN SISTA PUSH
  public void printTree() {
    printTree(root, 0);
  }

  private void printTree(Node node, int depth) {
    if (node == null) {
      return;
    }

    printTree(node.right, depth + 1);

    for (int i = 0; i < depth; i++) {
      System.out.print("        ");
    }
    System.out.println(node.data);

    printTree(node.left, depth + 1);
  }

}
