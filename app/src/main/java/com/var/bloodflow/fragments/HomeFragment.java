package com.var.bloodflow.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;
import com.var.bloodflow.ModelClasses.Users;
import com.var.bloodflow.R;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {
    int key = 0;
    String bldGrp;

    public HomeFragment() {

    }
    Button maps,find;
    TextView txt,blood_group;
    int PLACE_PICKER_REQUEST = 1;
    Spinner spinner;
    private WanderMateViewModel viewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txt = view.findViewById(R.id.txt);
        blood_group = view.findViewById(R.id.blood_group);

        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(WanderMateViewModel.class);
        viewModel.getAllStops().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> stopList) {
                addStops(stopList);
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(), R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String Item = adapterView.getItemAtPosition(i).toString();
                if (Item == "A+ve") {
                    key = 1;
                    bldGrp = "A-ve";
                } else if (Item == "B+ve") {
                    key = 1;
                    bldGrp = "B-ve";
                } else if (Item == "AB+ve") {
                    key = 1;
                    bldGrp = "AB+ve";
                } else if (Item == "AB-ve") {
                    key = 1;
                    bldGrp = "AB+ve";
                } else if (Item == "O+ve") {
                    key = 1;
                    bldGrp = "AB+ve";
                } else if (Item == "O-ve") {
                    key = 1;
                    bldGrp = "O-ve";
                } else {
                    key = 0;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                key = 0;
                Toast.makeText(getActivity(), "Choose Value", Toast.LENGTH_SHORT).show();
            }
        });

        maps = view.findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        find = view.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = editTxtFrom.getText().toString().trim();
                String stop = editTxtTo.getText().toString().trim();
                if(!stops.contains(start)) {
                    editTxtFrom.setError("Select a location from the drop down list");
                } else if(!stops.contains(stop)) {
                    editTxtTo.setError("Select a destination from the drop down list");
                } else {
                    closeKeyboard();
                    editTxtFrom.getText().clear();
                    editTxtTo.getText().clear();
                    editTxtFrom.clearFocus();
                    editTxtTo.clearFocus();
                    Intent intent = new Intent(getBaseContext(), FilteredServicesViewActivity.class);
                    intent.putExtra("Start", start);
                    intent.putExtra("Stop", stop);
                    startActivity(intent);
                }
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Place place = PlacePicker.getPlace(data, getActivity());
                StringBuilder stringBuilder = new StringBuilder();
                String latitude = String.valueOf(place.getLatLng().latitude);
                String longitude = String.valueOf(place.getLatLng().longitude);
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGITUDE :");
                stringBuilder.append(longitude);
                txt.setText(stringBuilder.toString());
            }
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();

    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String from = txt.getText().toString().trim();
            String to = blood_group.getText().toString().trim();
            find.setEnabled(!from.isEmpty() && !to.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void addStops(List<String> stopList) {
        stops = new ArrayList<>(stopList);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, stops);
        editTxtFrom.setAdapter(adapter);
        editTxtTo.setAdapter(adapter);
    }
}
