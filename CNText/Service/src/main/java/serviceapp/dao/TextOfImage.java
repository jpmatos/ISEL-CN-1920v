package serviceapp.dao;

public class TextOfImage {
    public String language;
    public String text;
    public String translationResult;
    public String error;

    public TextOfImage() {
    }

    @Override
    public String toString() {
        return "TextOfImage{" +
                "language='" + language + '\'' +
                ", text='" + text + '\'' +
                ", translationResult='" + translationResult + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
