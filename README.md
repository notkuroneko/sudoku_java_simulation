# sudoku_java_simulation
Sudoku app recreation using Java (for Ubuntu)

# Functionalities:
- Gameplay, including board filling and timer
- Save & load game
- See game history

# Methodology:
- Reuse a sudoku board generation (boardgen) algorithm using C++
- Create a native method in Java to call the boardgen method
- Create the game UI using Java
  
# Project Components:
- Boardgen Library (C++, imported): <SudokuGenerator.h>; <SudokuSolver.h>
- JNI (Java Native Interface) Library (C++, imported) to write native methods for Java in C++: <jni.h>
- A Java file generating a C++ header file, declaring native methods in the header file and linking a Java native library: sudokuboardgen1.java
- A C++ file defining the native methods in the header files: boardgenobj.cpp
- The Java file for the base game UI: sudoku.java

# Running the Program:
- Open a Terminal, create the directory to the src folder of the project
- Run "$ export JAVA_HOME=<path_to_jdk>". This will create an alias for the directory to your JDK.
- Run "$ javac -h sudokuboardgen1.java". This will create a header file named <sudokuboardgen1.h> and link a Java native library named "libprojectbtlcpp.so"
- Run "$ gcc -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libprojectbtlcpp.so SudokuGenerator.cpp SudokuSolver.cpp boardgenobj.cpp". This will compile the cpp files and generate the Java native library "libprojectbtlcpp.so". All the libraries used in the Java native library must be compiled, including the libraries for Boardgen (SudokuGenerator.cpp & SudokuSolver.cpp) and the C++ file defining the native methods (boardgenobj.cpp).
- Run "$ javac sudoku.java" to compile the main program for the base game.
- Run "$ java -Djava.library.path=<insert_path_to_src_folder> sudoku". This will run the Java UI for the base game.

# Dev Log:
- ?/11/2023: Successfully built gameplay UI
- 22/12/2023: Trying to fix the package encapsulation
- 26/12/2023: Successfully ran boardgen on UI
- 5/1/2024: Successfully implement endgame mechanism when player finishes the board
- 8/1/2024: Found a bug in deselecting a box (can fill deselected box, further leftclick into the same box doesn't trigger the original UI change)

# Plan:
- Bug fix
- Timer
- Midgame save
- Load previous saves
- Show game history
- Project Encapsulation (sort compiled class files into a different directory)

Sudoku Boardgen Library: https://github.com/vaithak/Sudoku-Generator
