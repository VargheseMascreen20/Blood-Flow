package com.var.bloodflow.MainFragments;

import android.graphics.Bitmap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.var.bloodflow.DonorsList;
import com.var.bloodflow.Map;
import com.var.bloodflow.R;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.var.bloodflow.webViewController;

public class HomeFragment extends Fragment {

    int key = 0;

    String bldGrp;
    Button maps, find, banks;
    TextView txt, blood_group;

    int PLACE_PICKER_REQUEST = 1;

    Spinner spinner;
    String bloodgroup;
    EditText city;
    WebView mywebView;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        blood_group = view.findViewById(R.id.blood_group);
        city = view.findViewById(R.id.city);
        txt = (TextView) view.findViewById(R.id.txt);
        mywebView = (WebView) view.findViewById(R.id.webview);


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

                bloodgroup = Item;

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                key = 0;
                Toast.makeText(getActivity(), "Choose Value", Toast.LENGTH_SHORT).show();
            }
        });


        find = view.findViewById(R.id.find);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sCity = city.getText().toString().trim();
//                String start = txt.getText().toString().trim();
//                String stop = blood_group.getText().toString().trim();
//                txt.setText(sCity);

                Intent intent = new Intent(getActivity(), DonorsList.class);
                intent.putExtra("blood group", bloodgroup);
                intent.putExtra("City", sCity);
                startActivity(intent);
            }

        });

        maps = view.findViewById(R.id.maps);
        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int flag = 0;
//                Intent i = new Intent(getActivity(), Map.class);
//                i.putExtra("FLAG",flag);
//                startActivity(i);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(getActivity()), PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
//
            }
        });
        banks = view.findViewById(R.id.banks);
        banks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 1;
                Intent i = new Intent(getActivity(), Map.class);
                i.putExtra("FLAG", flag);
                startActivity(i);
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
                stringBuilder.append("YOUR LOCATION\n");
                stringBuilder.append("LATITUDE :");
                stringBuilder.append(latitude);
                stringBuilder.append("\n");
                stringBuilder.append("LONGITUDE :");
                stringBuilder.append(longitude);
                txt.setText(stringBuilder.toString());
//                mywebView.loadUrl("https://www.google.com/search?tbs=lf:1,lf_ui:2&tbm=lcl&sxsrf=ALeKk029u99ZT1noBW6eau9WZbWAej4ugA:1623167753673&q=blood+donations+near+me&rflfq=1&num=10&sa=X&ved=2ahUKEwiJ7fiKs4jxAhWBXisKHXgkAn4QjGp6BAgQEEE&biw=1536&bih=758&dpr=1.25#rlfi=hd:;si:;mv:[[10.862584026176384,78.25198313671876],[9.234563353411698,75.18679758984376],null,[10.049598423883234,76.71939036328126],9]");
//                mywebView.setWebViewClient(new webViewController());
                Intent i = new Intent(getActivity(), Map.class);
                int flag = 0;
                i.putExtra("FLAG", flag);
                i.putExtra("Lat", latitude);
                i.putExtra("Lontd", longitude);
                startActivity(i);
            }
        }
    }

}
