package bank_system.controller;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocalizationManager {
    @SuppressWarnings("deprecation")
    private static Locale currentLocale = new Locale("en", "US");
    private static ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", Locale.of("en", "US"));


    public static ResourceBundle getBundle() {
        return bundle;
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    public static void setLocale(Locale newLocale) {
        currentLocale = newLocale;
        bundle = ResourceBundle.getBundle("MessagesBundle", currentLocale);
    }
}

