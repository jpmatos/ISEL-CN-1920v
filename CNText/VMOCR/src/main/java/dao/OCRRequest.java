package dao;

public class OCRRequest {
    private final String sessionID;
        private final String blobName;
    private final String language;

    public OCRRequest(String sessionID, String imageBlob, String language) {
        this.sessionID = sessionID;
        this.blobName = imageBlob;
        this.language = language;
    }

    public String getBlobName() {
        return blobName;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
        return "OCRRequest{" +
                "sessionID='" + sessionID + '\'' +
                ", imageBlob='" + blobName + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}
