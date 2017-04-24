# Samples of my code
## 8QueenPuzzleGeneralCase
Project that solving more general case of [Eight_queens_puzzle](https://en.wikipedia.org/wiki/Eight_queens_puzzle) (any type and count of pieces, any size of board). 

How to run (from 8QueenPuzzleGeneralCase folder, needs [SBT](http://www.scala-sbt.org/)):
```
sbt "run XBoardSize YBoardSize Pieces'Names"
sbt "run 8 8 queen queen queen queen queen queen queen"
sbt "run 6 9 queen king king castle bishop knight"
```

## Simple map reduce
Write ETL code that transforms csv file to orc format. You should use Hadoop MapReduce Framework and Java. 
All data format errors should be logged. Write *create external table* hive query for orc file.
Csv file format:

|    Name     |   Balance   |   isActive   | 
| ----------- | ----------- | ------------ |
| Alex        | 234.15      | 1            |
| Bob         | wrongType   | 0            |
| Alisa       | 100000.00   |              |

## SQL query
Write exactly one SQL query that transforms table:

|  id  |  name |
| ---- | ----- |
| 1    | name1 |
| 2    | name2 |
| 3    | naem3 |

to table:

| name1 | name2 | name3 |
| ----- | ----- | ----- |
| 1     | 2     | 3     |
