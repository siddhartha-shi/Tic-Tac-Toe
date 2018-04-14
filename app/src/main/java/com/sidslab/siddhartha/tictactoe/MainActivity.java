package com.sidslab.siddhartha.tictactoe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button block1, block2, block3, block4, block5, block6, block7, block8, block9, restart;
    private TextView result;
    private TicTacToe gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameState = new TicTacToe();

        block1 = findViewById(R.id.bt_block1);
        block2 = findViewById(R.id.bt_block2);
        block3 = findViewById(R.id.bt_block3);
        block4 = findViewById(R.id.bt_block4);
        block5 = findViewById(R.id.bt_block5);
        block6 = findViewById(R.id.bt_block6);
        block7 = findViewById(R.id.bt_block7);
        block8 = findViewById(R.id.bt_block8);
        block9 = findViewById(R.id.bt_block9);
        result = findViewById(R.id.tv_show_result);
        restart = findViewById(R.id.bt_restart_game);

        for (Button btn:
                new Button[]{block1, block2, block3, block4, block5, block6, block7, block8, block9}
                ) {
            btn.setOnClickListener(this);
        }

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRestartDialog(MainActivity.this);
            }
        });
    }

    /**
     * Shows the dialog when the restart button is clicked
     * Start new game, restart game, or cancel dialog
     * @param activity
     * get activity and show result
     */
    void showRestartDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(R.string.restart_message)
                .setTitle(R.string.app_name);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                restartGame();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    /**
     * resets the UI and gamestate
     */
    void restartGame() {
        for (Button btn:
                new Button[]{block1, block2, block3, block4, block5, block6, block7, block8, block9}
                ) {
            btn.setText("");
        }
        result.setText("");
        restart.setText(R.string.restart_button_text_in_middle_of_game);

        gameState.newGame();
    }

    /**
     * Handle the grid button clicks
     * @param view
     * get clickable view.
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_block1: checkBox(0, 0, (Button) view); break;
            case R.id.bt_block2: checkBox(1, 0, (Button) view); break;
            case R.id.bt_block3: checkBox(2, 0, (Button) view); break;
            case R.id.bt_block4: checkBox(0, 1, (Button) view); break;
            case R.id.bt_block5: checkBox(1, 1, (Button) view); break;
            case R.id.bt_block6: checkBox(2, 1, (Button) view); break;
            case R.id.bt_block7: checkBox(0, 2, (Button) view); break;
            case R.id.bt_block8: checkBox(1, 2, (Button) view); break;
            case R.id.bt_block9: checkBox(2, 2, (Button) view); break;
        }

    }

    void checkBox(int x, int y, Button box){
        if(!gameState.getCurrentGameStatus() || !result.getText().equals("")) return; //game not started yet
        if(!gameState.isMoveValid(x,y)) return; //box is already checked

        int currentPlayer = gameState.getCurrentPlayer();
        box.setText((currentPlayer == 1) ? R.string.player_1_move : R.string.player_2_move);

        //check for win
        TicTacToe.GameStatus status = gameState.updateGameState(x,y);
        if(status == TicTacToe.GameStatus.PLAYER_ONE_WIN || status == TicTacToe.GameStatus.PLAYER_TWO_WIN) {
            result.setText((currentPlayer == 1) ? R.string.player_1_wins : R.string.player_2_wins);
            restart.setText(R.string.restart_button_text_initially);
        }
        else if(status == TicTacToe.GameStatus.TIE_GAME){
            result.setText(R.string.draw);
        }
    }
}
