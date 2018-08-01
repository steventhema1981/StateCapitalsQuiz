// To get a New Project, File --> New Project..

package com.example.steve.statecapitalsquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizActivity extends AppCompatActivity {

    // Declare my variables here.
    private Button mTrueButton;     // Common practice in Android...start variable name
                                    //    with m, which stands for "member."
    private Button mFalseButton;
    private Button mNextButton;
    private Button mHintButton;
    private Button mPrevButton;

    private TextView mQuestionTextView;

    private Question[] mQuestionBank = new Question[] {
            new Question( R.string.question1,false, R.string.hint1 ),
            new Question( R.string.question2, true, R.string.hint2 ),
            new Question( R.string.question3, false, R.string.hint3 ),
            new Question( R.string.question4, false, R.string.hint4 ),
            new Question( R.string.question5, true, R.string.hint5 )
    };

    private int mCurrentIndex = 0;

    private int mScore = 0;
    private TextView mScoreTextView;

    private int mHighScore;
    private TextView mHighScoreTextView;

    // Declare my database.
    FirebaseDatabase database;
    DatabaseReference myRef;


    public int getScore()   {    return mScore;   }


    public void checkAnswer( boolean userPressedTrue )
    {
        boolean answerIsTrue = mQuestionBank[ mCurrentIndex ].isAnswerTrue();

        int messageResID = 0;

        if ( userPressedTrue == answerIsTrue )
        {
            messageResID = R.string.correct_toast;
            mScore++;
        }
        else
        {
            messageResID = R.string.incorrect_toast;
            mScore--;
        }
        Toast.makeText( this, messageResID, Toast.LENGTH_SHORT ).show();

        updateScore( mScore );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Instantiate database.
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Score" );

        // Read from the database
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.


                // String value = dataSnapshot.getValue(String.class);  // do this if database value is a string
                int value = dataSnapshot.getValue(Integer.class);
                mHighScore = value;

                mScoreTextView.setText( "I READ IN..." + mScore + " The value is: " + value );
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                mScoreTextView.setText( "FAIL" );

            }
        });

        // myRef.setValue( mScore + 9876 );   // Write to database.



        // Inflate / instantiate my Buttons here.
        mTrueButton = (Button)findViewById( R.id.true_button );
        mFalseButton = (Button)findViewById( R.id.false_button );
        mNextButton = (Button)findViewById( R.id.next_button );
        mPrevButton = (Button)findViewById( R.id.prev_button );
        mHintButton = (Button)findViewById( R.id.answer_button );

        mScoreTextView = (TextView)findViewById( R.id.score_text_view );
        mHighScoreTextView = (TextView)findViewById( R.id.high_score_text_view );

        mQuestionTextView = (TextView)findViewById( R.id.question_text_view);
        int question = mQuestionBank[ mCurrentIndex ].getTextResourceID();
        mQuestionTextView.setText( question );

        // Attach listeners to my buttons.
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer( true );
                /* Toast.makeText( QuizActivity.this,
                                R.string.incorrect_toast,
                                Toast.LENGTH_SHORT ).show();
                */
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                checkAnswer( false );

                /* Toast.makeText(QuizActivity.this,
                                R.string.correct_toast,
                                Toast.LENGTH_SHORT ).show(); */

            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
            }
        });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                if ( (mCurrentIndex - 1) < 0 )
                    mCurrentIndex = mQuestionBank.length - 1;
                else
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;

                updateQuestion();
            }

        } );
    }

    private void updateScore( int theScore )
    {
        mScoreTextView.setText( "Score: " + theScore );

        if ( theScore > mHighScore )
        {
            mHighScore = theScore;
            myRef.setValue( mHighScore );   // Write the high score to the database.
        }

        mHighScoreTextView.setText( "High Score: " + mHighScore);
    }

    private void updateQuestion()
    {
        int question = mQuestionBank [ mCurrentIndex ] .getTextResourceID();
        mQuestionTextView.setText(question);
    }

    public void answerOnClick(View view) {
        // Intent i = new Intent( QuizActivity.this, HintActivity.class );
        Intent i = new Intent( this, HintActivity.class );
        i.putExtra( "ANSWER", mQuestionBank[ mCurrentIndex ].isAnswerTrue() );
        i.putExtra( "HINT", mQuestionBank[ mCurrentIndex ].getHint_ID() );
        startActivity( i );
    }
}
