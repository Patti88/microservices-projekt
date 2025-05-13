package se.iths.jokeservice.model;

public class Joke {
    private String text;

    public Joke(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}