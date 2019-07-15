package sg.edu.rp.c346.petboardingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText etName, etDay, etDate;
    Spinner spn;
    CheckBox cb;
    Button btnSend;
    Boolean name, day, date, vaccinated;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    String pet, dat;
    Timestamp datetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        name = false;
        day = false;
        pet = "";
        date = false;
        calendar = Calendar.getInstance();

        etName = findViewById(R.id.etName);
        etDate = findViewById(R.id.etDate);
        etDay = findViewById(R.id.etDay);
        spn = findViewById(R.id.spinner);
        cb = findViewById(R.id.checkBox);
        btnSend = findViewById(R.id.btnSend);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        dat = day + "/" + month + "/" + year;
                        timePicker();
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        pet = "Cat";
                        break;
                    case 1:
                        pet = "Dog";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().equals("")) {
                    etName.setError("Name required!");
                } else {
                    name = true;
                }
                if (etDay.getText().toString().equals("")) {
                    etDay.setError("Days required!");
                } else {
                    day = true;
                }
                if (etDate.getText().toString().equals("")) {
                    etDate.setError("Date required!");
                } else {
                    date = true;
                }
                if (cb.isChecked()) {
                    vaccinated = true;
                } else {
                    vaccinated = false;
                }

                Map<String, Object> pets = new HashMap<>();
                pets.put("name", etName.getText().toString());
                pets.put("numDays", etDay.getText().toString());
                pets.put("petType", pet);
                pets.put("boardDate", etDate);
                pets.put("vaccinated", vaccinated);

                db.collection("Pet").add(pets)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MainActivity.this, "Pet Added", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Pet failed to add", Toast.LENGTH_SHORT).show();

                            }
                        });
            }
        });
    }

    private void timePicker(){
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        timePickerDialog = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                hour = hour;
                minute = minute;
                etDate.setText(dat +" "+hour + ":" + minute);
            }
        }, hour, minute, false);
        timePickerDialog.show();
    }
}
