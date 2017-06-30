package org.knoesis.health;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import org.knoesis.health.constants.Constants;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Activity to prompt users for input on the medications taken during the day.
 *
 * Created by Quintin on 8/31/2016.
 */
public class MedicationUsage extends KHealthAsthmaParentActivity {

    // User preferences object
    SharedPreferences userPreferences;

    // Dynamic lists to hold user medications
    ArrayList<String> shortTermMedications;
    ArrayList<String> longTermMedications;

    // List of user medication answers
    ArrayList<Integer> medicationAnswers;

    // Layout access objects
    RadioButton positiveAnswer;
    Button nextQuestion;
    Button prevQuestion;
    boolean fromCurrent, fromDay, fromNight;
    String type;

    // Index used to track question number and medicine question text
    int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_usage);

        Bundle extras = getIntent().getExtras();
        fromCurrent = extras.getBoolean("CURRENT", false);
        fromDay = extras.getBoolean("DAY", false);
        fromNight = extras.getBoolean("NIGHT", false);


        // Get a reference to the current user's preferences and populate the medications lists
        userPreferences = getSharedPreferences(asthmaApp.userLoggedIn + "Prefs", 0);

        shortTermMedications = new ArrayList<>();
        longTermMedications = new ArrayList<>();
        populateMedicationLists();

        // Check to make sure medications are being taken if not, skip this activity
        if(shortTermMedications.size() == 0 && longTermMedications.size() == 0){
            Class nextActivity = getNextActivity();
            // If the nextActivity is null an error has occurred. Return home.
            if(nextActivity == null){
                nextActivity = HomeActivity.class;
            }
            Intent intent = new Intent(MedicationUsage.this, nextActivity);
            startActivity(intent);
            finish();
            return;
        }

        // Create references to layout
        positiveAnswer = (RadioButton) findViewById(R.id.medication_positive);
        nextQuestion = (Button) findViewById(R.id.medication_next);
        prevQuestion = (Button) findViewById(R.id.medication_prev);

        resetQuestionText();

        ArrayList<Integer> temp_medicationAnswers = extras.getIntegerArrayList(type + "_medications");
        if (temp_medicationAnswers != null){
            medicationAnswers = temp_medicationAnswers;
        }
        if(medicationAnswers.size() == 1) {

        }
    }

    /**
     * Queries the user preferences to generate a list of short and long term medications that the
     * current user is using and creates a default list of answers.
     */
    private void populateMedicationLists() {
        if(shortTermMedications == null){
            shortTermMedications = new ArrayList<>();
        }
        // Add the short term medications that the current user is taking to the array list.
        for(String shortMedication : Constants.shortTermMedications){
            int isTaking = userPreferences.getInt(shortMedication, 0);
            if(isTaking == 1){
                shortTermMedications.add(shortMedication);
            }
        }

        if(longTermMedications == null){
            longTermMedications = new ArrayList<>();
        }
        // Add the long term medications that the current user is taking to the array list.
        for(String longMedication : Constants.longTermMedications){
            int isTaking = userPreferences.getInt(longMedication, -1);
            if(isTaking == 1){
                longTermMedications.add(longMedication);
            }
        }

        // Create a default list of answers that has a size equal to the size of the other lists
        medicationAnswers = new ArrayList<>(Collections.nCopies(
                Constants.shortTermMedications.length + Constants.longTermMedications.length, 0));
    }//end populateMedicationLists

    /**
     * Increments the question index so that the user will be asked about the next medication on the
     * list.
     */
    private void setNextQuestion(){
        if(questionIndex + 1 < shortTermMedications.size() + longTermMedications.size()){
            questionIndex++;
            resetQuestionText();
            prevQuestion.setVisibility(View.VISIBLE);
        }else{
            submit();
        }
    }//end setNextQuestion

    /**
     * Decrements the question index so that the user will be asked about the previous question.
     * The user's previous answer will be loaded into the radio buttons to help improve accuracy.
     */
    private void setPrevQuestion(){
        if(questionIndex > 0){
            questionIndex--;
            if(questionIndex == 0){
                prevQuestion.setVisibility(View.INVISIBLE);
            }
            resetQuestionText();
        }else{
            prevQuestion.setVisibility(View.INVISIBLE);
        }
    }//end setPrevQuestion

    /**
     * Sets the question text and radio button answer for the new question.
     * The radio button for an unanswered question will default to yes while previously answered
     * questions will default to how they were answered when previously seen during this session.
     */
    private void resetQuestionText(){
        // Set next button's text
        if(questionIndex == shortTermMedications.size() + longTermMedications.size() - 1){
            nextQuestion.setText(R.string.submit);
        } else {
            nextQuestion.setText(R.string.nextQuestion);
        }
        // Create variables to access the layout
        TextView medicationQuestionText = (TextView) findViewById(R.id.medication_question_text);
        RadioButton negativeAnswer = (RadioButton) findViewById(R.id.medication_negative);
        String beginPrompt = "Did you take";
        String timeFrame;
        String medicationName = questionIndex < shortTermMedications.size() ?
                shortTermMedications.get(getQuestionIndex()) :
                longTermMedications.get(getQuestionIndex());
        if(fromCurrent){
            timeFrame = "in the last 30 minutes";
            if(medicationName.toLowerCase().equals("symbicort")){
                timeFrame = "in the last 24 hours";
            }
            type = "CURRENT";
        }else if(fromNight){
            timeFrame = "during the night";
            type = "NIGHT";
        }else if(fromDay){
            timeFrame = "during the day";
            type = "DAY";
        }else{
            timeFrame = "since the last time you completed the questionnaire";
        }

        String newQuestionText = String.format("%s %s %s?", beginPrompt,
                medicationName, timeFrame);
        medicationQuestionText.setText(newQuestionText);
        int index;
        if(questionIndex < shortTermMedications.size()){
            index = Constants.getIndexFromShortTermMedicationsList(shortTermMedications.get(getQuestionIndex()));
        }else{
            int searchIndex = getQuestionIndex();
            String medication = longTermMedications.get(searchIndex);
            index = Constants.getIndexFromLongTermMedicationsList(medication);
            if(index >= 0){
                index += Constants.shortTermMedications.length;
            }
        }
        if(index < 0){
            Log.e("Medication Usage", "Unknown medication");
            return;
        }
        positiveAnswer.setChecked(medicationAnswers.get(index) == 1);
        negativeAnswer.setChecked(medicationAnswers.get(index) == 0);
    }//end resetQuestionText

    private Class getNextActivity() {
        Class nextActivity;

        if(!asthmaApp.activityChain.isEmpty() &&
                asthmaApp.activityChainIndex + 1 < asthmaApp.activityChain.size()){
            asthmaApp.activityChainIndex++;
            nextActivity = asthmaApp.activityChain.get(asthmaApp.activityChainIndex);
        }else {
            nextActivity = ConnectionNodeOptions.class;
        }

        return nextActivity;
    }

    /**
     * Timestamps the user's answers and saves them to the database.
     */
    private void submit(){
        // Create a timestamp for submission
        String timestamp = new Timestamp(new Date().getTime()).toString();
        //addRowToMedicationAnswersTable(medicationAnswers, timestamp, type, asthmaApp.userLoggedIn);

        Intent i;
        Class nextActivity = getNextActivity();
        if(nextActivity == ConnectionNodeOptions.class){
            // Save responses and go to Node
            String[] types = {"CURRENT", "DAY", "NIGHT"};
            Bundle extras = getIntent().getExtras();
            for(String t : types){
                ArrayList<Integer> tempAnswers = extras.getIntegerArrayList(t + "_medication");
                String tempTimestamp = extras.getString(t + "_timestamp");
                if(tempAnswers != null) {
                    addRowToMedicationAnswersTable(tempAnswers, tempTimestamp, t,
                            asthmaApp.userLoggedIn);
                }
            }
            addRowToMedicationAnswersTable(medicationAnswers, timestamp, type, asthmaApp.userLoggedIn);
        }
        i = new Intent(MedicationUsage.this, nextActivity);
        i.putIntegerArrayListExtra(type + "_medication", medicationAnswers);
        i.putExtra(type + "_timestamp", timestamp);
        startActivity(i);
        finish();
    }//end submit

    /**
     * Callback method that listens for and handles button clicks in this activity.
     * @param view The view that was clicked.
     */
    public void onClick(View view){
        int index;
        switch(view.getId()){
            case R.id.medication_next:
                // Save the current answer
                if(questionIndex < shortTermMedications.size()){
                    index = Constants.getIndexFromShortTermMedicationsList(shortTermMedications.get(getQuestionIndex()));
                }else{
                    index = Constants.getIndexFromLongTermMedicationsList(longTermMedications.get(getQuestionIndex()));
                    if(index >= 0){
                        index += Constants.shortTermMedications.length;
                    }
                }
                if(index < 0){
                    Log.e("Medication Usage", "Unknown medication");
                    return;
                }
                medicationAnswers.set(index, positiveAnswer.isChecked() ? 1 : 0);
                setNextQuestion();
                break;
            case R.id.medication_prev:
                if(questionIndex < shortTermMedications.size()){
                    index = Constants.getIndexFromShortTermMedicationsList(shortTermMedications.get(getQuestionIndex()));
                }else{
                    index = Constants.getIndexFromLongTermMedicationsList(longTermMedications.get(getQuestionIndex()));
                    if(index >= 0){
                        index += Constants.shortTermMedications.length;
                    }
                }
                if(index < 0){
                    Log.e("Medication Usage", "Unknown medication");
                    return;
                }
                medicationAnswers.set(index, positiveAnswer.isChecked() ? 1 : 0);
                setPrevQuestion();
                break;
            default:
                Log.e("MEDICATION_USAGE", "Invalid button pressed. ID=" + view.getId());
                break;
        }//end switch
    }//end onClick

    private int getQuestionIndex(){
        if(questionIndex >= shortTermMedications.size()){
            return questionIndex - shortTermMedications.size();
        }else{
            return questionIndex;
        }
    }
}//end class
