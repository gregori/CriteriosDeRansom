package tech.gregori.criteriosderansom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import tech.gregori.criteriosderansom.dal.PatientDAL;

public class AddPatient extends AppCompatActivity {

    private TextView txtScore;
    private TextView txtMortality;
    private TextView txtScoreLabel;
    private TextView txtMortalityLabel;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        txtScore = findViewById(R.id.txt_score);
        txtMortality = findViewById(R.id.txt_mortality_pct);
        txtScoreLabel = findViewById(R.id.txt_score_label);
        txtMortalityLabel = findViewById(R.id.txt_mortality_label);
        
        txtScore.setVisibility(View.INVISIBLE);
        txtScoreLabel.setVisibility(View.INVISIBLE);
        txtMortality.setVisibility(View.INVISIBLE);
        txtMortalityLabel.setVisibility(View.INVISIBLE);

        Button btnAdd = findViewById(R.id.btn_add);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatient();
            }
        });
        
    }

    private void addPatient() {
        CheckBox chkLitiasis = findViewById(R.id.chk_litiasis);
        EditText edtName = findViewById(R.id.txt_name_pct);
        EditText edtAge = findViewById(R.id.edt_age);
        EditText edtLeukocytes = findViewById(R.id.edt_leukocytes);
        EditText edtGlucose = findViewById(R.id.edt_glucose);
        EditText edtAst = findViewById(R.id.edt_ast);
        EditText edtLdh = findViewById(R.id.edt_ldh);

        boolean litiasis = chkLitiasis.isChecked();
        String name = edtName.getText().toString();
        int age = Integer.valueOf(edtAge.getText().toString());
        int leukocytes = Integer.valueOf(edtLeukocytes.getText().toString());
        double glucose = Double.valueOf(edtGlucose.getText().toString());
        int ast = Integer.valueOf(edtAst.getText().toString());
        int ldh = Integer.valueOf(edtLdh.getText().toString());

        Patient patient = new Patient(
                name, age, leukocytes, glucose, ast, ldh, litiasis
        );

        PatientDAL patientDAL = new PatientDAL(this);
        patientDAL.insert(patient);

        txtScore.setText(String.valueOf(patient.getScore()));
        txtMortality.setText(String.valueOf(patient.getMortality()) + " %");
        
        txtScore.setVisibility(View.VISIBLE);
        txtScoreLabel.setVisibility(View.VISIBLE);
        txtMortality.setVisibility(View.VISIBLE);
        txtMortalityLabel.setVisibility(View.VISIBLE);
        

    }
}
