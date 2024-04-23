package uppgift;

import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Iterator;

public class RandomQueue<T> implements Iterable<T> {
  private T[] queue;
  private int count;

  public RandomQueue() {
    queue = (T[]) new Object[2];
    count = 0;
  }

  public boolean isEmpty() {
    return count == 0;
  }

  public int size() {
    return count;
  }

  public void enqueue(T value) {
    if (value == null) {
      throw new IllegalArgumentException("Value cannot be null");
    }
    if (count == queue.length) {
      adjustSize(2 * queue.length);
    }
    queue[count++] = value;
  }

  private void adjustSize(int capacity) {
    T[] copy = (T[]) new Object[capacity];
    for (int i = 0; i < count; i++) {
      copy[i] = queue[i];
    }
    queue = copy;
  }

  public T dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("Queue is empty");
    }

    int randomPos = new Random().nextInt(count);
    T value = queue[randomPos];

    queue[randomPos] = queue[count - 1];
    queue[count - 1] = null;
    count--;

    if (count > 0 && count == queue.length / 4) {
      adjustSize(queue.length / 2);
    }
    return value;
  }

  @Override
  public Iterator<T> iterator() {
      return new RandomIterator();
  }

  private class RandomIterator implements Iterator<T> {
      private int index = 0;
      private final T[] shuffledQueue;

      public RandomIterator() {
        shuffledQueue = (T[]) new Object[count];
          System.arraycopy(queue, 0, shuffledQueue, 0, count);
          shuffleQueue(shuffledQueue);
      }

      private void shuffleQueue(T[] array) {
          Random randomizer = new Random();
          for (int i = count - 1; i > 0; i--) {
              int j = randomizer.nextInt(i + 1);
              T temp = array[j];
              array[j] = array[i];
              array[i] = temp;
          }
      }

      @Override
      public boolean hasNext() {
          return index < count;
      }

      @Override
      public T next() {
          if (!hasNext()) {
              throw new NoSuchElementException();
          }
          return shuffledQueue[index++];
      }

      @Override
      public void remove() {
          throw new UnsupportedOperationException("Remove method not supported");
      }
  }
}
