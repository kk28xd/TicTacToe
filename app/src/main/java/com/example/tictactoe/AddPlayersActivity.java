package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddPlayersActivity extends AppCompatActivity {

    EditText playerOne;
    EditText playerTwo;
    String playerOneName;
    String playerTwoName;
    Button startGame;
    private SoundPool soundPool;
    private int clickSoundId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        playerOne = findViewById(R.id.editText_playerOne);
        playerTwo = findViewById(R.id.editText_playerTwo);
        startGame = findViewById(R.id.button_startGame);


        SharedPreferences sharedPreferencesClick = getSharedPreferences("ClickPrefs", MODE_PRIVATE);


        //set up
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


        startGame.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }

            playerOneName = playerOne.getText().toString();
            playerTwoName = playerTwo.getText().toString();

            if (playerOneName.length() == 0 || playerTwoName.length() == 0) {
                if (playerOneName.length() == 0) {
                    playerOne.setError(getString(R.string.empty_field));
                }
                if (playerTwoName.length() == 0) {
                    playerTwo.setError(getString(R.string.empty_field));
                }
            } else {
                Intent intent = new Intent(AddPlayersActivity.this, PlayingWithAFriend.class);
                intent.putExtra("playerOne", playerOneName);
                intent.putExtra("playerTwo", playerTwoName);
                startActivity(intent);
            }
        });


    }

    private void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }
}