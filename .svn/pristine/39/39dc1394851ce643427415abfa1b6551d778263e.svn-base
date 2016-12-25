package edu.wm.cs.cs301.amazebyeyosyas.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.wm.cs.cs301.amazebyeyosyas.R;
import edu.wm.cs.cs301.amazebyeyosyas.falstad.Singleton;

/**
 * Same as STATE_FINISH. It is the last screen and is shown when the robot either exits the maze or runs out of batter.
 */
public class FinishActivity extends AppCompatActivity {

    public String tag = "FinishActivity";

    private Singleton maze;

    /**
     * Perform initialization of all fragments and loaders.
     * @param savedInstanceState - If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        maze = maze.getInstance();

        Intent clickedIntent = getIntent();
        String clicked = "";
        if (clickedIntent != null) {
            clicked = clickedIntent.getStringExtra("button_clicked");
        }

        if (clicked.equals("success_button")) {
            setContentView(R.layout.activity_finish_success);
            final MediaPlayer applause = MediaPlayer.create(this, R.raw.applause);
            applause.start();
        } else if (clicked.equals("failure_button")) {
            setContentView(R.layout.activity_finish_failure);
            final MediaPlayer boo = MediaPlayer.create(this, R.raw.boo);
            boo.start();
        }
        Vibrator vib = (Vibrator) getSystemService(this.VIBRATOR_SERVICE);
        vib.vibrate(1000);

        TextView pathLengthTextView = (TextView) findViewById(R.id.pathLengthNum_textView);
        pathLengthTextView.setText(Float.toString(clickedIntent.getFloatExtra("path_length", 0)));

        TextView energyConsumptionNumTextView = (TextView) findViewById(R.id.energyConsumtionNum_textView);
        energyConsumptionNumTextView.setText(Float.toString(clickedIntent.getFloatExtra("energy_consumed", 0)));

        Button playAgainButton = (Button) findViewById(R.id.playAgain_button);
        playAgainButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Changes activity from FinishActivity to AMazeActivity when playAgainButton is clicked.
             * @param v - the view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showToast("Play Again Button Pressed");
                Intent playAgain = new Intent(FinishActivity.this, AMazeActivity.class);
                startActivity(playAgain);
                maze = maze.getInstance();
                maze.killInstance();
            }
        });
    }

    /**
     * Overrides the back button so when clicked, the app goes to AMazeActivity
     */
    @Override
    public void onBackPressed() {
        showToast("Back Button Pressed");
        Intent backToTitle = new Intent(FinishActivity.this, AMazeActivity.class);
        startActivity(backToTitle);
        maze.killInstance();
    }

    /**
     * Displays a toast with the given string message
     * @param s - string message to be displayed
     */
    private void showToast(String s) {
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        //Log.v(tag, s);
    }

}
