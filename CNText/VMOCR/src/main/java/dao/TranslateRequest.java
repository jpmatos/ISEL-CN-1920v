package dao;

public class TranslateRequest {
    private final String id;
    private final String textToTranslate;
    private final String language;

    public TranslateRequest(String id, String textToTranslate, String language) {
        this.id = id;
        this.textToTranslate = textToTranslate;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getTextToTranslate() {
        return textToTranslate;
    }

    public String getLanguage() {
        return language;
    }
}
