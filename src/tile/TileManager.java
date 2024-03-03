package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] iiMapTileNumber;

    public TileManager(GamePanel gp){
        this.gp = gp;
        // Change this for more than 2 tiles saved
        tile = new Tile[2];
        iiMapTileNumber = new int[gp.iMaxMapCol][gp.iMaxMapRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {
        try {
            tile[0] = new Tile();
            tile[0].image = ImageIO.read(new File("./res/tiles/rocket.png"));

            tile[1] = new Tile();
            tile[1].image = ImageIO.read(new File("./res/tiles/gabe.png"));
            tile[1].collision = true;

        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public void loadMap(){
        try{
            InputStream is = new FileInputStream("./res/maps/tempmap.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.iMaxMapCol && row < gp.iMaxMapRow) {
                String line = br.readLine();
                while (col < gp.iMaxMapCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    iiMapTileNumber[col][row] = num;
                    col++;
                }
                if (col == gp.iMaxMapCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void draw(Graphics2D g2){

            int iMapCol = 0;
            int iMapRow = 0;

            while (iMapCol < gp.iMaxMapCol && iMapRow < gp.iMaxMapRow) {
                int iTileNum = iiMapTileNumber[iMapCol][iMapRow];

                int iMapX = iMapCol * gp.iTileSize;
                int iMapY = iMapRow * gp.iTileSize;
                int iScreenX = iMapX - gp.player.iWorldX + gp.player.iScreenX;
                int iScreenY = iMapY - gp.player.iWorldY + gp.player.iScreenY;

                if (iMapX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenX && iMapX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenX) {
                    g2.drawImage(tile[iTileNum].image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
                }

                iMapCol++;
                if (iMapCol == gp.iMaxMapCol) {
                    iMapCol = 0;
                    iMapRow++;
                }
            }
    }
}