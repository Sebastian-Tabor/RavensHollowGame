public class Character {
    String sCharName;
    private int iMaxHealth;
    private int iCurrentHealth;
    private int iDamagePerHit;
    private int iMaxUltimate;
    private int iCurrentUltimate;
    private int iPerHitUltimate;
    private int iUltimateDamage;
    private int iCharSpeed;
    private int iCharPosX;
    private int iCharPosY;

    public Character(String sCharName, int iMaxHealth, int iCurrentHealth, int iDamagePerHit, int iMaxUltimate, int iCurrentUltimate, int iPerHitUltimate, int iUltimateDamage, int iCharSpeed, int iCharPosX, int iCharPosY) {
        this.sCharName = sCharName;
        this.iMaxHealth = iMaxHealth;
        this.iCurrentHealth = iCurrentHealth;
        this.iDamagePerHit = iDamagePerHit;
        this.iMaxUltimate = iMaxUltimate;
        this.iCurrentUltimate = iCurrentUltimate;
        this.iPerHitUltimate = iPerHitUltimate;
        this.iUltimateDamage = iUltimateDamage;
        this.iCharSpeed = iCharSpeed;
        this.iCharPosX = iCharPosX;
        this.iCharPosY = iCharPosY;
    }
    //Character stats
    KeyBinds keyBinds = new KeyBinds();
    public int getiMaxHealth() {return iMaxHealth;}
    public void setiMaxHealth(int iMaxHealth) {this.iMaxHealth = iMaxHealth;}
    public String getsCharName() {return sCharName;}
    public void setsCharName(String sCharName) {this.sCharName = sCharName;}
    public int getiCurrentHealth() {return iCurrentHealth;}
    public void setiCurrentHealth(int iCurrentHealth) {this.iCurrentHealth = iCurrentHealth;}
    public void damageCharacter(int ivDamage){
        setiCurrentHealth(getiCurrentHealth()-ivDamage);}
    public int getiDamagePerHit() {return iDamagePerHit;}
    public void setiDamagePerHit(int iDamagePerHit) {this.iDamagePerHit = iDamagePerHit;}
    public int getiMaxUltimate() {return iMaxUltimate;}
    public void setiMaxUltimate(int iMaxUltimate) {this.iMaxUltimate = iMaxUltimate;}
    public int getiCurrentUltimate() {return iCurrentUltimate;}
    public void setiCurrentUltimate(int iCurrentUltimate) {this.iCurrentUltimate = iCurrentUltimate;}
    public void resetUltimate(){setiCurrentUltimate(0);}
    public int getiPerHitUltimate() {return iPerHitUltimate;}
    public void setiPerHitUltimate(int iPerHitUltimate) {this.iPerHitUltimate = iPerHitUltimate;}
    public int getiUltimateDamage() {return iUltimateDamage;}
    public void setiUltimateDamage(int iUltimateDamage) {this.iUltimateDamage = iUltimateDamage;}
    public int getiCharSpeed() {return iCharSpeed;}
    public void setiCharSpeed(int iCharSpeed) {this.iCharSpeed = iCharSpeed;}
    public int getiCharPosX() {return iCharPosX;}
    public void setiCharPosX(int iCharPosX) {this.iCharPosX = iCharPosX;}
    public int getiCharPosY() {return iCharPosY;}
    public void setiCharPosY(int iCharPosY) {this.iCharPosY = iCharPosY;}
    //Player pos vars

    public void characterMovement(Character character){
        int iOldCharPosY = iCharPosY;
        if (keyBinds.bLeftPressed) {iCharPosX -= character.getiCharSpeed();}
        if (keyBinds.bRightPressed) {iCharPosX += character.getiCharSpeed();}
    //if (keyBinds.bDownPressed == true) {iPlayerPosX += Player.getIvCharacterSpeed();}
        if (keyBinds.bUpPressed) {iCharPosY -= 2*character.getiCharSpeed();}
        if (keyBinds.bSpacePressed) {iCharPosY -= character.getiCharSpeed();}
        if (iCharPosY > 540){iCharPosY = 100;}
        if (iCharPosY-iOldCharPosY >= 6*character.getiCharSpeed()){iCharPosY += character.getiCharSpeed();}
        if (iCharPosY < 540){iCharPosY += character.getiCharSpeed();}
    }
}
