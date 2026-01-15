package com.example.listycity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private ListView cityList;
    private Button addBtn, deleteBtn, confirmBtn;
    private EditText cityInput;
    private LinearLayout addPanel;

    private ArrayList<String> dataList;
    private ArrayAdapter<String> cityAdapter;

    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        cityList = findViewById(R.id.city_list);
        addBtn = findViewById(R.id.btn_add_city);
        deleteBtn = findViewById(R.id.btn_delete_city);
        confirmBtn = findViewById(R.id.btn_confirm);
        cityInput = findViewById(R.id.et_city_name);
        addPanel = findViewById(R.id.add_panel);


        String[] cities = {"Edmonton", "Montréal"};
        dataList = new ArrayList<>();
        dataList.addAll(Arrays.asList(cities));


        cityAdapter = new ArrayAdapter<>(this, R.layout.row_city, dataList);
        cityList.setAdapter(cityAdapter);


        cityList.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            cityList.setItemChecked(position, true);
        });


        addBtn.setOnClickListener(v -> {
            addPanel.setVisibility(View.VISIBLE);
            cityInput.requestFocus();
            showKeyboard(cityInput);
        });


        confirmBtn.setOnClickListener(v -> {
            String newCity = cityInput.getText().toString().trim();

            if (TextUtils.isEmpty(newCity)) {
                Toast.makeText(this, "Enter a city name first.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.add(newCity);
            cityAdapter.notifyDataSetChanged();

            cityInput.setText("");
            addPanel.setVisibility(View.GONE);
            hideKeyboard(cityInput);


            selectedPosition = -1;
            cityList.clearChoices();
        });


        deleteBtn.setOnClickListener(v -> {
            if (selectedPosition < 0 || selectedPosition >= dataList.size()) {
                Toast.makeText(this, "Tap a city to select it first.", Toast.LENGTH_SHORT).show();
                return;
            }

            dataList.remove(selectedPosition);
            cityAdapter.notifyDataSetChanged();

            selectedPosition = -1;
            cityList.clearChoices();
        });
    }

    private void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
