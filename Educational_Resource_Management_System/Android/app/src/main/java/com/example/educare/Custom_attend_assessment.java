package com.example.educare;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Custom_attend_assessment extends ArrayAdapter<String> {

    private Activity context;
    private String[] question, option1, option2, option3, correctAnswer;
    private String[] selectedAnswers; // To store selected answers

    public Custom_attend_assessment(Activity context, String[] question, String[] option1, String[] option2, String[] option3, String[] correctAnswer) {
        super(context, R.layout.activity_custom_attend_assessment, question);
        this.context = context;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.correctAnswer = correctAnswer; // This will hold the correct answers
        this.selectedAnswers = new String[question.length]; // Initialize selected answers array
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.activity_custom_attend_assessment, null, true);

        TextView questionText = listViewItem.findViewById(R.id.question);
        RadioButton option1Radio = listViewItem.findViewById(R.id.option1);
        RadioButton option2Radio = listViewItem.findViewById(R.id.option2);
        RadioButton option3Radio = listViewItem.findViewById(R.id.option3);
        RadioButton option4Radio = listViewItem.findViewById(R.id.option4); // Fourth option

        questionText.setText(question[position]);

        // Create a list of options and include the correct answer
        List<String> options = new ArrayList<>();
        options.add(option1[position]);
        options.add(option2[position]);
        options.add(option3[position]);
        options.add(correctAnswer[position]); // Add the correct answer as one of the options

        Collections.shuffle(options); // Shuffle the options

        // Set the shuffled options to the radio buttons
        option1Radio.setText(options.get(0));
        option2Radio.setText(options.get(1));
        option3Radio.setText(options.get(2));
        option4Radio.setText(options.get(3));

        // Set the checked state based on the selected answer
        option1Radio.setChecked(selectedAnswers[position] != null && selectedAnswers[position].equals(options.get(0)));
        option2Radio.setChecked(selectedAnswers[position] != null && selectedAnswers[position].equals(options.get(1)));
        option3Radio.setChecked(selectedAnswers[position] != null && selectedAnswers[position].equals(options.get(2)));
        option4Radio.setChecked(selectedAnswers[position] != null && selectedAnswers[position].equals(options.get(3)));

        // Set a listener to capture the selected answer
        option1Radio.setOnClickListener(v -> selectedAnswers[position] = options.get(0));
        option2Radio.setOnClickListener(v -> selectedAnswers[position] = options.get(1));
        option3Radio.setOnClickListener(v -> selectedAnswers[position] = options.get(2));
        option4Radio.setOnClickListener(v -> selectedAnswers[position] = options.get(3));

        return listViewItem;
    }

    public String[] getSelectedAnswers() {
        return selectedAnswers;
    }
}