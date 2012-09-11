package models;

/**
 * Created by Mart0
 * Date: 9/6/12
 */
public class Question {
    String about;
    String value;
    String text;
    private Card card;

    public Question(String about, String value, String text, Card card) {
        this.about = about;
        this.value = value;
        this.text = text;
        this.card = card;
        System.out.println("New Question:" + " about = " + about + " - value = " + value);
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
        String name = card.getNamee();

        // todo ACA VA LA LOGICA DE CUANDO MIENTEN, FALTA UNIFICAR LAS PREGUNTAS
        // todo PARA EL SPRINT 3!!!!!


        return "YES";
    }
}
