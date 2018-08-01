// To get a new class: File -> Java Class

package com.example.steve.statecapitalsquiz;

public class Question {
    private int mTextResourceID;
    private boolean mAnswerTrue;
    private int mHint_ID;

    public Question ( int textResId, boolean answerTrue, int hint_ID )
    {
        mTextResourceID = textResId;
        mAnswerTrue = answerTrue;
        mHint_ID = hint_ID;
    }

    public int getTextResourceID() {
        return mTextResourceID;
    }

    public void setTextResourceID(int textResourceID) {
        mTextResourceID = textResourceID;
    }

    public boolean isAnswerTrue()
    {
        return mAnswerTrue;
    }

    public void setAnswerTrue( boolean answerTrue )
    {
        mAnswerTrue = answerTrue;
    }

    public int getHint_ID() { return mHint_ID; }

    public void setHint_ID( int questionHintID )  {  mHint_ID = questionHintID; }

}
