package com.example.educare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Your_score extends Activity {

    TextView t1;
    int yourScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_score);

        t1 = findViewById(R.id.score);

        // Get the score passed from Attend_assessment
        yourScore = getIntent().getIntExtra("yourscore", 0);

        // Display the score
        t1.setText("Your Score: " + yourScore);
    }
}
