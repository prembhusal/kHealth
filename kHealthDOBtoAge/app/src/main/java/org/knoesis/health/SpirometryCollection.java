package org.knoesis.health;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.knoesis.health.constants.Constants;

/**
 * Activity to collect spirometry data from patient.
 * Created by Quintin on 4/25/2017.
 */

public class SpirometryCollection extends KHealthAsthmaParentActivity {

    private final double FEV_MIN = 0.0;
    private final double FEV_MAX = 1000.0;
    private double fevValue = 0.0;

    private Button mPrevButton;
    private Button mNextButton;
    private EditText mEditSpirometry;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirometry_collection);

        //mPrevButton = (Button) findViewById(R.id.spirometry_previous);
        mNextButton = (Button) findViewById(R.id.spirometry_next);
        mNextButton.setEnabled(false);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRowtoSensorVlauesTable(Constants.sensors.spirometry.ordinal(), fevValue);
                Intent intent = new Intent(SpirometryCollection.this, PopulationSignals.class);
                startActivity(intent);
                finish();
            }
        });


        mEditSpirometry = (EditText) findViewById(R.id.editText_fev);
        mEditSpirometry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    double value = Double.parseDouble(s.toString());
                    mNextButton.setEnabled((value >= FEV_MIN) && (value <= FEV_MAX));
                    fevValue = value;
                } else {
                    mNextButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
