package main;

import entity.SuperEntity;

import java.io.*;

public class LoadingTool {
    GamePanel gp;

    public LoadingTool(GamePanel gp) {
        this.gp = gp;
    }
    public void newGame(){

        gp.aSetter.resetMonsterPos();
        gp.aSetter.resetNPCPos();
        gp.aSetter.resetPlayerPos();

        try{
            InputStream is = new FileInputStream("./res/saves/newGameState.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            String[] dataAt = line.split(" ");

            gp.iScene = Integer.parseInt(dataAt[0]);
            gp.iWins = Integer.parseInt(dataAt[1]);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void loadGame(){
        try{
            InputStream is = new FileInputStream("./res/saves/saveState.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String value = br.readLine();
            String[] dataAt = value.split(" ");
            gp.iMaxLines = Integer.parseInt(dataAt[0]);
            for (int line = 0; line < gp.iMaxLines; line++) {
                //LOAD PLAYER
                if (line == 1) {
                    value = br.readLine();
                    dataAt = value.split(" ");
                    gp.iScene = Integer.parseInt(dataAt[0]);
                    gp.iWins = Integer.parseInt(dataAt[1]);
                    gp.player.iWorldX = Integer.parseInt(dataAt[2]);
                    gp.player.iWorldY = Integer.parseInt(dataAt[3]);
                } else if (line > 1){
                    if (gp.monster[line - 2] != null) {
                        gp.monster[line - 2].iWorldX = Integer.parseInt(dataAt[2]);
                        gp.monster[line - 2].iWorldY = Integer.parseInt(dataAt[3]);
                    }
                }
            }
            br.close();


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void saveGame(){
        try{
            FileWriter file = new FileWriter("./res/saves/saveState.txt");
            BufferedWriter bw = new BufferedWriter(file);
            bw.write(Integer.toString(gp.iMaxLines));
            //PLAYER SAVE DATA
            gp.saveData[0] = gp.iScene;
            gp.saveData[1] = gp.iWins;
            gp.saveData[2] = gp.player.iWorldX;
            gp.saveData[3] = gp.player.iWorldY;
            bw.newLine();
            for (int value : gp.saveData) {
                bw.write(Integer.toString(value));
                bw.write(" ");
            }
            //MONSTER SAVE DATA
            gp.monsterSaveData[0] = 0;
            gp.monsterSaveData[1] = 0;
            for (int i = 0; i < gp.monster.length; i++) {
                bw.newLine();
                if (gp.monster[i] != null) {
                    gp.monsterSaveData[2] = gp.monster[i].iWorldX;
                    gp.monsterSaveData[3] = gp.monster[i].iWorldY;
                    for (int value : gp.monsterSaveData) {
                        bw.write(Integer.toString(value));
                        bw.write(" ");
                    }
                } else {
                    bw.write(Integer.toString(0));
                }
            }
            bw.close();
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
