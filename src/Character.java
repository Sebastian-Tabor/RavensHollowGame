public class Character {
    String sCharName;
    private int ivMaxHealth;
    private int ivCurrentHealth;
    private int ivDamagePerHit;
    private int ivMaxUltimate;
    private int ivCurrentUltimate;
    private int ivPerHitUltimate;
    private int ivUltimateDamage;
    private int ivCharacterSpeed;
    public Character(String sCharName, int ivMaxHealth, int ivCurrentHealth, int ivDamagePerHit, int ivMaxUltimate, int ivCurrentUltimate, int ivPerHitUltimate, int ivUltimateDamage, int ivCharacterSpeed) {
        this.sCharName = sCharName;
        this.ivMaxHealth = ivMaxHealth;
        this.ivCurrentHealth = ivCurrentHealth;
        this.ivDamagePerHit = ivDamagePerHit;
        this.ivMaxUltimate = ivMaxUltimate;
        this.ivCurrentUltimate = ivCurrentUltimate;
        this.ivPerHitUltimate = ivPerHitUltimate;
        this.ivUltimateDamage = ivUltimateDamage;
        this.ivCharacterSpeed = ivCharacterSpeed;
    }
    public int getIvMaxHealth() {return ivMaxHealth;}
    public void setIvMaxHealth(int ivMaxHealth) {this.ivMaxHealth = ivMaxHealth;}
    public String getsCharName() {return sCharName;}
    public void setsCharName(String sCharName) {this.sCharName = sCharName;}
    public int getIvCurrentHealth() {return ivCurrentHealth;}
    public void setIvCurrentHealth(int ivCurrentHealth) {this.ivCurrentHealth = ivCurrentHealth;}
    public void damageCharacter(int ivDamage){setIvCurrentHealth(getIvCurrentHealth()-ivDamage);}
    public int getIvDamagePerHit() {return ivDamagePerHit;}
    public void setIvDamagePerHit(int ivDamagePerHit) {this.ivDamagePerHit = ivDamagePerHit;}
    public int getIvMaxUltimate() {return ivMaxUltimate;}
    public void setIvMaxUltimate(int ivMaxUltimate) {this.ivMaxUltimate = ivMaxUltimate;}
    public int getIvCurrentUltimate() {return ivCurrentUltimate;}
    public void setIvCurrentUltimate(int ivCurrentUltimate) {this.ivCurrentUltimate = ivCurrentUltimate;}
    public void resetUltimate(){setIvCurrentUltimate(0);}
    public int getIvPerHitUltimate() {return ivPerHitUltimate;}
    public void setIvPerHitUltimate(int ivPerHitUltimate) {this.ivPerHitUltimate = ivPerHitUltimate;}
    public int getIvUltimateDamage() {return ivUltimateDamage;}
    public void setIvUltimateDamage(int ivUltimateDamage) {this.ivUltimateDamage = ivUltimateDamage;}
    public int getIvCharacterSpeed() {return ivCharacterSpeed;}
    public void setIvCharacterSpeed(int ivCharacterSpeed) {this.ivCharacterSpeed = ivCharacterSpeed;}



}
