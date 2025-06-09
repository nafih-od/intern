package com.example.educare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Attend_assessment extends AppCompatActivity implements JsonResponse {

    ListView l1;
    Button b1;

    public static String yourscore;

    SharedPreferences sh;

    String[] question, option1, option2, option3, answer, score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.activity_attend_assessment);

        l1 = findViewById(R.id.list);
        b1 = findViewById(R.id.submit);

        JsonReq jr = new JsonReq();
        jr.json_response = (JsonResponse) Attend_assessment.this;
        String q = "/attend_assessment?id=" + Custom_view_assessment.assess;
        jr.execute(q);

//        b1.setOnClickListener(v -> {
//            Custom_attend_assessment adapter = (Custom_attend_assessment) l1.getAdapter();
//            String[] selectedAnswers = adapter.getSelectedAnswers();
//            int totalScore = calculateScore(selectedAnswers);
//            Toast.makeText(getApplicationContext(), "Your score: " + totalScore, Toast.LENGTH_LONG).show();
//
//
//
//
//        });


        b1.setOnClickListener(v -> {
            Custom_attend_assessment adapter = (Custom_attend_assessment) l1.getAdapter();
            String[] selectedAnswers = adapter.getSelectedAnswers();

            int totalScore = calculateScore(selectedAnswers);

            Intent intent = new Intent(getApplicationContext(), Your_score.class);
            intent.putExtra("yourscore", totalScore);
            startActivity(intent);
        });


    }

    @Override
    public void response(JSONObject jo) throws JSONException {
        try {
            String method = jo.getString("method");
            String status = jo.getString("status");
            Log.d("pearl", status);

            if (method.equalsIgnoreCase("view")) {
                if (status.equalsIgnoreCase("success")) {
                    JSONArray ja1 = jo.getJSONArray("data");

                    question = new String[ja1.length()];
                    option1 = new String[ja1.length()];
                    option2 = new String[ja1.length()];
                    option3 = new String[ja1.length()];
                    answer = new String[ja1.length()]; // This will hold the correct answer
                    score = new String[ja1.length()];

                    for (int i = 0; i < ja1.length(); i++) {
                        question[i] = ja1.getJSONObject(i).getString("question");
                        option1[i] = ja1.getJSONObject(i).getString("option_1");
                        option2[i] = ja1.getJSONObject(i).getString("option_2");
                        option3[i] = ja1.getJSONObject(i).getString("option_3");
                        answer[i] = ja1.getJSONObject(i).getString("answer"); // The correct answer
                        score[i] = ja1.getJSONObject(i).getString("score");
                    }

                    Custom_attend_assessment ar = new Custom_attend_assessment(Attend_assessment.this, question, option1, option2, option3, answer);
                    l1.setAdapter(ar);
                }
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private int calculateScore(String[] selectedAnswers) {
        int totalScore = 0;
        for (int i = 0; i < selectedAnswers.length; i++) {
            // Log the selected answer and the correct answer for debugging
            Log.d("ScoreCalculation", "Question " + (i + 1) + ": Selected = " + selectedAnswers[i] + ", Correct = " + answer[i]);

            if (selectedAnswers[i] != null && selectedAnswers[i].equals(answer[i])) {
                totalScore += Integer.parseInt(score[i]); // Add the score for the correct answer
            }
        }
        return totalScore;
    }
}