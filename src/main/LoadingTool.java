package main;

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
        gp.aSetter.resetMonsterPos();
        gp.aSetter.resetNPCPos();
        gp.aSetter.resetPlayerPos();
        try{
            InputStream is = new FileInputStream("./res/saves/saveState.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = br.readLine();
            String[] dataAt = line.split(" ");

            gp.iScene = Integer.parseInt(dataAt[0]);
            gp.iWins = Integer.parseInt(dataAt[1]);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    public void saveGame(){
        try{
            //BufferedWriter bw = new BufferedWriter(new Wr);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
