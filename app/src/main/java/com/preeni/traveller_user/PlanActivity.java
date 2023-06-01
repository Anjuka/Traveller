package com.preeni.traveller_user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.preeni.traveller_user.adapter.PlansAdapter;
import com.preeni.traveller_user.model.PlanDataList;

import java.util.ArrayList;
import java.util.Map;

public class PlanActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView iv_menu;
    private GridView gv_plans;

    private FirebaseFirestore firebaseFirestore;
    private ProgressDialog progressDialog;

    private ArrayList<PlanDataList> planDataLists = new ArrayList<>();
    private ArrayList<PlanDataList> planDataFilterList = new ArrayList<>();
    private ArrayList<Map<String, String>> plansDataMapList = new ArrayList<>();
    private PlansAdapter plansAdapter;

    private String selected_days ="";
    private String selected_vehicle ="";
    private String selected_people ="";
    private String selected_budget ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);

        iv_menu = findViewById(R.id.iv_menu);
        gv_plans = findViewById(R.id.gv_plans);

        progressDialog = new ProgressDialog(this);

        firebaseFirestore = FirebaseFirestore.getInstance();

        iv_menu.setOnClickListener(this);

        selected_days = getIntent().getStringExtra("selected_days");
        selected_vehicle = getIntent().getStringExtra("selected_vehicle");
        selected_people = getIntent().getStringExtra("selected_people");
        selected_budget = getIntent().getStringExtra("selected_budget");

        progressDialog.setMessage("Loading...");
        progressDialog.show();
        DocumentReference documentReferencePlans = firebaseFirestore.collection("plans").document("admin_plans");
        documentReferencePlans.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                plansDataMapList = (ArrayList<Map<String, String>>) (documentSnapshot.get("planDataLists"));
                if (plansDataMapList != null) {
                    for (int x = 0; x < plansDataMapList.size(); x++) {
                        String description = plansDataMapList.get(x).get("description");
                        String duration = plansDataMapList.get(x).get("duration");
                        String plan_code = plansDataMapList.get(x).get("plane_code");
                        String rating = plansDataMapList.get(x).get("rating");
                        String routing = plansDataMapList.get(x).get("routing");

                        PlanDataList planDataList = new PlanDataList(plan_code, duration, routing, description, rating);
                        planDataLists.add(planDataList);
                    }

                    planDataFilterList.clear();

                    for (PlanDataList planDataList : planDataLists){
                        if (planDataList.getDuration().equals(selected_days)){
                            planDataFilterList.add(planDataList);
                        }
                    }

                    plansAdapter = new PlansAdapter(PlanActivity.this, planDataFilterList);
                    gv_plans.setAdapter(plansAdapter);
                    progressDialog.cancel();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id) {
            case R.id.iv_menu:
                Intent home = new Intent(getApplicationContext(), PlanQuestionActivity.class);
                startActivity(home);
                finish();
                overridePendingTransition(0, 0);
                break;
        }
    }
}