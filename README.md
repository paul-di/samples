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
  4. hadoop -jar SimpleMapReduce-assembly-1.0.jar <input_folder> <output_folder>

## SQL query
Write exactly one SQL query that transforms table:

|  id  |  name |
| ---- | ----- |
| 1    | name1 |
| 2    | name2 |
| 3    | name3 |

to table:

| name1 | name2 | name3 |
| ----- | ----- | ----- |
| 1     | 2     | 3     |

How to run: http://sqlfiddle.com/ (PostgreSQL)

## My Stack Overflow profile
[paul_di](http://stackoverflow.com/users/2737635/paul-di)
