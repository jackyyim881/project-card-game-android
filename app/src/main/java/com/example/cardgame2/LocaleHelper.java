package com.example.cardgame2;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class LocaleHelper {
    public static void setLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());

        // Persist the language preference
        SharedPreferences.Editor editor =
            context.getSharedPreferences("Settings" , MODE_PRIVATE).edit();
        editor.putString("language", languageCode);
        editor.apply();
    }

    public static Context wrapContext(Context context) {
        SharedPreferences prefs =
            context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = prefs.getString("language", "en");
        return updateBaseContextLocale(context, language);
    }

    private static Context updateBaseContextLocale(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        return context.createConfigurationContext(configuration);
    }
}