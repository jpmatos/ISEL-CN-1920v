package clientapp.utils;

public class CompletedUploads {
    private final String filename;
    private final String uploadToken;

    public CompletedUploads(String filename, String uploadToken) {
        this.filename = filename;
        this.uploadToken = uploadToken;
    }

    public String getFilename() {
        return filename;
    }

    public String getUploadToken() {
        return uploadToken;
    }
}
