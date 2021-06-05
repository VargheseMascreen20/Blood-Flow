package com.var.bloodflow.MainFragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.var.bloodflow.DonorsList;
import com.var.bloodflow.R;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class HomeFragment extends Fragment {
    int key = 0;
    String bldGrp;

    public HomeFragment() {

    }

    Button maps, find;
    TextView txt, blood_group;
    int PLACE_PICKER_REQUEST = 1;
    Spinner spinner;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txt = view.findViewById(R.id.txt);
        blood_group = view.findViewById(R.id.blood_group);

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
                String start = txt.getText().toString().trim();
                String stop = blood_group.getText().toString().trim();
                Intent intent = new Intent(getActivity(), DonorsList.class);
                    startActivity(intent);
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

}
