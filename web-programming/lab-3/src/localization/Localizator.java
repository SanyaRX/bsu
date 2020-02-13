package localization;

import java.util.Locale;
import java.util.ResourceBundle;

public class Localizator {
    public static final String DEFAULT_LANGUAGE = "ru";
    private static Locale locale = new Locale(DEFAULT_LANGUAGE);

    private static final String BUNDLE_PATH = "localization/locales/locale";
    private static ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);

    public static String TEXT_LOADED = "TextLoaded";
    public static String SORTING = "Sorting";
    public static String WORDS_PROCESSING = "WordsProcessing";
    public static String TEXT_SORTED = "TextSorted";
    public static String TEXT_PARSING = "TextParsing";
    public static String TEXT_PARSED = "TextParsed";

    public static Locale getDefaultLocale(){
        return new Locale(DEFAULT_LANGUAGE);
    }

    public static Locale getCurrentLocale(){
        return locale;
    }

    /**
     * set new locale
     * @param localeStr new loacle string
     */
    public static void setLocale( String localeStr ){
        locale = new Locale(localeStr);
        bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
    }

    /**
     * get localized string
     * @param string localized string key
     * @return localized string
     */
    public static String getLocalizedString( String string ){
        return bundle.getString( string );
    }

}
