package serviceapp.dao;

public class TextOfImage {
    public String language;
    public String text;
    public String translationResult;

    public TextOfImage() {
    }

    @Override
    public String toString() {
        return "{" +
                "language='" + language + '\'' +
                ", text='" + text + '\'' +
                ", translationResult='" + translationResult + '\'' +
                '}';
    }
}
