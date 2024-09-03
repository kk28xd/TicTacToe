package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlayingWithAFriend extends AppCompatActivity {

    TextView playerOne, playerTwo;
    LinearLayout playerOneLayout, playerTwoLayout;
    ImageView image9, image8, image7, image6, image5, image4, image3, image2, image1;
    Intent intent;
    ResultDialog resultDialog;

    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 0;

    private SoundPool soundPool;
    private int clickSoundId;
    private boolean isPlayerTurnFirst = false; // Initialize to player starting first

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_with_a_friend);

        playerOne = findViewById(R.id.text_playerOne);
        playerTwo = findViewById(R.id.text_playerTwo);
        playerOneLayout = findViewById(R.id.playerOne);
        playerTwoLayout = findViewById(R.id.playerTwo);
        image1 = findViewById(R.id.topLeftBox);
        image2 = findViewById(R.id.topCenterBox);
        image3 = findViewById(R.id.topRightBox);
        image4 = findViewById(R.id.centerLeftBox);
        image5 = findViewById(R.id.centerBox);
        image6 = findViewById(R.id.centerRightBox);
        image7 = findViewById(R.id.bottomLeftBox);
        image8 = findViewById(R.id.bottomCenterBox);
        image9 = findViewById(R.id.bottomRightBox);

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

        intent = getIntent();
        playerOne.setText(intent.getStringExtra("playerOne"));
        playerTwo.setText(intent.getStringExtra("playerTwo"));

        combinationList.add(new int[]{0, 1, 2});
        combinationList.add(new int[]{0, 3, 6});
        combinationList.add(new int[]{0, 4, 8});
        combinationList.add(new int[]{1, 4, 7});
        combinationList.add(new int[]{2, 5, 8});
        combinationList.add(new int[]{2, 4, 6});
        combinationList.add(new int[]{3, 4, 5});
        combinationList.add(new int[]{6, 7, 8});


        image1.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(0)) {
                performAction((ImageView) v, 0, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image2.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(1)) {
                performAction((ImageView) v, 1, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }

        });
        image3.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(2)) {
                performAction((ImageView) v, 2, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }

        });
        image4.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(3)) {
                performAction((ImageView) v, 3, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image5.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(4)) {
                performAction((ImageView) v, 4, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image6.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(5)) {
                performAction((ImageView) v, 5, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image7.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(6)) {
                performAction((ImageView) v, 6, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image8.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(7)) {
                performAction((ImageView) v, 7, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });
        image9.setOnClickListener(v -> {
            if (sharedPreferencesClick.getBoolean("ClickOn", true)) {
                playClickSound();
            }
            if (isBoxSelectable(8)) {
                performAction((ImageView) v, 8, sharedPreferencesClick.getBoolean("ClickOn", true), soundPool, clickSoundId);
            }
        });

    }

    private boolean isBoxSelectable(int boxPosition) {
        return boxPositions[boxPosition] == 0;
    }

    private void performAction(ImageView imageView, int selectedBoxPosition, boolean isClickOn, SoundPool soundPool, int clickSoundId) {
        boxPositions[selectedBoxPosition] = playerTurn;
        imageView.setImageResource(playerTurn == 1 ? R.drawable.cross : R.drawable.zero);
        totalSelectedBoxes++;
        if (checkResults()) {
            String winner = (playerTurn == 1) ? playerOne.getText().toString() : playerTwo.getText().toString();
            resultDialog = new ResultDialog(PlayingWithAFriend.this, winner + " " + getString(R.string.winner), PlayingWithAFriend.this, isClickOn, soundPool, clickSoundId);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else if (totalSelectedBoxes == 9) {
            resultDialog = new ResultDialog(PlayingWithAFriend.this, getString(R.string.draw), PlayingWithAFriend.this, isClickOn, soundPool, clickSoundId);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else {
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.rounded_box_with_border);
            playerTwoLayout.setBackgroundResource(R.drawable.rounded_box);
        } else {
            playerTwoLayout.setBackgroundResource(R.drawable.rounded_box_with_border);
            playerOneLayout.setBackgroundResource(R.drawable.rounded_box);
        }
    }

    private boolean checkResults() {
        boolean response = false;
        for (int i = 0; i < combinationList.size(); i++) {
            final int[] combination = combinationList.get(i);
            if (boxPositions[combination[0]] == playerTurn && boxPositions[combination[1]] == playerTurn &&
                    boxPositions[combination[2]] == playerTurn) {
                response = true;
            }
        }
        return response;
    }

    public void restartMatch() {
        boxPositions = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        totalSelectedBoxes = 0;
        image1.setImageResource(R.drawable.rounded_box);
        image2.setImageResource(R.drawable.rounded_box);
        image3.setImageResource(R.drawable.rounded_box);
        image4.setImageResource(R.drawable.rounded_box);
        image5.setImageResource(R.drawable.rounded_box);
        image6.setImageResource(R.drawable.rounded_box);
        image7.setImageResource(R.drawable.rounded_box);
        image8.setImageResource(R.drawable.rounded_box);
        image9.setImageResource(R.drawable.rounded_box);

        if (isPlayerTurnFirst) {
            playerTurn = 1;
            isPlayerTurnFirst = false;
            changePlayerTurn(1);
        } else {
            playerTurn = 2;
            isPlayerTurnFirst = true;
            changePlayerTurn(2);
        }
    }

    private void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }
}