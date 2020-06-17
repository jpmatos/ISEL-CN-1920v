package dao;

public class TranslateRequest {
    private final String id;
    private final OCRResult ocrResult;
    private final String language;

    public TranslateRequest(String id, OCRResult ocrResult, String language) {
        this.id = id;
        this.ocrResult = ocrResult;
        this.language = language;
    }

    public String getId() {
        return id;
    }

    public String getLanguage() {
        return language;
    }

    public OCRResult getOcrResult() {
        return ocrResult;
    }
}
