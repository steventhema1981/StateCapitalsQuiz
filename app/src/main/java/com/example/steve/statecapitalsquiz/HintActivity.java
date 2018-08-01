// To make a new activity, do File --> New --> Empty Activity

package com.example.steve.statecapitalsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HintActivity extends AppCompatActivity {

    // Declare Buttons and TextView objects here.
    private TextView mQuestionTextViewOnHint;
    private Button mShowAnswerButton;
    private TextView mAnswerTextView;
    private Button mHintButton;
    private TextView mHintTextView;
    private Button mBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hint);

        // mQuestionTextViewOnHint = (TextView)findViewById( R.id.question_text_view );
        mShowAnswerButton = (Button)findViewById( R.id.show_answer_button );
        mAnswerTextView = (TextView)findViewById( R.id.answer_text_view);
        mHintButton = (Button)findViewById( R.id.hint_button  );
        mHintTextView = (TextView)findViewById( R.id.hint_text_view );
        mBackButton = (Button)findViewById( R.id.back_button );

        mShowAnswerButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean answer = getIntent().getBooleanExtra("ANSWER", false );

                String displayedText = "";
                if ( answer == true )
                    displayedText = "The answer is: YAAAAS!";
                else
                    displayedText = "The answer is: NAH, BRAH.";

                mAnswerTextView.setText( displayedText );
            }
        } );


        mHintButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int hint = getIntent().getIntExtra("HINT", 0 );
                mHintTextView.setText( hint );

                // COMMENT THE LINE ABOVE, AND UNCOMMENT THE LINE OF CODE BELOW,
                //    AND THE HINT SHOWS UP IN A DIFFERENT PLACE!
                // mAnswerTextView.setText( displayedText );


            }
        } );

        mBackButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mHintTextView.setText( "I TRIED TO GO BACK!!!!" );
                finish();   // A method in the Activity class that finishes the activity you are on.
            }
        } );


    }
}
