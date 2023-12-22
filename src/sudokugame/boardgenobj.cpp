#include "SudokuGenerator.h"
#include <iostream>
#include <fstream>
#include <array>
#include "/usr/lib/jvm/java-16-openjdk-amd64/include/jni.h"
#include "/usr/lib/jvm/java-16-openjdk-amd64/include/linux/jni_md.h"
#include "sudokuboardgen1.h"

using namespace std;

JNIEXPORT void JNICALL Java_sudoku_boardgen(JNIEnv *env, jobject thisObj) {
    Generator g = Generator();
    std::array<std::array<int, 9>, 9> a = g.getGrid();
    string temp;
    ofstream o_f("sudokuboardgentest.txt");
    for (auto i=0; i<9; i++){
        for (auto j=0; j<9; j++) o_f << a[i][j];
        o_f << endl;
    }
    o_f.close();
    ifstream i_f;
    i_f.open("sudokuboardgentest.txt");
    while (i_f) {
        getline(i_f,temp);
        cout << temp << endl;
    }
    i_f.close();
    std::remove("sudokuboardgentest.txt");
    return;
}

/*
gcc -fPIC -I"$JAVA_HOME/include" -I"$JAVA_HOME/include/linux" -shared -o libprojectbtlcpp.so SudokuGenerator.cpp SudokuSolver.cpp boardgenobj.cpp
java -Djava.library.path sudoku

https://www3.ntu.edu.sg/home/ehchua/programming/java/javanativeinterface.html
*/