import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

    private int padddlePosX = 400;
    private int ballPosX = 500;
    private int ballPosY = 400;
    private int ballDirX = -1;
    private int ballDirY = -2;
    private boolean play = false;
    private boolean restart = false;
    private int brickRow = 5; // if u r chaning these change on line 222 also
    private int brickCol = 10; // if u r chaning these change on line 223 also
    private int score  = 0;
    private int totalBrick = this.brickRow*this.brickCol;
    private Timer timer;
    private int delay = 5;

    private BrickGen generate ;


    public Gameplay() {
        generate = new BrickGen(5, 10);
        addKeyListener(this);
        setFocusable(true);
        //setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();

    }
    

    public void paint(Graphics g) {
        // background of Gameplay Panel  
        g.setColor(Color.black);
        g.fillRect(2, 2, 900, 600);

        //Creating Bricks
        generate.draw((Graphics2D) g);

        // Paddle 
        g.setColor(Color.RED);
        g.fillRect(padddlePosX, 550, 60, 8);

        // Ball 
        g.setColor(Color.YELLOW);
        g.fillOval(ballPosX, ballPosY, 15, 15);

        //Borders so that paddle can't go out of screen 
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 3, 600);
        g.fillRect(0, 0, 900, 3);
        g.fillRect(900, 0, 3, 600);

        //Score 
        g.setColor(Color.ORANGE);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: "+this.score, 730, 30);
        
        //game start only when enter pressed 
        if( !this.play  && !this.restart){
            g.setColor(Color.MAGENTA);
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Start.", 350, 330);
        }

        //ball check 
        if(this.totalBrick <= 0){
            this.play = false;
            this.ballDirX = 0;
            this.ballDirY = 0;

            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("You Won !", 390, 300);

            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart.", 350, 330);
            g.drawString("Press Esc To Exit.", 370, 350);
            this.restart = true;
        }
        if(ballPosY > 560 ){
            this.play = false;
            this.ballDirX = 0;
            this.ballDirY = 0;

            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 35));
            g.drawString("Game Over, Score: "+this.score, 300, 300);
            
            g.setFont(new Font("serif", Font.BOLD, 25));
            g.drawString("Press Enter to Restart.", 350, 330);
            g.drawString("Press Esc To Exit.", 370, 370);
            this.restart = true;

        }

        g.dispose();
    }

    //move's paddle to right side
    private void moveRight() {
        this.padddlePosX += 10;
    }

    //move's paddle to left side 
    private void moveLeft() {
        this.padddlePosX -= 10;
    }

    //all the action are recorded in this function
    @Override
    public void actionPerformed(ActionEvent e){
        timer.start();

        //checks wheather ball on contact to paddle or not
        if(new Rectangle(ballPosX, ballPosY, 15, 15)
        .intersects(new Rectangle(padddlePosX, 550, 60, 8))) {
            this.ballDirY  = -this.ballDirY;
        }

        A :for(int i=0; i<generate.block.length; i++){
            for(int j = 0; j<generate.block[0].length; j++){
                if(generate.block[i][j] > 0){
                    int brickX = j*generate.blockwidth + 80;
                    int brickY = i*generate.blockheight + 50;
                    int brickWidth = generate.blockwidth;
                    int brickHeight = generate.blockheight;
                    
                    Rectangle brickRect = new Rectangle(brickX, 
                    brickY, brickWidth, brickHeight );

                    Rectangle ballRect = new Rectangle(ballPosX, ballPosY,
                    15, 15);

                    if(ballRect.intersects(brickRect)){
                        generate.setBrickValue(0, i, j);
                        this.totalBrick--;
                        this.score += 10;

                        switch (this.score) {
                            case 100:
                                this.delay--;
                                break;
                            case 200:
                                this.delay--;
                            case 300:
                                this.delay = 1;
                            default:
                                break;
                        }

                        if(ballPosX+19 <= brickRect.x || 
                        ballPosX+1 >= brickRect.x + brickRect.width){
                            ballDirX = -ballDirX;
                        } else {
                            ballDirY = -ballDirY;
                        }

                        break A;
                    }

                }
            } 
        }

        //condition so ball doesn't move outside the window
        if(this.play){
            this.ballPosX += this.ballDirX;
            this.ballPosY += this.ballDirY;
            
            if(this.ballPosX < 0){
                this.ballDirX = -this.ballDirX;
            }

            if(this.ballPosY < 0){
                this.ballDirY = -this.ballDirY;
            }

            if(this.ballPosX > 880){
                this.ballDirX = -this.ballDirX;
            }
        }


        repaint();
    }

    //records which key is pressed
    @Override
    public void keyPressed(KeyEvent e) {
        if(this.play){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                if(padddlePosX >= 840){
                    padddlePosX = 840;
                } else {
                    moveRight();
                }
            }
            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                if(padddlePosX <= 0){
                    padddlePosX = 0;
                } else {
                    moveLeft();
                }
            }
    }

        if( !this.play ) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER){
                this.play = true;
                this.ballPosX = 500;
                this.ballPosY = 400;
                this.ballDirX = -1;
                this.ballDirY = -2;
                this.brickRow = 5;
                this.brickCol = 10;
                this.totalBrick = this.brickRow*this.brickCol;
                this.delay = 5;
                this.score = 0;
                generate = new BrickGen(this.brickRow, this.brickCol);
            }
        }

        if( this.restart ){
            if(e.getKeyCode() ==  KeyEvent.VK_ESCAPE){
                System.exit(0);
            }
        }
        
    }


    @Override
    public void keyTyped(KeyEvent e){

    }

    
    @Override
    public void keyReleased(KeyEvent e){

    }
}
