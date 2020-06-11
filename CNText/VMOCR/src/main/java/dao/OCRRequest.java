package dao;

public class OCRRequest {
    private final String sessionID;
    private final String token;
    private final String blobName;
    private final String language;

    public OCRRequest(String sessionID, String token, String imageBlob, String language) {
        this.sessionID = sessionID;
        this.token = token;
        this.blobName = imageBlob;
        this.language = language;
    }

    public String getBlobName() {
        return blobName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getToken() {
        return token;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "OCRRequest{" +
                "sessionID='" + sessionID + '\'' +
                ", token='" + token + '\'' +
                ", imageBlob='" + blobName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
