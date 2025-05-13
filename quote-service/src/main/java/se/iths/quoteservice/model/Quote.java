package se.iths.quoteservice.model;

public class Quote {
    private String text;
    private String author;

    public Quote(String text, String author) {
        this.text = text;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public String getAuthor() {
        return author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}