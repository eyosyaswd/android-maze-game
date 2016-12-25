package edu.wm.cs.cs301.amazebyeyosyas.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;

import edu.wm.cs.cs301.amazebyeyosyas.R;

/**
 * Same as STATE_TITLE. It is the screen shown when the maze application is started. It allowed the user to choose parameters for the maze.
 */
public class AMazeActivity extends AppCompatActivity {

    public String tag = "AMazeActivity";
    public String driver = "";
    public String builder = "";
    public int skillLevel = 0;

    /**
     * Perform initialization of all fragments and loaders.
     * @param savedInstanceState - If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amaze);
        final MediaPlayer backgroundMusic = MediaPlayer.create(this, R.raw.bg_music);
        backgroundMusic.start();

        SeekBar skillLevelSeekBar = (SeekBar) findViewById(R.id.skillLevel_seekBar);
        //Log.v(tag, "Skill Level Selected is: " + skillLevelSeekBar.getProgress());
        skillLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                this.progress = progress;
                skillLevel = this.progress;
                //Log.v(tag, "Skill Level Selected is: " + skillLevel);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                showToast("Skill level selected is: " + progress);
            }
        });

        Spinner builderSpinner = (Spinner) findViewById(R.id.builder_spinner);
        ArrayAdapter<CharSequence> builderAdapter = ArrayAdapter.createFromResource(this, R.array.builder, android.R.layout.simple_spinner_item);
        builderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        builderSpinner.setAdapter(builderAdapter);

        // Toast for maze generators
        builderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (count != 0) {
                    showToast("Builder Changed To: " + parent.getItemAtPosition(position).toString());
                }
                count ++;
                builder = parent.getItemAtPosition(position).toString();
                //Log.v(tag, "Builder (a.k.a. Generator) selected is: " + builder);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Spinner driverSpinner = (Spinner) findViewById(R.id.driver_spinner);
        ArrayAdapter<CharSequence> driverAdapter = ArrayAdapter.createFromResource(this, R.array.driver, android.R.layout.simple_spinner_item);
        driverAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        driverSpinner.setAdapter(driverAdapter);

        // Toast for maze drivers
        driverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int count = 0;
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (count != 0) {
                    showToast("Driver Changed To: " + parent.getItemAtPosition(position).toString());
                }
                count ++;
                driver = parent.getItemAtPosition(position).toString();
                //Log.v(tag, "Driver selected is: " + driver);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button exploreButton = (Button) findViewById(R.id.explore_button);
        exploreButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Changes activity from AMazeActivity to GeneratingActivity when exploreButton is clicked.
             * @param v - the view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showToast("Explore Button Pressed");
                Intent exploreIntent = new Intent(AMazeActivity.this, GeneratingActivity.class);
                exploreIntent.putExtra("driver_selected", driver);
                exploreIntent.putExtra("builder_selected", builder);
                exploreIntent.putExtra("skillLevel_selected", skillLevel);
                startActivity(exploreIntent);
            }
        });

        // I will need to change what revisitButton does for Project 7
        Button revisitButton = (Button) findViewById(R.id.revisit_button);
        revisitButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Changes activity from AMazeActivity to GeneratingActivity when revisitButton is clicked.
             * @param v - the view that was clicked.
             */
            @Override
            public void onClick(View v) {
                showToast("Revisit Button Pressed");
                Intent revisitIntent = new Intent(AMazeActivity.this, GeneratingActivity.class);
                revisitIntent.putExtra("driver_selected", driver);
                revisitIntent.putExtra("builder_selected", builder);
                revisitIntent.putExtra("skillLevel_selected", skillLevel);
                startActivity(revisitIntent);
            }
        });
    }

    /**
     * Overrides the back button so when clicked, the app closes (minimized not terminated).
     */
    @Override
    public void onBackPressed() {
        showToast("Back Button Pressed");
        moveTaskToBack(true);
    }

    /**
     * Displays a toast with the given string message
     * @param s - string message to be displayed
     */
    private void showToast(String s) {
        //Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        Log.v(tag, s);
    }

}
