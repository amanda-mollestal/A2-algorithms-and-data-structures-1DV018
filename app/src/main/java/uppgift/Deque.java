package uppgift;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<T> implements Iterable<T> {
  private Node head;
  private Node tail;
  private int count;

  private class Node {
    T data;
    Node nextNode;
    Node previousNode;
  }

  public Deque() {
    this.head = null;
    this.tail = null;
    this.count = 0;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public int size() {
    return count;
  }

  public void addFirst(T data) {
    if (data == null)
      throw new IllegalArgumentException("Data cannot be null");

    Node newNode = new Node();
    newNode.data = data;

    if (isEmpty()) {
      tail = newNode;
    } else {
      head.previousNode = newNode;
      newNode.nextNode = head;
    }

    head = newNode;
    count++;
  }

  public void addLast(T data) {
    if (data == null)
      throw new IllegalArgumentException("Data cannot be null");

    Node newNode = new Node();
    newNode.data = data;

    if (isEmpty()) {
      head = newNode;
    } else {
      tail.nextNode = newNode;
      newNode.previousNode = tail;
    }

    tail = newNode;
    count++;
  }


  public T removeFirst() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");

    T removedData = head.data;
    head = head.nextNode;
    count--;

    if (!isEmpty()) {
        head.previousNode = null;
    } else {
        tail = null;
    }

    return removedData;
}

  public T removeLast() {
    if (isEmpty()) throw new NoSuchElementException("Deque is empty");

    T removedData = tail.data;
    tail = tail.previousNode;
    count--;

    if (!isEmpty()) {
      tail.nextNode = null;
    } else {
      head = null;
    }

    return removedData;
}


  @Override
  public Iterator<T> iterator() {
    return new DequeIterator();
  }

  private class DequeIterator implements Iterator<T> {
    private Node currentNode = head;

    @Override
    public boolean hasNext() {
      return currentNode != null;
    }

    @Override
    public T next() {
      if (!hasNext())
        throw new NoSuchElementException();

      T data = currentNode.data;
      currentNode = currentNode.nextNode;
      return data;
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException("Remove operation not supported");
    }
  }

}
