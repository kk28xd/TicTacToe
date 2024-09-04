package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class DifficultyLevelActivity extends AppCompatActivity {

    Button easy, medium, hard;
    private SoundPool soundPool;
    private int clickSoundId;
    SharedPreferences sharedPreferencesClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_level);

        easy = findViewById(R.id.button_easy);
        medium = findViewById(R.id.button_medium);
        hard = findViewById(R.id.button_hard);

        sharedPreferencesClick = getSharedPreferences("ClickPrefs", MODE_PRIVATE);

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

        easy.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(DifficultyLevelActivity.this, Playing_VS_Robot.class);
            intent.putExtra("level", 1);
            startActivity(intent);
        });
        medium.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(DifficultyLevelActivity.this, Playing_VS_Robot.class);
            intent.putExtra("level", 2);
            startActivity(intent);
        });
        hard.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            Intent intent = new Intent(DifficultyLevelActivity.this, Playing_VS_Robot.class);
            intent.putExtra("level",3);
            startActivity(intent);
        });

    }

    private void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }
}