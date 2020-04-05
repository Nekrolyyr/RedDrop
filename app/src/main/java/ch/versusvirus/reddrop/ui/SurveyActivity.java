package ch.versusvirus.reddrop.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ch.versusvirus.reddrop.R;
import ch.versusvirus.reddrop.logic.model.Questions;


public class SurveyActivity extends AppCompatActivity {

    Button btn_yes, btn_no, btn_CoronaAlert;
    TextView text_field_question;

    private Questions mQuestions = new Questions();
    private String mAnswer;
    private int mQuestionsLength = mQuestions.mQuestions.length;
    private int mCounter = 0;
    private float fraction = (100/mQuestionsLength);
    private int[] mSurvey = new int[mQuestionsLength];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        setTitle("Survey");

        btn_yes = (Button) findViewById(R.id.btn_yes);
        btn_no = (Button) findViewById(R.id.btn_no);
        text_field_question = (TextView) findViewById(R.id.text_field_question);

        updateQuestion(mCounter);

        ProgressBar moveBar = (ProgressBar) findViewById(R.id.progress);
        moveBar.setProgress(0);

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_yes.getText() == mAnswer) {
                    mSurvey[mCounter] = 0;
                } else {
                    mSurvey[mCounter] = 1;
                }
                mCounter++;
                if ((mCounter) == mQuestionsLength) {
                    Intent intent = new Intent(SurveyActivity.this, SurveyResultActivity.class);
                    intent.putExtra("RESULTS",mSurvey);
                    startActivity(intent);
                } else {
                    moveBar.setProgress( (int)(fraction * mCounter));
                    updateQuestion(mCounter);}
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btn_no.getText() == mAnswer) {
                    mSurvey[mCounter] = 0;
                } else {
                    mSurvey[mCounter] = 1;
                }
                mCounter++;
                if ((mCounter) == mQuestionsLength) {
                    Intent intent = new Intent(SurveyActivity.this, SurveyResultActivity.class);
                    intent.putExtra("RESULTS",mSurvey);
                    startActivity(intent);
                } else {
                    moveBar.setProgress( (int)(fraction * mCounter));
                    updateQuestion(mCounter);}
            }
        });

        findViewById(R.id.btn_CoronaAlert).setOnClickListener(v -> goToUrl("https://www.blutspende.ch/de/blutspende"));
    }

     private void updateQuestion(int num){
        text_field_question.setText(mQuestions.getQuestion(num));
        btn_yes.setText(mQuestions.getChoice1(num));
        btn_no.setText(mQuestions.getChoice2(num));
        mAnswer = mQuestions.getCorretAnswer(num);
    }

    @Override
    public void onBackPressed() {
        ProgressBar moveBar = (ProgressBar) findViewById(R.id.progress);
        if  (mCounter==0) {
            super.onBackPressed();
        }
        else {
            mCounter--;
            updateQuestion(mCounter);
            moveBar.setProgress((int) (fraction * mCounter));
        }
    }

    private void goToUrl(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
