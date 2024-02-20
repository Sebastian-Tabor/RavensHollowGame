package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] iiMapTileNumber;

    public TileManager(GamePanel gp){
        this.gp = gp;
        // Change this for more than 20 tiles saved
        tile = new Tile[2];
        iiMapTileNumber = new int[gp.iMaxScreenColumns][gp.iMaxScreenRows];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/rocket.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/gabe.png"));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try{
            InputStream is = getClass().getResourceAsStream("/maps/tempmap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.iMaxScreenColumns && row < gp.iMaxScreenRows) {
                String line = br.readLine();
                while (col < gp.iMaxScreenColumns) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    iiMapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gp.iMaxScreenColumns) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e){}

    }
    public void draw(Graphics2D g2){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;
         while (col < gp.iMaxScreenColumns && row < gp.iMaxScreenRows){
             int iTileNum = iiMapTileNumber[col][row];

             g2.drawImage(tile[iTileNum].image, x, y, gp.iTileSize, gp.iTileSize, null);
             col++;
             x += gp.iTileSize;
             if (col == gp.iMaxScreenColumns){
                 col = 0;
                 x = 0;
                 row++;
                 y += gp.iTileSize;
             }
         }

    }
}
