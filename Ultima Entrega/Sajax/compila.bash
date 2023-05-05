#!/bin/bash
cd sajax_lex/
java -cp jflex.jar jflex.Main ejemplo.l
cd ..
cd constructorast/
java -cp cup.jar java_cup.Main -parser ConstructorASTExp -symbols ClaseLexica -nopositions ConstructorAST.cup
cd ..
javac -cp "cup.jar:." */*.java
java -cp "cup.jar:." constructorast.Main input.txt
./wat2wasm result.wat
node main-memory.js
