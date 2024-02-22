package tile;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.text.FieldPosition;

public class TileManager {
    GamePanel gp;
    Tile[] tile;
    int[][] iiMapTileNumber;

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

        }catch(IOException e){
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

        if (gp.bBossFight){
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
        } else {
            int iMapCol = 0;
            int iMapRow = 0;

            while (iMapCol < gp.iMaxMapCol && iMapRow < gp.iMaxMapRow){
                int iTileNum = iiMapTileNumber[iMapCol][iMapRow];

                int iMapX = iMapCol * gp.iTileSize;
                int iMapY = iMapRow * gp.iTileSize;
                int iScreenX = iMapX - gp.player.iPlayerX + gp.player.iScreenX;
                int iScreenY = iMapX - gp.player.iPlayerY + gp.player.iScreenY;

                g2.drawImage(tile[iTileNum].image, iScreenX, iScreenY, gp.iTileSize, gp.iTileSize, null);
                iMapCol++;
                if (iMapCol == gp.iMaxMapCol){
                    iMapCol = 0;
                    iMapRow++;
                }
            }
        }

    }
}
