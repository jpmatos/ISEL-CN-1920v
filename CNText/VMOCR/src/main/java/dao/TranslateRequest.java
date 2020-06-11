package dao;

public class TranslateRequest {
    private final String token;
    private final String textToTranslate;
    private final String language;

    public TranslateRequest(String token, String textToTranslate, String language) {
        this.token = token;
        this.textToTranslate = textToTranslate;
        this.language = language;
    }

    public String getToken() {
        return token;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public String getLanguage() {
        return language;
    }
}
