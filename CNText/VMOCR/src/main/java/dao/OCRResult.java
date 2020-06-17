package dao;

public class OCRResult {

    private final String result;
    private final String locale;

    public OCRResult(String result, String locale) {
        this.result = result;
        this.locale = locale;
    }
    public String getResult() {
        return result;
    }

    public String getLocale() {
        return locale;
    }
}
