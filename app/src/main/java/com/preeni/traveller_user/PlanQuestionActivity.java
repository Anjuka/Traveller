package com.preeni.traveller_user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PlanQuestionActivity extends AppCompatActivity {

    private Spinner sp_days;
    private Spinner sp_people;
    private Spinner sp_vehicle;
    private Spinner sp_budget;
    private Button btn_plan_generate;
    private ConstraintLayout cl_loading;

    List<String> days = new ArrayList<>();
    List<String> people = new ArrayList<>();
    List<String> vehicle = new ArrayList<>();
    List<String> budget = new ArrayList<>();

    private String selected_days ="";
    private String selected_people ="";
    private String selected_vehicle ="";
    private String selected_budget ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_question);

        sp_days = findViewById(R.id.sp_days);
        sp_people = findViewById(R.id.sp_people);
        sp_vehicle = findViewById(R.id.sp_vehicle);
        sp_budget = findViewById(R.id.sp_budget);
        btn_plan_generate = findViewById(R.id.btn_plan_generate);
        cl_loading = findViewById(R.id.cl_loading);

        days.add("1");
        days.add("2");
        days.add("3");
        days.add("4");
        days.add("5");
        days.add("5+");

        people.add("1");
        people.add("2");
        people.add("3");
        people.add("3+");

        vehicle.add("Train");
        vehicle.add("Bus");
        vehicle.add("Motor Bike");
        vehicle.add("Tuk");
        vehicle.add("Car");
        vehicle.add("Van");

        budget.add("50$ - 100$");
        budget.add("100$ - 150$");
        budget.add("150$ - 200$");
        budget.add("200$+");

        ArrayAdapter<String> arrayAdapterDays = new ArrayAdapter<String>(PlanQuestionActivity.this,
                R.layout.spinner_item_usermang, days);
        arrayAdapterDays.setDropDownViewResource(R.layout.spinner_item_usermang);
        sp_days.setAdapter(arrayAdapterDays);
        sp_days.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_days = days.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> arrayAdapterPeople = new ArrayAdapter<String>(PlanQuestionActivity.this,
                R.layout.spinner_item_usermang, people);
        arrayAdapterPeople.setDropDownViewResource(R.layout.spinner_item_usermang);
        sp_people.setAdapter(arrayAdapterPeople);
        sp_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_people = people.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> arrayAdapterVehicle = new ArrayAdapter<String>(PlanQuestionActivity.this,
                R.layout.spinner_item_usermang, vehicle);
        arrayAdapterPeople.setDropDownViewResource(R.layout.spinner_item_usermang);
        sp_vehicle.setAdapter(arrayAdapterVehicle);
        sp_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_vehicle = vehicle.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<String> arrayAdapterBudget = new ArrayAdapter<String>(PlanQuestionActivity.this,
                R.layout.spinner_item_usermang, budget);
        arrayAdapterPeople.setDropDownViewResource(R.layout.spinner_item_usermang);
        sp_budget.setAdapter(arrayAdapterBudget);
        sp_budget.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_budget = budget.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_plan_generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_plan_generate.setVisibility(View.INVISIBLE);
                cl_loading.setVisibility(View.VISIBLE);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(getApplicationContext(), PlanActivity.class);
                        intent.putExtra("selected_days", selected_days);
                        intent.putExtra("selected_vehicle", selected_vehicle);
                        intent.putExtra("selected_people", selected_people);
                        intent.putExtra("selected_budget", selected_budget);
                        startActivity(intent);
                        finish();
                    }
                }, 3000);

            }
        });
    }
}