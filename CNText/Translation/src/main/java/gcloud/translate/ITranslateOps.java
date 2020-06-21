package gcloud.translate;

public interface ITranslateOps {
    String getTextTranslated(String textToTranslate,String locale, String language);
    String getError();
}
