package com.mbia.cyrille.localnotif;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class AnswerRecActivity extends AppCompatActivity {
    private TextView AnswerRecText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_rec);
        AnswerRecText = (TextView) findViewById(R.id.AnswerRecText);
        Log.d("Main", getIntent().getAction());

        AnswerRecText.setText(getIntent().getAction());
    }
}
