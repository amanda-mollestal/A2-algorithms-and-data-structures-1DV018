package uppgift;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>>  {
  private Node<T> root;
  private static final int ALLOWED_IMBALANCE = 1;

  private static class Node<T> {
    T data;
    Node<T> left;
    Node<T> right;
    int height;

    Node(T data) {
        this(data, null, null);
    }

    Node(T data, Node<T> left, Node<T> right) {
        this.data = data;
        this.left = left;
        this.right = right;
        height = 0;
    }
}

private int height(Node<T> node) {
  return node == null ? -1 : node.height;
}

public void add(T newNodeData) {
  root = add(newNodeData, root);
}

private Node<T> add(T newNodeData, Node<T> node) {
  if(node == null)
      return new Node<>(newNodeData);

  int compareResult = newNodeData.compareTo(node.data);

  if(compareResult < 0)
      node.left = add(newNodeData, node.left);
  else if(compareResult > 0)
      node.right = add(newNodeData, node.right);


  return balance(node);
}

private Node<T> balance(Node<T> node) {
  if(node == null)
      return node;

  if(height(node.left) - height(node.right) > ALLOWED_IMBALANCE) {
      if(height(node.left.left) >= height(node.left.right))
          node = rotateWithLeftChild(node);
      else
          node = doubleWithLeftChild(node);
  } else if(height(node.right) - height(node.left) > ALLOWED_IMBALANCE) {
      if(height(node.right.right) >= height(node.right.left))
          node = rotateWithRightChild(node);
      else
          node = doubleWithRightChild(node);
  }

  node.height = Math.max(height(node.left), height(node.right)) + 1;
  return node;
}

private Node<T> rotateWithLeftChild(Node<T> k2) {
  Node<T> k1 = k2.left;
  k2.left = k1.right;
  k1.right = k2;
  k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
  k1.height = Math.max(height(k1.left), k2.height) + 1;
  return k1;
}

private Node<T> doubleWithLeftChild(Node<T> k3) {
  k3.left = rotateWithRightChild(k3.left);
  return rotateWithLeftChild(k3);
}

private Node<T> rotateWithRightChild(Node<T> k1) {
  Node<T> k2 = k1.right;
  k1.right = k2.left;
  k2.left = k1;
  k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
  k2.height = Math.max(k1.height, height(k2.right)) + 1;
  return k2;
}

private Node<T> doubleWithRightChild(Node<T> k1) {
  k1.right = rotateWithLeftChild(k1.right);
  return rotateWithRightChild(k1);
}

public void remove(T nodeToRemove) {
  root = remove(nodeToRemove, root);
}

private Node<T> remove(T nodeToRemove, Node<T> root) {
  if (root == null) {
      return root; 
  }

  int compareResult = nodeToRemove.compareTo(root.data);

  if (compareResult < 0) {
      root.left = remove(nodeToRemove, root.left);
  } else if (compareResult > 0) {
      root.right = remove(nodeToRemove, root.right);
  } else if (root.left != null && root.right != null) { 
      root.data = smallest(root.right).data;
      root.right = remove(root.data, root.right);
  } else {
      root = (root.left != null) ? root.left : root.right;
  }
  return balance(root);
}

private Node<T> smallest(Node<T> node) {
  if (node == null) {
      return null;
  } else if (node.left == null) {
      return node;
  }
  return smallest(node.left);
}


public void printTree() {
  printTree(root, 0);
}

/* 
private void printTree(Node<T> node, int depth) {
  if (node == null) {
    return;
  }

  printTree(node.right, depth + 1);

  for (int i = 0; i < depth; i++) {
    System.out.print("        ");
  }
  System.out.println(node.data);

  printTree(node.left, depth + 1);
}*/

private void printTree(Node<T> node, int depth) {
        if (node == null) {
            printWhitespaces(depth);
            System.out.println("-");
        } else {
            printTree(node.right, depth + 1);
            printWhitespaces(depth);
            System.out.println(node.data);
            printTree(node.left, depth + 1);
        }
    }

    private void printWhitespaces(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print("   ");
        }
    }

    public boolean isBalanced() {
      return isBalanced(root);
  }

  private boolean isBalanced(Node<T> node) {
      if (node == null) {
          return true;
      }

      int heightDiff = height(node.left) - height(node.right);
      if (Math.abs(heightDiff) > 1) {
          return false;
      } else {
          return isBalanced(node.left) && isBalanced(node.right);
      }
  }

  public int getHeight() {
      return this.height(root);
  }

  public Node<T> getRoot() {
    return root;
}

  public int balanceFactor(Node<T> node) {
    if (node == null) return 0;
    return height(node.left) - height(node.right);
}

private void depthOfLeaves(Node<T> node, int currentDepth, List<Integer> depths) {
    if (node == null) return;
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

private int countNodes(Node<T> node) {
  if (node == null) return 0;
  return 1 + countNodes(node.left) + countNodes(node.right);
}






}
