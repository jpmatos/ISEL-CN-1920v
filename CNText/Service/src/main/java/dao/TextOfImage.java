package dao;

public class TextOfImage {
    public String language;
    public String ocrResult;
    public String translationResult;

    public TextOfImage() {
    }

    @Override
    public String toString() {
        return "{" +
                "language='" + language + '\'' +
                ", ocrResult='" + ocrResult + '\'' +
                ", translationResult='" + translationResult + '\'' +
                '}';
    }
}
