package models;

/**
 * Created with IntelliJ IDEA.
 * User: debian
 * Date: 5/19/12
 * Time: 11:39 AM
 */

public class Card {
    private String name;
    private String hairColor;
    private boolean hat;
    private boolean longHair;
    private boolean glasses;
    private boolean bold;
    private boolean beard;
    private boolean mustache;
    private Character sex;
    private boolean bigNose;

    public Card() {
    }

    public Card(String name, String hairColor, boolean hat, boolean longHair, boolean glasses, boolean bold, boolean beard, boolean mustache, Character sex, boolean bigNose) {
        this.name = name;
        this.hairColor = hairColor;
        this.hat = hat;
        this.longHair = longHair;
        this.glasses = glasses;
        this.bold = bold;
        this.beard = beard;
        this.mustache = mustache;
        this.sex = sex;
        this.bigNose = bigNose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public boolean isHat() {
        return hat;
    }

    public void setHat(boolean hat) {
        this.hat = hat;
    }

    public boolean isLongHair() {
        return longHair;
    }

    public void setLongHair(boolean longHair) {
        this.longHair = longHair;
    }

    public boolean isGlasses() {
        return glasses;
    }

    public void setGlasses(boolean glasses) {
        this.glasses = glasses;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isBeard() {
        return beard;
    }

    public void setBeard(boolean beard) {
        this.beard = beard;
    }

    public boolean isMustache() {
        return mustache;
    }

    public void setMustache(boolean mustache) {
        this.mustache = mustache;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public boolean isBigNose() {
        return bigNose;
    }

    public void setBigNose(boolean bigNose) {
        this.bigNose = bigNose;
    }

    @Override
    public String toString() {
        return "Card{" +
                "name='" + name + '\'' +
                ", hairColor='" + hairColor + '\'' +
                ", hat=" + hat +
                ", longHair=" + longHair +
                ", glasses=" + glasses +
                ", bold=" + bold +
                ", beard=" + beard +
                ", mustache=" + mustache +
                ", sex=" + sex +
                ", bigNose=" + bigNose +
                '}';
    }
}

