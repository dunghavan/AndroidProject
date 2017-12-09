package com.example.dung.demo_recyclerview;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.model_for_map.Route;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback, Route.onUpdateListener {

    //private Route route; // Used for send to interface of CartActivity
    private GoogleMap mMap;
    Route route;
    private NhaHang nhaHang;
    APIService apiService;
    // UI
    TextView tv_diaChiCuaHang, tv_diaChiCuaBan, tv_khoangCach, tv_thoiGian;
    static TextView tv_date, tv_time;
    DialogFragment dateFragment;
    DialogFragment timeFragment;
    RadioButton radio_btn_select_time, radio_btn_earliest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        tv_diaChiCuaHang = (TextView)findViewById(R.id.tv_from);
        tv_diaChiCuaBan = (TextView)findViewById(R.id.tv_to);
        tv_khoangCach = (TextView)findViewById(R.id.tv_distance);
        tv_thoiGian = (TextView)findViewById(R.id.tv_duration);

        tv_date = (TextView)findViewById(R.id.tv_date_in_map);
        tv_time = (TextView)findViewById(R.id.tv_time_in_map);

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateFragment = new DatePickerFragment();
                dateFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeFragment = new TimePickerFragment();
                timeFragment.show(getSupportFragmentManager(), "timePicker");
            }
        });

        radio_btn_select_time = findViewById(R.id.radio_select_time);
        radio_btn_earliest = findViewById(R.id.radio_earliest);

        radio_btn_select_time.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radio_btn_select_time.setChecked(b);
                    radio_btn_earliest.setChecked(!b);
                }
            }
        });
        radio_btn_earliest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    radio_btn_select_time.setChecked(!b);
                    radio_btn_earliest.setChecked(b);
                }
            }
        });

        //Get thong tin nha hang
        String maNhaHang = Cart.getCartContent().get(0).getMaNhaHang();
        apiService = ApiUtils.getAPIService();
        apiService.getNhaHangById(maNhaHang).enqueue(new Callback<NhaHang>() {
            @Override
            public void onResponse(Call<NhaHang> call, Response<NhaHang> response) {
                try{
                    nhaHang = response.body();
                    tv_diaChiCuaHang.setText("From: " + nhaHang.getDiaChi());
                }
                catch (Exception e){
                    tv_diaChiCuaHang.setText("From: null");
                    Log.d("Error in MapActivity", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<NhaHang> call, Throwable t) {
                Log.d("Error in MapActivity", "Failure");
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        public DatePickerFragment(){}
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            if(month == 12)
                month = 1;
            else
                month++;
            tv_date.setText("Ngày: " + day + "/" + month + "/" + year);
        }
    }
    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            tv_time.setText("Giờ: " + hourOfDay + ":" + minute);
        }
    }
    public void onUpdateMapUI(String distance, String duration){
        tv_khoangCach.setText("Khoảng cách: " + distance);
        tv_thoiGian.setText("Thời gian: " + duration);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(10.866046, 106.803453);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        //LatLng golf = new LatLng(10.862561, 106.780504);
        LatLng golf = new LatLng(10.840808, 106.745635);
        route = new Route();
        route.drawRoute(mMap, this, golf, sydney, Route.LANGUAGE_ENGLISH);
        route.setOnUpdateUIListener(this);
    }
}
