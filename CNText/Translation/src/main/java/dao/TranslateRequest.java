package dao;

public class TranslateRequest {

    private final String id;
    private final String textToTranslate;
    private final String language;
    private final String locale;

    public TranslateRequest(String id, String textToTranslate, String language,String locale) {
        this.id = id;
        this.textToTranslate = textToTranslate;
        this.language = language;
        this.locale = locale;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public String getTextToTranslate() { return textToTranslate; }

    public String getLocale() { return locale; }
}
