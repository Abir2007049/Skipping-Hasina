package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
public class SkipHasina extends JPanel implements ActionListener,KeyListener {
    int boardWidth =750;
    int boardHeight = 450;

    Image studImg;
    Image bg;
    Image studJumpImg;
    Image studDeadImg;
    Image hasu1;
    Image hasu2;
    Image hasu3;
    Image hasu4;
    Image hasu5;
    Image hasu6;

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver)
        {
            placeHasuTimer.stop();
            gameLoop.stop();

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if( student.y==studY) {
                // System.out.println("Oh yeah!");
                velocityY = -17;

                placeHasuTimer.start();
            }


        }
        if(gameOver)
        {
           student.y=studY;
           student.img=studImg;
           velocityY=0;
            hasuArray.clear();
            score=0;
            gameOver=false;
            gameLoop.start();
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    class Block{
        int x,y,width,height;
        Image img;
        Block(int x,int y,int width,int height,Image img)
        {
            this.x=x;
            this.y=y;
            this.width=width;
            this.height=height;
            this.img=img;


        }

    }


    //student
    int studWidth=88,studHeight=100,studX=50;
    int studY=boardHeight - studHeight;
    Block student;


    //hasina
    int hasu1Width=70;
    int hasu2Width=90;
    int hasu3Width=90;
    int hasu4Width=90;
    int hasu5Width=90;
    int hasu6Width=90;
    int hasuHeight=80;
    int hasuX=700;
    int hasuY=boardHeight-hasuHeight;
    ArrayList<Block> hasuArray;






    //jump and move
    int velocityY=0;
    int velocityX=-10;
    int gravity=1;

    //gameover
    boolean gameOver=false;
    int score=0;


    //timer
    Timer gameLoop;
    Timer placeHasuTimer;


    public SkipHasina() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setBackground(Color.white);
        setFocusable(true);
        addKeyListener(this);
        studImg = loadImage("images/student.gif");

        studDeadImg = loadImage("images/student2.jpg");
        studJumpImg = loadImage("images/student.png");
        hasu1 = loadImage("images/hasu.jpg");
        hasu2 = loadImage("images/hasu2.jpg");
        hasu3 = loadImage("images/hasu.gif");
        hasu4 = loadImage("images/hasu4.gif");
        hasu5 = loadImage("images/hasu5.gif");
        hasu6 = loadImage("images/hasu6.gif");
        bg = loadImage("images/bg.png");

        //stud
        student = new Block(studX, studY, studWidth, studHeight, studImg);

        //hasu
        hasuArray = new ArrayList<Block>();
        //game Timer
        gameLoop = new Timer(1000 / 70, this);
        gameLoop.start();

        //hasu placement
        placeHasuTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeHasu();

            }
        });
        placeHasuTimer.start();
    }

    void placeHasu()
    {
        if(gameOver)
        {
            return;

        }
        double placeHasuChance=Math.random();
        if(placeHasuChance>0.65)
        {
            Block hasu=new Block(hasuX,hasuY,hasu3Width,hasuHeight,hasu3);
            hasuArray.add(hasu);

        }
        else if(placeHasuChance>.55)
        {
            Block hasu=new Block(hasuX,hasuY,hasu4Width,hasuHeight,hasu4);
            hasuArray.add(hasu);
        }
        else if(placeHasuChance>.45)
        {
            Block hasu=new Block(hasuX,hasuY,hasu5Width,hasuHeight,hasu5);
            hasuArray.add(hasu);
        }
        else if(placeHasuChance>.35)
        {
            Block hasu=new Block(hasuX,hasuY,hasu2Width,hasuHeight,hasu2);
            hasuArray.add(hasu);
        }
        else if(placeHasuChance>.25)
        {
            Block hasu=new Block(hasuX,hasuY,hasu6Width,hasuHeight,hasu6);
            hasuArray.add(hasu);
        }
        else if(placeHasuChance>.20)
        {
            Block hasu=new Block(hasuX,hasuY,hasu1Width,hasuHeight,hasu1);
            hasuArray.add(hasu);
        }

    }


    private Image loadImage(String path) {
        java.net.URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g)
    {
        g.drawImage(bg,0,0,boardWidth,boardHeight,null);
        g.drawImage(student.img,student.x,student.y,student.width,student.height,null);

        for(int i=0;i<hasuArray.size();i++)
        {
            Block hasu=hasuArray.get(i);
            g.drawImage(hasu.img,hasu.x,hasu.y,hasu.width,hasu.height,null);


        }
        //score
        g.setColor(Color.black);
        g.setFont(new Font("Courier",Font.PLAIN,32));
        if(gameOver)
        {
            g.drawString("Game Over:" + String.valueOf(score),10,35);


        }
        else
        {
            g.drawString(String.valueOf(score),10,35);
        }
    }
    public void move()
    {
        //stud
        velocityY+=gravity;
        student.y+=velocityY;
        if(student.y > studY)
        {
            student.y=studY;
            velocityY=0;

        }
        //hasu
        for(int i=0;i<hasuArray.size();i++)
        {
           Block hasu=hasuArray.get(i);

            hasu.x+=velocityX;
            if(collision(student,hasu))
            {   gameOver=true;

                student.img=studDeadImg;
            }
        }
        //score
        score++;


    }
    boolean collision(Block a,Block b)
    {
        return a.x<b.x + b.width &&
                a.x+a.width>b.x &&
                a.y<b.y+b.height &&
                a.y+a.height>b.y;

    }




}
