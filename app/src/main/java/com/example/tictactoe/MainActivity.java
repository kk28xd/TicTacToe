package com.example.tictactoe;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView settings;
    ImageView vsFriend;
    ImageView vsRobot;
    private ActivityResultLauncher<Intent> settingsLauncher;
    private SoundPool soundPool;
    private int clickSoundId;

    boolean areWeWillLeave = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settings = findViewById(R.id.settings);
        vsFriend = findViewById(R.id.imageFriend);
        vsRobot = findViewById(R.id.imageRobot);

        SharedPreferences sharedPreferences = getSharedPreferences("MusicPrefs", MODE_PRIVATE);
        boolean isMusicOn = sharedPreferences.getBoolean("MusicOn", true);
        if (isMusicOn) {
            startService(new Intent(this, MusicService.class));
        }

        SharedPreferences sharedPreferencesClick = getSharedPreferences("ClickPrefs", MODE_PRIVATE);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        // Load the click sound
        clickSoundId = soundPool.load(this, R.raw.click, 1);

        settingsLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        recreate();
                        areWeWillLeave = false;
                    }
                }
        );

        settings.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            settingsLauncher.launch(intent);
        });

        vsFriend.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(MainActivity.this, AddPlayersActivity.class);
            startActivity(intent);
        });

        vsRobot.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(MainActivity.this, DifficultyLevelActivity.class);
            startActivity(intent);
        });
    }

    private void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (areWeWillLeave){
            Intent intent = new Intent(this, MusicService.class);
            stopService(intent);
        }
        areWeWillLeave = true;
    }
}