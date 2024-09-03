package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.ToggleButton;

import java.util.Locale;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayAdapter<String> arrayAdapter;
    private boolean isUserAction = false;

    ToggleButton toggleButton;
    ToggleButton toggleButton_click;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        String[] languages = new String[]{getString(R.string.select_language), "English \uD83C\uDDEC\uD83C\uDDE7", "Deutsch \uD83C\uDDE9\uD83C\uDDEA",
                "\uD83C\uDDEA\uD83C\uDDEC العربية", "简体中文 \uD83C\uDDE8\uD83C\uDDF3", "Español \uD83C\uDDEA\uD83C\uDDF8\n",
                "français \uD83C\uDDEB\uD83C\uDDF7", "हिन्दी \uD83C\uDDEE\uD83C\uDDF3", "русский \uD83C\uDDF7\uD83C\uDDFA",
                "português \uD83C\uDDF5\uD83C\uDDF9", "italiano \uD83C\uDDEE\uD83C\uDDF9"};

        spinner = findViewById(R.id.spinner_language);
        toggleButton = findViewById(R.id.toggleButton);
        toggleButton_click = findViewById(R.id.toggleButtonClick);

        SharedPreferences sharedPreferences = getSharedPreferences("MusicPrefs", MODE_PRIVATE);
        boolean isMusicOn = sharedPreferences.getBoolean("MusicOn", true);
        toggleButton.setChecked(isMusicOn);

        SharedPreferences sharedPreferencesClick = getSharedPreferences("ClickPrefs", MODE_PRIVATE);
        boolean isClickOn = sharedPreferencesClick.getBoolean("ClickOn", true);
        toggleButton_click.setChecked(isClickOn);

        arrayAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, languages);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("UnsafeIntentLaunch")
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isUserAction) {
                    String selectedLanguage = (String) parent.getItemAtPosition(position);
                    String languageCode = null;
                    switch (selectedLanguage) {
                        case "English \uD83C\uDDEC\uD83C\uDDE7":
                            setLocal(SettingsActivity.this, "en");
                            languageCode = "en";
                            break;
                        case "Deutsch \uD83C\uDDE9\uD83C\uDDEA":
                            setLocal(SettingsActivity.this, "de");
                            languageCode = "de";
                            break;
                        case "\uD83C\uDDEA\uD83C\uDDEC العربية":
                            setLocal(SettingsActivity.this, "ar");
                            languageCode = "ar";
                            break;
                        case "简体中文 \uD83C\uDDE8\uD83C\uDDF3":
                            setLocal(SettingsActivity.this, "zh");
                            languageCode = "zh";
                            break;
                        case "Español \uD83C\uDDEA\uD83C\uDDF8\n":
                            setLocal(SettingsActivity.this, "es");
                            languageCode = "es";
                            break;
                        case "français \uD83C\uDDEB\uD83C\uDDF7":
                            setLocal(SettingsActivity.this, "fr");
                            languageCode = "fr";
                            break;
                        case "हिन्दी \uD83C\uDDEE\uD83C\uDDF3":
                            setLocal(SettingsActivity.this, "hi");
                            languageCode = "hi";
                            break;
                        case "русский \uD83C\uDDF7\uD83C\uDDFA":
                            setLocal(SettingsActivity.this, "ru");
                            languageCode = "ru";
                            break;
                        case "português \uD83C\uDDF5\uD83C\uDDF9":
                            setLocal(SettingsActivity.this, "pt");
                            languageCode = "pt";
                            break;
                        case "italiano \uD83C\uDDEE\uD83C\uDDF9":
                            setLocal(SettingsActivity.this, "it");
                            languageCode = "it";
                            break;
                    }

                    // Restart the activity to apply the language change
                    saveLanguagePreference(languageCode);
                    setResult(RESULT_OK);
                    finish();
                    startActivity(getIntent());
                }
                isUserAction = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        toggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Start the music service
                startService(new Intent(SettingsActivity.this, MusicService.class));
            } else {
                // Stop the music service
                stopService(new Intent(SettingsActivity.this, MusicService.class));
            }

            // Save the music state
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("MusicOn", isChecked).apply();
        });

        toggleButton_click.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferencesClick.edit();
            editor.putBoolean("ClickOn", isChecked).apply();
        });
    }

    private void saveLanguagePreference(String languageCode) {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Language", languageCode);
        editor.apply();
    }

    public void setLocal(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}