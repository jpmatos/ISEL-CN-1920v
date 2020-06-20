package dao;

public class OCRRequest {
    private final String blobName; //It's also the token
    private final String language;

    public OCRRequest(String imageBlob, String language) {
        this.blobName = imageBlob;
        this.language = language;
    }

    public String getBlobName() {
        return blobName;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "OCRRequest{" +
                ", blobName='" + blobName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
