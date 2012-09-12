package models;

/**
 * Created by Mart0
 * Date: 9/6/12
 */
public class CurrentQuestion {
    String about;
    String value;
    String text;
    private Card card;

    public CurrentQuestion(String about, String value, String text, Card card) {
        this.about = about;
        this.value = value;
        this.text = text;
        this.card = card;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRightAnswer() {
        if (about.equalsIgnoreCase("sex")) {
            String cardSex = card.getSex().toString();
            return cardSex.equalsIgnoreCase("M") ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("hair")) {
            return card.getHairColor().equalsIgnoreCase(value) ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("glasses")) {
            return card.isGlasses() ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("hat")) {
            return card.isHat() ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("mustache")) {
            return card.isMustache() ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("beard")) {
            return card.isBeard() ? "YES" : "NO";

        } else if (about.equalsIgnoreCase("nose")) {
            return card.isBigNose() ? "YES" : "NO";

        } else {
            System.out.println("Error: cannot get the right answer.");
            System.out.println("about = " + about);
            System.out.println("value = " + value);
            return "YES";
        }
    }
}
