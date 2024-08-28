package main;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        int boardWidth=750;
        int boardHeight=350;

        JFrame frame=new JFrame("SkipHasina"); 
        frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        //frame.setLocation(null);
         frame.setLocationRelativeTo(null);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        SkipHasina skiphasina=new SkipHasina();
        frame.add(skiphasina);
        frame.pack();
        skiphasina.requestFocus();
        frame.setVisible(true);


    }
}