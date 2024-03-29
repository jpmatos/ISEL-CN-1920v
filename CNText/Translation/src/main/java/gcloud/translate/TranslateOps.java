package gcloud.translate;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import static utils.Output.OutputType.ERROR;
import static utils.Output.log;

public class TranslateOps implements ITranslateOps {
    public String errorMessage=null;

    @Override
    public String getTextTranslated(String texToTranslate, String locale, String language) {

        try {
            return translateText(texToTranslate, locale, language);
        } catch (Exception e) {
            errorMessage=e.getMessage();
            log(ERROR, errorMessage);

            return null;
        }
    }

    // Translating text, is not possible translate text in the same language
    public static String translateText(String text, String locale, String targetLanguage) {

        // Instantiates a client
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        //Translating text to the target language identified (targetlanguage)
        Translation translation =
                translate.translate(
                        text, Translate.TranslateOption.sourceLanguage(locale), Translate.TranslateOption.targetLanguage(targetLanguage));

        return translation.getTranslatedText();
    }
    public String getError(){
        return errorMessage;
    }

}
