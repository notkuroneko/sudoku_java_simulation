#include "SudokuGenerator.h"
#include <iostream>
#include <fstream>
#include <array>
#include "/usr/lib/jvm/java-16-openjdk-amd64/include/jni.h"
#include "/usr/lib/jvm/java-16-openjdk-amd64/include/linux/jni_md.h"
#include "sudokuboardgen1.h"

using namespace std;

JNIEXPORT void JNICALL Java_sudokuboardgen1_boardgen(JNIEnv *env, jobject thisObj) {
    Generator g = Generator();
    std::array<std::array<int, 9>, 9> a = g.getGrid();
    std::array<std::array<int, 9>, 9> b = g.getSolution();
    string temp; int count = 0;
    ofstream o_f("sudokuboardgentest.txt");
    for (auto i=0; i<9; i++){
        for (auto j=0; j<9; j++) o_f << a[i][j] << endl;
    }
    for (auto i=0; i<9; i++){
        for (auto j=0; j<9; j++) o_f << b[i][j] << endl;
    }
    o_f.close();
    return;
}

JNIEXPORT void JNICALL Java_sudokuboardgen1_fileDelete(JNIEnv *env, jobject thisObj) {
    std::remove("sudokuboardgentest.txt");
    return;
}

/*
gcc -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libprojectbtlcpp.so SudokuGenerator.cpp SudokuSolver.cpp boardgenobj.cpp
java -Djava.library.path sudoku

https://www3.ntu.edu.sg/home/ehchua/programming/java/javanativeinterface.html
*/