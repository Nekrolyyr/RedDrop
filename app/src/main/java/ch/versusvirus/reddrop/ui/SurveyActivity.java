package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Vector;
import java.util.stream.IntStream;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.Questions;


public class SurveyActivity extends AppCompatActivity {

    Button btn_yes, btn_no;

    TextView text_field_question;

    private Questions mQuestions = new Questions();
    private String mAnswer;
    private int mQuestionsLength = mQuestions.mQuestions.length;
    private int mCounter = 0;
    private Vector<Integer> mSurvey = new Vector<Integer>(mQuestionsLength);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        /*findViewById(R.id.btn_submit).setOnClickListener(v -> {
            startActivity(new Intent(this, LocationActivity.class));
        });*/

        btn_yes = (Button) findViewById(R.id.btn_yes);
        btn_no = (Button) findViewById(R.id.btn_no);

        text_field_question = (TextView) findViewById(R.id.text_field_question);


        updateQuestion(mCounter);
        mCounter++;

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_yes.getText() == mAnswer) {
                    mSurvey.add(0);
                } else {
                    mSurvey.add(1);
                }
                updateQuestion(mCounter);
                mCounter++;
                if (mCounter == mQuestionsLength) {
                    Intent intent = new Intent(SurveyActivity.this, SurveyResultActivity.class);
                    intent.putExtra("Results",mSurvey);
                    startActivity(intent);
                }
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_no.getText() == mAnswer) {
                    mSurvey.add(0);
                } else {
                    mSurvey.add(1);
                }
                updateQuestion(mCounter);
                mCounter++;
                if (mCounter == mQuestionsLength) {
                    Intent intent = new Intent(SurveyActivity.this, SurveyResultActivity.class);
                    intent.putExtra("Results",mSurvey);
                    startActivity(intent);
                }
            }
        });


    }

     private void updateQuestion(int num){
        text_field_question.setText(mQuestions.getQuestion(num));
        btn_yes.setText(mQuestions.getChoice1(num));
        btn_no.setText(mQuestions.getChoice2(num));
        mAnswer = mQuestions.getCorretAnswer(num);
    }

}
