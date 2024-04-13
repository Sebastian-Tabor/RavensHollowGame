package world;

import main.GamePanel;
import main.UtilityTool;

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
        tile = new Tile[6];
        iiMapTileNumber = new int[gp.iMaxMapCol][gp.iMaxMapRow];
        getTileImage();
        loadMap();
    }
    public void getTileImage() {

            setup(0, "blank", false);
            setup(1, "cross", true);
            setup(2, "dirt", true);
            setup(3, "grass", true);
            setup(4, "grassleft", true);
            setup(5, "grassright", true);

    }
    public void setup(int index, String imageName, boolean collision) {
        UtilityTool uTool = new UtilityTool();

        try {
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(new File("./res/world/" + imageName + ".png"));
            tile[index].image = uTool.scaleImage(tile[index].image, gp.iTileSize, gp.iTileSize);
            tile[index].collision = collision;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadMap(){
        try{
            InputStream is = new FileInputStream("./res/maps/" + gp.iScene + "map.txt");
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
                int iScreenX = iMapX - gp.player.iWorldX + gp.player.iScreenPosX;
                int iScreenY = iMapY - gp.player.iWorldY + gp.player.iScreenPosY;

                if (iMapX + gp.iTileSize > gp.player.iWorldX - gp.player.iScreenPosX && iMapX - gp.iTileSize < gp.player.iWorldX + gp.player.iScreenPosX) {
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