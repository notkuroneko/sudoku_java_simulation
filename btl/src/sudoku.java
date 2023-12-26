import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException; 
import java.util.Scanner;

public class sudoku{
    private JFrame f; 
    private JButton b1,b2,b3,b4,b5,b6,b7,b8;
    private JScrollPane s1,s2;
    private Font presetNum = new Font(Font.DIALOG, Font.BOLD, 24);
    private Font filledNum = new Font(Font.DIALOG, Font.PLAIN, 24);
    private Font big = new Font(Font.DIALOG, Font.BOLD, 18);
    private Font medium = new Font(Font.DIALOG, Font.BOLD, 15);
    private class SudokuBox extends JButton {
        private boolean isChosen = false;
        private boolean canChoose = true;
        private int value = 0;
        private int answer;
        public void setPresetSquare(int x) {
            this.canChoose = false;
            this.setFont(presetNum);
            this.value=x;
            this.setText(String.valueOf(x));
            this.setFocusable(false);
        }
        public boolean getChosen() {
            return this.isChosen;
        }
        public void setChosen(boolean x) {
            this.isChosen=x;
        }
        public void setValue(int x){
            this.value=x;
        }
        public void setAnswer(int x){
            this.answer=x;
        }
    };
    private class Box extends JComponent{
        public void paint(Graphics g){
            setBackground(Color.white);  
            Graphics2D g1 = (Graphics2D) g; 
            g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);          
            for (int i=1; i<=8; i++) g.drawLine(100+60*i,70,100+60*i,610);
            for (int i=1; i<=8; i++) g.drawLine(100,70+60*i,640,70+60*i);
            g1.setStroke(new BasicStroke(3));
            g.drawRect(100,70,540,540);
            g.drawLine(280,70,280,610);
            g.drawLine(460,70,460,610);
            g.drawLine(100,250,640,250);
            g.drawLine(100,430,640,430);
        }
    };
    private SudokuBox board[][];
    private class InputListener extends KeyAdapter {}
    private int previ=0,prevj=0;

    sudoku(){
        f = new JFrame("Sudoku"); //init game window
        f.setBackground(Color.white);
        f.setBounds(100,100,960,720);
        f.setLayout(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        b1 = new JButton("Start"); //init start game button
        b1.setBounds(400,240,160,80);
        b1.setVisible(true);
        b1.setFont(big);
        b2 = new JButton("Load"); //init load game button
        b2.setBounds(400,340,160,80);
        b2.setVisible(true);
        b2.setFont(big);
        b3 = new JButton("History"); //init show game history button
        b3.setBounds(400,440,160,80);
        b3.setVisible(true);
        b3.setFont(big);
        b4 = new JButton("Exit"); //init exit game button
        b4.setBounds(400,540,160,80);
        b4.setVisible(true);
        b4.setFont(big);
        b5 = new JButton("Quit"); //init midgame exit button
        b5.setBounds(700,400,160,80);
        b5.setVisible(false);
        b5.setFont(big);
        b8 = new JButton("Save"); //init midgame save button
        b8.setBounds(700,300,160,80);
        b8.setVisible(false);
        b8.setFont(big);
        b6 = new JButton("Delete History"); //init delete game history button
        b6.setBounds(400,450,160,80);
        b6.setVisible(false);
        b6.setFont(medium);
        b7 = new JButton("Back"); //init return to main menu button
        b7.setBounds(400,550,160,80);
        b7.setVisible(false);
        b7.setFont(big);
        Box b = new Box(); //init game board
        b.setSize(960,720);
        b.setVisible(false);
        s1 = new JScrollPane(); //init unfinished game pane
        s1.setBounds(300,150,360,250);
        s1.setVisible(false);
        s2 = new JScrollPane(); //init game history pane
        s2.setBounds(300,150,360,250);
        s2.setVisible(false);
        board = new SudokuBox[10][10]; //init board squares
        for (int i=1; i<=9; i++) {
        for (int j=1; j<=9; j++)
        {
            board[i][j] = new SudokuBox();
            board[i][j].setBackground(Color.white);
            board[i][j].setBounds(102+60*(i-1),72+60*(j-1),56,56);
            board[i][j].setBorderPainted(false);
            board[i][j].setVisible(false);
            board[i][j].setFont(filledNum);
            f.add(board[i][j]);
        } }
        board[0][0] = new SudokuBox();
        board[0][0].setChosen(false);
        b1.addActionListener(new ActionListener() { //start game button, generates board and stack
            public void actionPerformed(ActionEvent e){
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);
                b4.setVisible(false);
                b.setVisible(true);   
                b5.setVisible(true);  
                b8.setVisible(true);
                for (int i=1; i<=9; i++) {
                for (int j=1; j<=9; j++) {
                    board[i][j].setVisible(true);
                    board[i][j].setFocusable(true);
                    board[i][j].setValue(0);
                    board[i][j].setText(" ");
                    board[i][j].setChosen(false);
                    board[i][j].setBackground(Color.white);
                } }     
                new sudokuboardgen1().boardgen(); //boardgen
                try {
                    File myObj = new File("sudokuboardgentest.txt");
                    Scanner myReader = new Scanner(myObj);
                    for (int i=1; i<=9; i++) { 
                    for (int j=1; j<=9; j++) {
                        String dataStr = myReader.nextLine();
                        int dataInt = Integer.parseInt(dataStr);
                        if (dataInt!=0) board[j][i].setPresetSquare(dataInt); 
                    } }  
                    for (int i=1; i<=9; i++) { 
                    for (int j=1; j<=9; j++) {
                        board[j][i].setAnswer(Integer.parseInt(myReader.nextLine()));
                    } }
                    myReader.close();
                  } 
                catch (FileNotFoundException ee) {
                    System.out.println("An error occurred.");
                    ee.printStackTrace();
                  } //scan file for boardgen output (riddle and answer)
                new sudokuboardgen1().fileDelete();
                for (int i=1; i<=9; i++) { 
                for (int j=1; j<=9; j++) {
                    if (board[i][j].canChoose) {
                        final int tempi = i;
                        final int tempj = j;
                        board[i][j].addActionListener(new ActionListener() {    
                            public void actionPerformed(ActionEvent e){
                                board[tempi][tempj].setChosen(!board[tempi][tempj].getChosen());
                                if (board[tempi][tempj].getChosen()) {
                                    board[tempi][tempj].setBackground(Color.LIGHT_GRAY);
                                    if (board[previ][prevj].getChosen()) {
                                        board[previ][prevj].setChosen(false);
                                        board[previ][prevj].setBackground(Color.white);
                                    }
                                    board[tempi][tempj].addKeyListener(new InputListener(){
                                        public void keyPressed(KeyEvent e) {
                                            if (e.getKeyChar()=='1') {board[tempi][tempj].setValue(1); board[tempi][tempj].setText("1");}
                                            if (e.getKeyChar()=='2') {board[tempi][tempj].setValue(2); board[tempi][tempj].setText("2");}
                                            if (e.getKeyChar()=='3') {board[tempi][tempj].setValue(3); board[tempi][tempj].setText("3");}
                                            if (e.getKeyChar()=='4') {board[tempi][tempj].setValue(4); board[tempi][tempj].setText("4");}
                                            if (e.getKeyChar()=='5') {board[tempi][tempj].setValue(5); board[tempi][tempj].setText("5");}
                                            if (e.getKeyChar()=='6') {board[tempi][tempj].setValue(6); board[tempi][tempj].setText("6");}
                                            if (e.getKeyChar()=='7') {board[tempi][tempj].setValue(7); board[tempi][tempj].setText("7");}
                                            if (e.getKeyChar()=='8') {board[tempi][tempj].setValue(8); board[tempi][tempj].setText("8");}
                                            if (e.getKeyChar()=='9') {board[tempi][tempj].setValue(9); board[tempi][tempj].setText("9");}
                                            if (e.getKeyCode()==KeyEvent.VK_BACK_SPACE) {board[tempi][tempj].setValue(0); board[tempi][tempj].setText("");}
                                        }
                                    });
                                }
                                else {board[tempi][tempj].setBackground(Color.white);}
                                previ=tempi;
                                prevj=tempj;
                            }
                    });
                }
                }
                }
            }
        });
        b2.addActionListener(new ActionListener() { //load game button
            public void actionPerformed(ActionEvent e) {
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);
                b4.setVisible(false);
                s1.setVisible(true);
                b7.setVisible(true);
            }
        });
        b3.addActionListener(new ActionListener() { //show game history button
            public void actionPerformed(ActionEvent e) {
                b1.setVisible(false);
                b2.setVisible(false);
                b3.setVisible(false);
                b4.setVisible(false);
                s2.setVisible(true);
                b6.setVisible(true);
                b7.setVisible(true);
            }
        });
        b4.addActionListener(new ActionListener() { //exit button
            public void actionPerformed(ActionEvent e) {
                f.dispose();
            }           
        });
        b5.addActionListener(new ActionListener() { //save and quit button during the game
            public void actionPerformed(ActionEvent e) {
                b1.setVisible(true);
                b2.setVisible(true);
                b3.setVisible(true);
                b4.setVisible(true);
                b.setVisible(false);
                b5.setVisible(false);
                b8.setVisible(false);
                for (int i=1; i<=9; i++) {
                for (int j=1; j<=9; j++) {
                    board[i][j].setVisible(false);
                } }      
            }
        });
        b6.addActionListener(new ActionListener() { //delete history button
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        b7.addActionListener(new ActionListener() { //back to main menu button
            public void actionPerformed(ActionEvent e){
                b1.setVisible(true);
                b2.setVisible(true);
                b3.setVisible(true);
                b4.setVisible(true);
                s1.setVisible(false);
                s2.setVisible(false);
                b6.setVisible(false);
                b7.setVisible(false);
            }
        });
        b8.addActionListener(new ActionListener() { //save game button
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        f.add(b1); //add all componenents into the frame
        f.add(b2);
        f.add(b3);
        f.add(b4);
        f.add(b);
        f.add(b5);
        f.add(b6);
        f.add(b7);
        f.add(b8);
        f.add(s1);
        f.add(s2);
        f.setVisible(true);
    }

    public static void main(String args[]) throws Exception{
        new sudoku();
        //System.out.println(System.getProperty("java.library.path"));
    }  
}

/*
Run command: 
cd /home/jackie/Documents/vscode/java/btl/src
java -Djava.library.path=/home/jackie/Documents/vscode/java/btl/src sudoku
 */
