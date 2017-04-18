# Samples of my code
## 8QueenPuzzleGeneralCase
Project that solving more general case of [Eight_queens_puzzle](https://en.wikipedia.org/wiki/Eight_queens_puzzle) (any type and count of pieces, any size of board). 

How to run (from 8QueenPuzzleGeneralCase folder, needs [SBT](http://www.scala-sbt.org/)):
```
sbt "run XBoardSize YBoardSize Pieces'Names"
sbt "run 8 8 queen queen queen queen queen queen queen"
sbt "run 6 9 queen king king castle bishop knight"
``` 
## AnnotationService (REST)
### Task description
You should write a REST servise that annotates text with information about cities. Information about cities can be get here [cities15000](http://download.geonames.org/export/dump/). Service should support matching by name and alternative names, exact and lowercased match (configurable). Service should return results in JSON or XML format. Example: 
```
val text = "NeW YoKk CiTy is the most populous..."
```
```
matches : {
  match : {
    geoid : NUMBER,
    name : "New York City",
    covered_text : "NeW YoKk CiTy",
    start : 0,
    end : 13, (java  style exclusive)
    score : 1.0
  } 
}  
```
