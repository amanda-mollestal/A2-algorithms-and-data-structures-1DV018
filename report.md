### Report on Comparative Analysis of Binary Search Tree (BST) and AVL Tree Performance

#### Introduction
This report presents a detailed analysis of the performance of Binary Search Trees (BST) versus AVL Trees. The experiment was designed to compare these two data structures in terms of operation time (insertion and removal) and tree height under different scenarios. The dataset size and distribution pattern (skewed vs. random) were varied to observe their impact on tree performance.

#### Methodology
The experiment was conducted using Java, with trees of sizes 250, 500, 1000, 2000, and 4000 nodes. For each size, both skewed and randomly distributed datasets were tested. Each tree type (BST and AVL) underwent operations of insertion and removal. Key metrics measured were:
- **Add Time**: Time taken to insert all nodes.
- **Remove Time**: Time taken to remove all nodes.
- **Tree Height**: The height of the tree post-insertion.

#### Results

Full output of the experiment is available in tree_experiment_results.txt file.

The results highlight significant differences between the BST and AVL Tree, particularly in skewed datasets. 

**Skewed Data Patterns:**
- **BST** showed dramatically increasing add time and height with increasing tree size, reaching heights equal to the size of the tree minus one. This indicates a degeneration to a linked list structure.
- **AVL Tree**, in contrast, maintained a much lower and consistent height, indicative of its self-balancing nature. The add and remove times were also significantly lower than BST.

**Random Data Patterns:**
- Both trees performed comparably in terms of add and remove times. However, AVL Tree maintained a consistently lower height, showcasing better balance compared to BST.

**Key Observations:**
- **BST Performance**: In skewed datasets, BST's performance degrades severely due to the linear structure it acquires, leading to higher operation times and tree heights.
- **AVL Tree Efficiency**: AVL's self-balancing mechanism keeps the tree height minimal, leading to more efficient operations, especially noticeable in skewed datasets.

#### Discussion
The results align with theoretical expectations. BST, lacking any self-balancing mechanism, becomes inefficient in skewed datasets, resembling a linear list. This leads to poor performance in both addition and removal operations. AVL Trees, with their balancing algorithms, maintain a more consistently optimal structure, resulting in better performance, especially noticeable in skewed data scenarios.

**BST** is simpler but becomes inefficient for skewed data, where each insertion or removal operation can potentially become O(n).

**AVL Tree**, with its rotation mechanisms to maintain balance, ensures an O(log n) performance for insertion and removal, regardless of data pattern, making it more suitable for cases where data might be skewed or where balanced tree height is critical for performance.

#### Conclusion
The experiment validates the theoretical advantages of AVL Trees over regular BSTs in terms of operational efficiency and tree height, particularly in skewed datasets. While BSTs can be efficient with random data, their performance significantly deteriorates with skewed data, unlike AVL Trees which maintain consistent efficiency irrespective of data distribution.

This analysis underscores the importance of choosing the right data structure based on the expected data patterns and operational requirements in practical applications.

---


# Vehicle Registration Hash Function Assessment
## Overview
This report examines the performance of a hash function in a hash table handling vehicle registration data. It focuses on how conflict frequencies change with varying table sizes and vehicle counts.
## Experimental Design
### Setup Parameters
- **Hash Table Sizes**: Tested with sizes 10, 20, 40, 80, 160.
- **Counts of Vehicles**: Experimented with capacities matching vehicle counts, specifically 10, 20, 40, 80, and 160 vehicles.
- **Evaluated Metrics**:
  - Initial Conflict Averages: Conflicts encountered before probing.
  - Probing Conflict Averages: Conflicts during the probing process.
  - Total Conflict Averages: Combined initial and probing conflicts.
### Structure of Hash Function
- **Design Principle**: Utilizes vehicle registration numbers for hashing.
- **Conflict Resolution Approach**: Specific method implemented to handle hash collisions (e.g., quadratic probing).
## Observations and Discussion
### Relationship Between Table Size and Conflict Occurrence
- **Small Capacities (10, 20)**: Noted an elevated level of total conflicts, underscoring the inadequacy for handling larger data sets.
- **Intermediate Capacities (40, 80)**: Observed a decline in total conflicts, indicative of a more dispersed distribution of hash values.
- **Larger Capacity (160)**: Showed superior efficiency in conflict management, underlining the advantages of expansive table sizes.
### Vehicle Quantity Impact on Conflict Frequency
- **Rising Vehicle Numbers**: Uniform increase in conflict rates across all table sizes, underscoring the influence of data volume on hashing efficiency.
### Efficacy of the Hash Function
- **Initial Conflict Trends**: Tended to be lower with expanding table sizes, suggesting more effective dispersion of data.
- **Probing Conflict Patterns**: Displayed a mixed trend, generally diminishing with increased table sizes, emphasizing the significance of the probing mechanism.
## Comparative Observations
- **Table Size Versus Data Volume**: Larger tables tend to mitigate conflict frequencies, enhancing overall hash table functionality. The hashing algorithm appears to distribute data more uniformly across larger spaces.
- **Significance of Probing Method**: The efficacy of the conflict resolution method is paramount, particularly in scenarios involving substantial data volumes.
## Conclusive Remarks
The hash function, tailored for vehicle registration numbers, exhibits variable efficiency depending on the table size and the volume of vehicle data. Peak performance, characterized by reduced conflict rates, is most evident in larger table capacities. The findings from this investigation offer valuable perspectives on crafting efficient hash tables with robust mechanisms for conflict resolution, especially pertinent in scenarios involving extensive data sets such as vehicle registrations.

