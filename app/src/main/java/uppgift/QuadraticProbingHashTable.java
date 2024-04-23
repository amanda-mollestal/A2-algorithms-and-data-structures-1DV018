package uppgift;

import java.util.Arrays;

public class QuadraticProbingHashTable<AnyType> {
  private static class HashEntry<AnyType> {
    public AnyType data;
    public boolean isActive;

    public HashEntry(AnyType givenData) {
      this(givenData, true);
    }

    public HashEntry(AnyType givenData, boolean status) {
      data = givenData;
      isActive = status;
    }
  }

  private static final int DEFAULT_TABLE_SIZE = 11;
  private HashEntry<AnyType>[] tableArray;
  private int totalInsertions;
  private int collisionCount;
  private int rehashCount;

  public QuadraticProbingHashTable() {
    this(DEFAULT_TABLE_SIZE);
  }

  public QuadraticProbingHashTable(int size) {
    allocateArray(size);
    collisionCount = 0;
    rehashCount = 0;
  }

  private void allocateArray(int arraySize) {
    tableArray = (HashEntry<AnyType>[]) new HashEntry[arraySize];
  }

  private static int nextPrime(int n) {
    if (n % 2 == 0) {
      n++;
    }
    for (; !isPrime(n); n += 2)
      ;
    return n;
  }

  private static boolean isPrime(int n) {
    if (n == 2 || n == 3)
      return true;
    if (n == 1 || n % 2 == 0)
      return false;
    for (int i = 3; i * i <= n; i += 2)
      if (n % i == 0)
        return false;
    return true;
  }

  public void insert(AnyType givenData) {
    int currentPosition = findPosition(givenData);
    if (isActive(currentPosition))
      return;

    tableArray[currentPosition] = new HashEntry<>(givenData);

    if (++totalInsertions > tableArray.length / 2)
      rehash();
  }

  private int findPosition(AnyType dataToFind) {
    int offset = 1;
    int currentPosition = myHash(dataToFind);

    while (tableArray[currentPosition] != null && !tableArray[currentPosition].data.equals(dataToFind)) {
      collisionCount++;
      currentPosition += offset;
      offset += 2;
      if (currentPosition >= tableArray.length)
        currentPosition -= tableArray.length;
    }

    return currentPosition;
  }

  private boolean isActive(int currentPosition) {
    return tableArray[currentPosition] != null && tableArray[currentPosition].isActive;
  }

  private int myHash(AnyType data) {
    int hashValue = data.hashCode();

    hashValue %= tableArray.length;
    if (hashValue < 0)
      hashValue += tableArray.length;

    return hashValue;
  }

  private void rehash() {
    // Save previous state for analysis
    int previousTableSize = tableArray.length;
    double preRehashCollisionRate = getCollisionRate();
    double preRehashBucketUtilization = getBucketUtilization();

    long startTime = System.nanoTime(); 

    System.out.println("");
    System.out.println("REHASHING: Table Size: " + tableArray.length + ", Vehicle amount : " + totalInsertions +
        ", Collision Count: " + collisionCount + ", Bucket Utilization: " + getBucketUtilization());

    HashEntry<AnyType>[] oldArray = tableArray;
    allocateArray(nextPrime(2 * oldArray.length));
    totalInsertions = 0;
    collisionCount = 0;

    for (HashEntry<AnyType> entry : oldArray)
        if (entry != null && entry.isActive)
            insert(entry.data);

    rehashCount++;
    System.out.println("Post-Rehash: Table Size: " + tableArray.length);

    long endTime = System.nanoTime(); 
    double timeTaken = (endTime - startTime) / 1e6; 

    // Post-rehash analysis
    double postRehashCollisionRate = getCollisionRate();
    double postRehashBucketUtilization = getBucketUtilization();
    double growthFactor = (double) tableArray.length / previousTableSize;

    System.out.println("Rehashing Time: " + timeTaken + " ms");
    System.out.println("Table Size Growth Factor: " + growthFactor);
    System.out.println("Collision Rate Reduction: " + (preRehashCollisionRate - postRehashCollisionRate));
    System.out.println("Bucket Utilization Improvement: " + (postRehashBucketUtilization - preRehashBucketUtilization));
    System.out.println("");

}



  public void remove(AnyType data) {
    int currentPosition = findPosition(data);
    if (isActive(currentPosition))
      tableArray[currentPosition].isActive = false;
  }

  public boolean contains(AnyType data) {
    int currentPosition = findPosition(data);
    return isActive(currentPosition);
  }

  public void printTable() {
    System.out.println("Table: ");
    for (int i = 0; i < tableArray.length; i++) {
      System.out.print(i + " ");
      if (tableArray[i] != null && tableArray[i].isActive) {
        System.out.println(tableArray[i].data);
      } else {
        System.out.println("null");
      }
    }
  }

  public int getCollisionCount() {
    return collisionCount;
  }

  public double getCollisionRate() {
    return (double) collisionCount / totalInsertions;
  }

  public double getBucketUtilization() {
    int usedBuckets = 0;
    for (HashEntry<AnyType> entry : tableArray) {
      if (entry != null && entry.isActive) {
        usedBuckets++;
      }
    }
    return (double) usedBuckets / tableArray.length;
  }

  public int getRehashCount() {
    return rehashCount;
  }

  public double getCollisionsPerBucket() {
    return tableArray.length == 0 ? 0 : (double) collisionCount / tableArray.length;
  }

  public double getLoadFactor() {
    return (double) totalInsertions / tableArray.length;
  }

  public double analyzeBucketDistribution() {
    int[] bucketCounts = new int[tableArray.length];
    for (HashEntry<AnyType> entry : tableArray) {
      if (entry != null && entry.isActive) {
        int bucketIndex = myHash(entry.data);
        bucketCounts[bucketIndex]++;
      }
    }

    double mean = (double) totalInsertions / tableArray.length;
    double sumSquareDifferences = 0.0;
    for (int count : bucketCounts) {
      sumSquareDifferences += Math.pow(count - mean, 2);
    }
    double standardDeviation = Math.sqrt(sumSquareDifferences / tableArray.length);
    
    return standardDeviation;
  }

  public String getDistributionSummary() {
    int[] bucketCounts = new int[tableArray.length];
    int emptyBuckets = 0;
    int maxCount = 0;
    for (HashEntry<AnyType> entry : tableArray) {
        if (entry != null && entry.isActive) {
            int bucketIndex = myHash(entry.data);
            bucketCounts[bucketIndex]++;
            maxCount = Math.max(maxCount, bucketCounts[bucketIndex]);
        }
    }

    int[] distribution = new int[maxCount + 1];
    for (int count : bucketCounts) {
        distribution[count]++;
        if (count == 0) emptyBuckets++;
    }

    StringBuilder sb = new StringBuilder();
    sb.append("Empty Buckets: ").append(emptyBuckets).append("\n");
    for (int i = 1; i < distribution.length; i++) {
        if (distribution[i] > 0) {
            sb.append("Buckets with ").append(i).append(" items: ").append(distribution[i]).append("\n");
        }
    }
    return sb.toString();
}


}
