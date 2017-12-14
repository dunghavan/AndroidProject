package com.example.dung.demo_recyclerview;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dung.demo_recyclerview.model.MyDateTime;
import com.example.dung.demo_recyclerview.model.NhaHang;
import com.example.dung.demo_recyclerview.model_for_map.Route;
import com.example.dung.demo_recyclerview.retrofit.APIService;
import com.example.dung.demo_recyclerview.retrofit.ApiUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.location.places.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks,
        OnMapReadyCallback, Route.onUpdateListener, GoogleApiClient.OnConnectionFailedListener, LocationListener,
        MyPaymentActivity.OnSendSubmitListener {

    MapActivity mapActivity;
    MyPaymentActivity paymentActivity;
    //private Route route; // Used for send to interface of CartActivityprivate Location location;
    // Đối tượng tương tác với Google API
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    //LatLng sydney = new LatLng(10.860344, 106.761616);
    LatLng nhaHangLatLong;
    private GoogleApiClient gac;
    LocationRequest mLocationRequest;
    private GoogleMap mMap;
    LatLng userLatLong;
    Marker currLocationMarker;
    Geocoder geocoder;
    Route route;
    private NhaHang nhaHang;
    APIService apiService;

    // Informations for submit order
    String curDateTime;
    static MyDateTime deliveryDateTime;
    String submitAddress;
    String phoneNumber;
    String paymentType;
    Double total;
    String orderDetail;
    // UI
    TextView tv_diaChiCuaHang, tv_diaChiCuaBan, tv_khoangCach, tv_thoiGian;
    static TextView tv_date, tv_time;
    EditText editText_Phone;
    DialogFragment dateFragment;
    DialogFragment timeFragment;
    RadioButton radio_btn_select_time, radio_btn_earliest, radio_btn_pay, radio_btn_paypal;
    Button btn_Back, btn_SendOrder;

    Calendar c;
    int date;
    int month;
    int year;
    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mapActivity = this;
        paymentActivity = new MyPaymentActivity();
        paymentActivity.setOnSendSubmitListener(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(this);

        //for map:
        // kiểm tra play services
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        geocoder = new Geocoder(this, Locale.getDefault());

        tv_diaChiCuaHang = (TextView)findViewById(R.id.tv_from);
        tv_diaChiCuaBan = (TextView)findViewById(R.id.tv_to);
        tv_khoangCach = (TextView)findViewById(R.id.tv_distance);
        tv_thoiGian = (TextView)findViewById(R.id.tv_duration);
        btn_Back = (Button)findViewById(R.id.back_btn_in_map);
        btn_SendOrder = (Button)findViewById(R.id.send_btn_in_map);

        tv_date = (TextView)findViewById(R.id.tv_date_in_map);
        tv_time = (TextView)findViewById(R.id.tv_time_in_map);
        editText_Phone = (EditText)findViewById(R.id.editText_sdt_in_map);

        c = Calendar.getInstance();
        date = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH) + 1;
        year = c.get(Calendar.YEAR);
        tv_date.setText("Ngày: " + date + "/" + month + "/" + year);
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        tv_time.setText("Giờ: " + hour + ":" + minute);

        //Set current date time when send order:
        curDateTime = year + "-" + month + "-" + date + "T" + hour + ":" + minute + ":00";
        deliveryDateTime = new MyDateTime();
        deliveryDateTime.setDate(year + "-" + month + "-" + date);
        deliveryDateTime.setTime(hour + ":" + minute + ":00");
        submitAddress = "";
        phoneNumber = ""; //set later
        paymentType = ""; //set later

        apiService = ApiUtils.getAPIService();

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
        radio_btn_earliest.setChecked(true);

        radio_btn_pay = findViewById(R.id.radio_pay);
        radio_btn_paypal = findViewById(R.id.radio_paypal);
        radio_btn_pay.setChecked(true);

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

        btn_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_SendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radio_btn_pay.isChecked()){
                    sendSubmitToServer("HinhThucThanhToan1", "");
                }
                else{
                    Intent intent = new Intent(mapActivity, MyPaymentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putDouble("TOTAL", Cart.getTotal());
                    intent.putExtras(bundle);
                    mapActivity.startActivity(intent);
                }
            }
        });

        //Get thong tin nha hang
        String maNhaHang = Cart.getCartContent().get(0).getMaNhaHang();
        apiService.getNhaHangById(maNhaHang).enqueue(new Callback<NhaHang>() {
            @Override
            public void onResponse(Call<NhaHang> call, Response<NhaHang> response) {
                try{
                    nhaHang = response.body();
                    tv_diaChiCuaHang.setText("From: " + nhaHang.getDiaChi());
                    nhaHangLatLong = new LatLng(nhaHang.getViDo(), nhaHang.getKinhDo());
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
            month++;
            tv_date.setText("Ngày: " + day + "/" + month + "/" + year);
            deliveryDateTime.setDate(year + "-" + month + "-" + day);
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
            deliveryDateTime.setTime(hourOfDay + ":" + minute + ":00");
        }
    }
    public void onUpdateMapUI(String distance, String duration){
        tv_khoangCach.setText("Khoảng cách: " + distance);
        tv_thoiGian.setText("Thời gian: " + duration);
    }

    public void sendSubmitToServer(String _paymentType, String _payID){
        String _deliveryDateTime = deliveryDateTime.toString();
        if(radio_btn_earliest.isChecked()){
            _deliveryDateTime = curDateTime;
        }
        phoneNumber = editText_Phone.getText().toString();
        DecimalFormat df = new DecimalFormat("###");
        int tongTien = (int)Math.round(Cart.getTotal());
        apiService.submitOrder(LoginActivity.getID(), curDateTime, _deliveryDateTime,
                submitAddress, phoneNumber, _paymentType, _payID, tongTien, Cart.convertTo_CTDDH(),
                "false", "false").enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MyApplication.getCurrentContext(), "Send success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MyApplication.getCurrentContext(), "Send onFailure()", Toast.LENGTH_SHORT).show();
            }
        });
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
        mMap.addMarker(new MarkerOptions().position(nhaHangLatLong).title("Vị trí nhà hàng"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nhaHangLatLong));

    }

    /**
     * Tạo đối tượng google api client
     * */
    protected synchronized void buildGoogleApiClient() {
        if (gac == null) {
            gac = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API).build();

            gac.connect();
        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Kiểm tra quyền hạn
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(gac);

            if (location != null) {
                userLatLong = new LatLng(location.getLatitude(), location.getLongitude());

                Log.d("My Location", userLatLong.latitude + ", " + userLatLong.longitude);
                route = new Route();
                route.drawRoute(mMap, this, nhaHangLatLong, userLatLong, Route.LANGUAGE_ENGLISH);
                //Add Marker:
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(userLatLong);
                markerOptions.title("Vị trí của bạn");
                currLocationMarker = mMap.addMarker(markerOptions);
                //Update location:
                mLocationRequest = new LocationRequest();
                mLocationRequest.setInterval(60000); //5 seconds
                mLocationRequest.setFastestInterval(60000); //3 seconds
                mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
                LocationServices.FusedLocationApi.requestLocationUpdates(gac, mLocationRequest, this);

                route.setOnUpdateUIListener(this);

                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = geocoder.getFromLocation(userLatLong.latitude, userLatLong.longitude, 1);
                    Log.d("Address", addresses.toString());
                    android.location.Address address = addresses.get(0);
                    if (address != null) {
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++){
                            sb.append(address.getAddressLine(i) + "\n");
                        }

                        String newAddress = address.getFeatureName() + "-"
                                + address.getSubLocality() + "-" + address.getSubAdminArea() + "-"
                                + address.getAdminArea();
                        tv_diaChiCuaBan.setText("Địa chỉ của bạn: " + newAddress);
                        submitAddress = newAddress;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                // Hiển thị
                //tvLocation.setText(latitude + ", " + longitude);
            } else {
                //tvLocation.setText("(Không thể hiển thị vị trí. " + "Bạn đã kích hoạt location trên thiết bị chưa?)");
            }
        }
    }
    /**
     * Phương thức kiểm chứng google play services trên thiết bị
     * */
    private boolean checkPlayServices() {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(this);
        if(result != ConnectionResult.SUCCESS) {
            if(googleAPI.isUserResolvableError(result)) {
                googleAPI.getErrorDialog(this, result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }

            return false;
        }

        return true;
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        gac.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Toast.makeText(this, "Lỗi kết nối: " + result.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLocationChanged(Location location) {

        //place marker at current position
        //mGoogleMap.clear();
        if (currLocationMarker != null) {
            currLocationMarker.remove();
        }
        userLatLong = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(userLatLong);
        markerOptions.title("Vị trí của bạn");
        //markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        currLocationMarker = mMap.addMarker(markerOptions);

        //zoom to current position:
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLong, 13));

        //If you only need one location, unregister the listener
        //LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            final MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.fragment_map);
            mapFragment.getMapAsync(this);
            try {
                mMap.setMyLocationEnabled(true);
            } catch (SecurityException e) {
                Log.d("SecurityException", "Not have permission!");
            }
        }
        // Check if we were successful in obtaining the map.
        if (mMap != null) {
            FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            try{
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            Location mLastKnownLocation = (Location)task.getResult();
                            userLatLong = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                   // new LatLng(mLastKnownLocation.getLatitude(),
                                            //mLastKnownLocation.getLongitude()), 13.0f));
                        } else {
                            Log.d("myLocation", "Current location is null. Using defaults.");
                            Log.e("myLocation", "Exception: %s", task.getException());
                           // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                           // mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
            catch (SecurityException e){
                Log.d("SecurityException", "Not have permission!");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.setCurrentContext(this);
    }
}
