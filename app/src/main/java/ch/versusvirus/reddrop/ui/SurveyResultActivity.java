package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.Questions;


public class SurveyResultActivity extends AppCompatActivity {

    Button btn_proceed;
    TextView text_field_result;

    private Questions mQuestions = new Questions();
    private String Result_str = "This is your Result";
    private String Proceed_button_text;
    private int mQuestionsLength = mQuestions.mQuestions.length;

    public static int sumArray(int[] Array) {
        int SumArray = 0;

        for (int i = Array.length; --i >= 0; )
            SumArray += Array[i];

        return SumArray;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_result);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        myToolbar.findViewById(R.id.btn_toolbar_home).setOnClickListener(v -> startActivity(new Intent(this, HomeActivity.class)));
        ((TextView) myToolbar.findViewById(R.id.txt_toolbar_title)).setText("Survey Result");

        Intent intent = getIntent();
        StringBuilder stringBuilder = new StringBuilder();

        Bundle bundle = intent.getExtras();
        int[] results = bundle.getIntArray("RESULTS");

        int sum_test = sumArray(results);

        if (sum_test==0) {
            stringBuilder.append("\n\n\n Great! You are eligible to donate.\n\n\n");
        } else {
            stringBuilder.append("\n Unfortunately you are not eligible to donate due to the following reason(s):\n \n");
            for(int num = 0; num < mQuestionsLength; num++) {
                if(results[num] == 1){
                    stringBuilder.append(mQuestions.getEvaluationCriteria(num));
                    stringBuilder.append("\n \n");
                }

            }

            Result_str="Unfortunately you are not eligible to donate.";
        }

        btn_proceed = findViewById(R.id.button_proceed);
        text_field_result = findViewById(R.id.text_field_result);
        text_field_result.setText(stringBuilder.toString());

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
            Intent intent1 = new Intent(this, HomeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent1);
        });

    }

}
