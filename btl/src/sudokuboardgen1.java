public class sudokuboardgen1 {
    static {
        System.loadLibrary("projectbtlcpp");
    }
    native void boardgen();
    native void fileDelete();

    //javac -h . sudokuboardgen1.java
}
