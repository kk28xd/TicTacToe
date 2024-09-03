package com.example.tictactoe;

import android.app.Dialog;
import android.content.Context;
import android.media.SoundPool;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;

public class ResultDialog extends Dialog {

    private final String message;
    private final boolean isClickOn;
    private PlayingWithAFriend playingWithAFriend = null;
    private final SoundPool soundPool;
    private final int clickSoundId;
    private Playing_VS_Robot playingVSRobot = null;

    public ResultDialog(@NonNull Context context, String message, PlayingWithAFriend playingWithAFriend, boolean isClickOn, SoundPool soundPool, int clickSoundId) {
        super(context);
        this.message = message;
        this.isClickOn = isClickOn;
        this.playingWithAFriend = playingWithAFriend;
        this.soundPool = soundPool;
        this.clickSoundId = clickSoundId;
    }

    public ResultDialog(@NonNull Context context, String message, Playing_VS_Robot playingVSRobot, boolean isClickOn, SoundPool soundPool, int clickSoundId) {
        super(context);
        this.message = message;
        this.isClickOn = isClickOn;
        this.playingVSRobot = playingVSRobot;
        this.soundPool = soundPool;
        this.clickSoundId = clickSoundId;
    }

    TextView textMessage;
    Button startAgain;
    Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_dialog);

        textMessage = findViewById(R.id.messageText);
        startAgain = findViewById(R.id.startAgainButton);
        exit = findViewById(R.id.exitButton);


        textMessage.setText(message);

        startAgain.setOnClickListener(v -> {
            if (isClickOn) {
                playClickSound();
            }
            if (playingWithAFriend != null) {
                playingWithAFriend.restartMatch();
            } else {
                playingVSRobot.restartMatch();
            }
            dismiss();
        });

        exit.setOnClickListener(v -> {
            if (isClickOn) {
                playClickSound();
            }
            if (playingWithAFriend != null) {
                playingWithAFriend.finish();
            } else {
                playingVSRobot.finish();
            }
            dismiss();
        });
    }

    private void playClickSound() {
        if (soundPool != null && clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }
}