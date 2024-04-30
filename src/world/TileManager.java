package world;

import main.GamePanel;
import main.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;

public class TileManager {
    GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileCoordinates;

    public TileManager(GamePanel gp){
        this.gp = gp;
        // Change this for more than 2 tiles saved
        tile = new Tile[6];
        mapTileCoordinates = new int[gp.maxMapCol][gp.maxMapRow];
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
            tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadMap(){
        try{
            InputStream is = new FileInputStream("./res/maps/" + gp.sceneNumber + "map.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            int col = 0;
            int row = 0;

            while (col < gp.maxMapCol && row < gp.maxMapRow) {
                String line = br.readLine();
                while (col < gp.maxMapCol) {
                    String[] numbers = line.split(" ");
                    int num = Integer.parseInt(numbers[col]);
                    mapTileCoordinates[col][row] = num;
                    col++;
                }
                if (col == gp.maxMapCol) {
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

            while (iMapCol < gp.maxMapCol && iMapRow < gp.maxMapRow) {
                int iTileNum = mapTileCoordinates[iMapCol][iMapRow];

                int iMapX = iMapCol * gp.tileSize;
                int iMapY = iMapRow * gp.tileSize;
                int iScreenX = iMapX - gp.player.worldX + gp.player.screenPosX;
                int iScreenY = iMapY - gp.player.worldY + gp.player.screenPosY;

                if (iMapX + gp.tileSize > gp.player.worldX - gp.player.screenPosX && iMapX - gp.tileSize < gp.player.worldX + gp.player.screenPosX) {
                    g2.drawImage(tile[iTileNum].image, iScreenX, iScreenY, gp.tileSize, gp.tileSize, null);
                }

                iMapCol++;
                if (iMapCol == gp.maxMapCol) {
                    iMapCol = 0;
                    iMapRow++;
                }
            }
    }
}