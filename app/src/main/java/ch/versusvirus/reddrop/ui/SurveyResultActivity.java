package ch.versusvirus.reddrop.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.Questions;

public class SurveyResultActivity extends AppCompatActivity {

    Button btn_donate, btn_redo, btn_home;

    TextView text_field_question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);
        /*findViewById(R.id.btn_submit).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });*/

        btn_donate = (Button) findViewById(R.id.btn_donate);
        btn_redo = (Button) findViewById(R.id.btn_redo);
        btn_home = (Button) findViewById(R.id.btn_home);

        text_field_question = (TextView) findViewById(R.id.text_field_question);


        btn_donate.setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });

        btn_redo.setOnClickListener(v -> {
            startActivity(new Intent(this,SurveyActivity.class));
        });

        btn_donate.setOnClickListener(v -> {
            startActivity(new Intent(this, SurveyActivity.class));
        });

    }

}
