package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioAttributes;

import java.util.Random;

import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Playing_VS_Robot extends AppCompatActivity {

    TextView playerOne;
    LinearLayout playerOneLayout;
    ImageView image9, image8, image7, image6, image5, image4, image3, image2, image1;
    ResultDialog resultDialog;
    private boolean isPlayerTurnFirst = false; // Initialize to player starting first
    private final List<int[]> combinationList = new ArrayList<>();
    private int[] boxPositions = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    private int playerTurn = 1;
    private int totalSelectedBoxes = 0;

    private SoundPool soundPool;
    private int clickSoundId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playingvs_robot);

        playerOne = findViewById(R.id.text_player);
        playerOneLayout = findViewById(R.id.player);
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
            String winner = (playerTurn == 1) ? playerOne.getText().toString() : getString(R.string.robot);
            resultDialog = new ResultDialog(Playing_VS_Robot.this, winner + " " + getString(R.string.winner), Playing_VS_Robot.this, isClickOn, soundPool, clickSoundId);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else if (totalSelectedBoxes == 9) {
            resultDialog = new ResultDialog(Playing_VS_Robot.this, getString(R.string.draw), Playing_VS_Robot.this, isClickOn, soundPool, clickSoundId);
            resultDialog.setCancelable(false);
            resultDialog.show();
        } else {
            changePlayerTurn(playerTurn == 1 ? 2 : 1);
            if (playerTurn == 2) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    new Handler().postDelayed(this::computerPlay, this, 400);
                }
            }
        }
    }

    private void changePlayerTurn(int currentPlayerTurn) {
        playerTurn = currentPlayerTurn;
        if (playerTurn == 1) {
            playerOneLayout.setBackgroundResource(R.drawable.rounded_box_with_border);
        } else {
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
            computerPlay();
        }
    }

    private void playClickSound() {
        if (clickSoundId != 0) {
            soundPool.play(clickSoundId, 1, 1, 0, 0, 1);
        }
    }

    public int checkWinner(int[] board) {
        // Check rows for a win
        for (int i = 0; i < 9; i += 3) {
            if (board[i] == board[i + 1] && board[i + 1] == board[i + 2] && board[i] != 0) {
                return (board[i] == 2) ? 10 : -10;
            }
        }

        // Check columns for a win
        for (int i = 0; i < 3; i++) {
            if (board[i] == board[i + 3] && board[i + 3] == board[i + 6] && board[i] != 0) {
                return (board[i] == 2) ? 10 : -10;
            }
        }

        // Check diagonals for a win
        if (board[0] == board[4] && board[4] == board[8] && board[0] != 0) {
            return (board[0] == 2) ? 10 : -10;
        }

        if (board[2] == board[4] && board[4] == board[6] && board[2] != 0) {
            return (board[2] == 2) ? 10 : -10;
        }


        // Check for a draw
        boolean isDraw = true;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                isDraw = false;
                break;
            }
        }

        if (isDraw) {
            return 0; // Draw
        }

        // Game is still ongoing
        return Integer.MIN_VALUE;
    }


    private boolean isBoardFull(int[] board) {
        for (int i : board) {
            if (i == 0) {
                return false;
            }
        }
        return true;
    }

    private int minimax(int[] board, int depth, boolean isMaximizing) {
        int score = checkWinner(board);

        if (score == 10 || score == -10) {
            return score;
        }

        if (isBoardFull(board)) {
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 2; // 2 is the computer
                    best = Math.max(best, minimax(board, depth + 1, false));
                    board[i] = 0;
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;

            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 1; // 1 is the player
                    best = Math.min(best, minimax(board, depth + 1, true));
                    board[i] = 0;
                }
            }
            return best;
        }
    }

    private int findBestMove() {
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (boxPositions[i] == 0) {
                boxPositions[i] = 2; // 2 is the computer
                int moveVal = minimax(boxPositions, 0, false);
                boxPositions[i] = 0;

                if (moveVal > bestVal) {
                    bestMove = i;
                    bestVal = moveVal;
                }
            }
        }
        return bestMove;
    }

    private void computerPlay() {
        // Check if the board is empty
        if (isBoardEmpty()) {
            makeRandomMove(); // Make a random move if the board is empty
        } else {
            int bestMove = findBestMove();
            makeMove(bestMove); // Perform the move
        }
    }

    private boolean isBoardEmpty() {
        for (int position : boxPositions) {
            if (position != 0) {
                return false; // If any position is not empty, return false
            }
        }
        return true; // All positions are empty
    }

    private void makeRandomMove() {
        Random random = new Random();
        int randomPosition;

        do {
            randomPosition = random.nextInt(9); // Generate a random position from 0 to 8
        } while (!isBoxSelectable(randomPosition)); // Ensure the random position is selectable

        makeMove(randomPosition);
    }

    private void makeMove(int position) {
        ImageView selectedImageView;

        switch (position) {
            case 0:
                selectedImageView = image1;
                break;
            case 1:
                selectedImageView = image2;
                break;
            case 2:
                selectedImageView = image3;
                break;
            case 3:
                selectedImageView = image4;
                break;
            case 4:
                selectedImageView = image5;
                break;
            case 5:
                selectedImageView = image6;
                break;
            case 6:
                selectedImageView = image7;
                break;
            case 7:
                selectedImageView = image8;
                break;
            case 8:
                selectedImageView = image9;
                break;
            default:
                return;
        }
        performAction(selectedImageView, position, false, soundPool, clickSoundId);
    }
}