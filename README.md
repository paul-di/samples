# Samples of code
## 8QueenPuzzleGeneralCase
Project that solving more general case of [Eight_queens_puzzle](https://en.wikipedia.org/wiki/Eight_queens_puzzle) 
(any type and count of pieces, any size of board). 

How to run (from 8QueenPuzzleGeneralCase folder, needs [SBT](http://www.scala-sbt.org/)):
```
sbt "run XBoardSize YBoardSize Pieces'Names"
sbt "run 8 8 queen queen queen queen queen queen queen queen"
sbt "run 6 9 queen king king castle bishop knight"
```

## Simple map reduce
Write ETL code that transforms csv file to orc format. You should use Hadoop MapReduce Framework and Java. 
All data format errors should be logged. Write *"create external table"* hive-query for orc file.
Csv file format:

|    Name     |   Balance   |   isActive   | 
| ----------- | ----------- | ------------ |
| Alex        | 234.15      | 1            |
| Bob         | wrongType   | 0            |
| Alisa       | 100000.00   |              |

How to run:
  1. Get hadoop cluster. [Local mode](http://hadoop.apache.org/docs/r2.7.2/hadoop-project-dist/hadoop-common/SingleCluster.html#Hadoop:_Setting_up_a_Single_Node_Cluster.)
  2. Set hadoop version in build.sbt (usually you can skip that step)
  3. Build jar with *sbt assembly*
  4. hadoop jar SimpleMapReduce-assembly-1.0.jar <input_folder> <output_folder>

## Round train
Imagine that you are stuck inside a strange railway train. It consists of several passenger wagons only. The last wagon is connected to the first one at the round railway. You should calculate size of the train (count of wagons) to escape. You can move forward and backward inside a train and turn on or turn off light inside a visited wagon. Initial state of wagons is random, all windows are painted black, all wagons are the same, blah, blah, blah...
