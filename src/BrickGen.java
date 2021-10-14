import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
public class BrickGen {
    //Generates Brick logically
    public int[][] block;
    public int blockwidth;
    public int blockheight;

    public BrickGen(int row, int col) {
        block = new int[row][col];
        for(int i=0; i<block.length; i++){
            for(int j = 0; j<block[0].length; j++){
                block[i][j] = 1;
            }
        }

        blockwidth = 750/col;
        blockheight = 200/row;

    }

    public void draw(Graphics2D g) {
        for(int i=0; i<block.length; i++){
            for(int j =0; j<block[0].length; j++){
                if(block[i][j] > 0){
                    g.setColor(Color.CYAN);
                    g.fillRect(j * blockwidth+80, i*blockheight+50, blockwidth, blockheight);

                    g.setStroke(new BasicStroke(3));
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(j * blockwidth+80, i*blockheight+50, blockwidth, blockheight);
                }
            }
        }
    }

    public void setBrickValue(int value, int row, int col){
        block[row][col] = value;
    }

}
