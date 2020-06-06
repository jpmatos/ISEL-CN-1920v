package clientapp.utils;

public class CompletedTranslations {
    private final String uploadToken;
    private final String translation;
    private final String language;

    public CompletedTranslations(String uploadToken, String translation, String language) {
        this.uploadToken = uploadToken;
        this.translation = translation;
        this.language = language;
    }

    public String getUploadToken() {
        return uploadToken;
    }

    public String getTranslation() {
        return translation;
    }

    public String getLanguage() {
        return language;
    }
}
