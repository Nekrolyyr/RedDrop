package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import ch.versusvirus.reddrop.R;



public class SurveyResultActivity extends AppCompatActivity {

    Button btn_proceed;
    TextView text_field_result;

    private String Result_str = "This is your Result";
    private String Proceed_button_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();
        int results[] = (int[]) bundle.getIntArray("RESULTS");

        int sum_test = sumArray(results);

        if (sum_test==0) {
            Result_str="Great! You are eligible to donate.";
        //} else if (sum_test==1) {
            //Result_str="There is only one issue:";
            //TODO: Show the wrong question
        } else {
            Result_str="Unfortunately you are not eligible to donate.";
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);

        btn_proceed = (Button) findViewById(R.id.button_proceed);
        text_field_result = (TextView) findViewById(R.id.text_field_result);
        text_field_result.setText(Result_str);

        if (sum_test==0) {
            Proceed_button_text="Donate now";
        } else {
            Proceed_button_text="Redo test";
        }


        btn_proceed.setText(Proceed_button_text);
        btn_proceed.setOnClickListener(v -> {
            if (sum_test==0) {
                startActivity(new Intent(this, LocationActivity.class));
            } else {
                startActivity(new Intent(this, SurveyActivity.class));
            }

        });

        findViewById(R.id.button_go_home).setOnClickListener(v -> {
            startActivity(new Intent(this, HomeActivity.class));
        });

    }

    public static int sumArray(int Array[]) {
        int SumArray = 0;

        for (int i = Array.length; --i >= 0;)
            SumArray += Array[i];

        return SumArray;
    }

}
