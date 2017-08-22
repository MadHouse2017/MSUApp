package com.madhouse.msu.v10.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.madhouse.msu.v10.R;
import com.madhouse.msu.v10.applicationUtils.ApplicationUtil;
import com.madhouse.msu.v10.applicationUtils.UserPreferences;
import com.madhouse.msu.v10.bean.Member_Location_Bean;
import com.madhouse.msu.v10.bean.NSDC_Center_Bean;
import com.madhouse.msu.v10.bean.bean_markerDetail;
import com.madhouse.msu.v10.exceptions.ApplicationException;
import com.madhouse.msu.v10.proxies.members_location_proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.NETWORK_PROVIDER;

/**
 * Created by Kartikay on 8/6/17.
 */

public class Members_location1 extends Fragment implements android.location.LocationListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMarkerClickListener {

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    static Marker marker1;
    static LocationManager lm;
    static TextView fm3;
    static ArrayList<bean_markerDetail> markers;
    private View view;

    MapView mMapView;
    private GoogleMap googleMap;
    private Location location;
    private ArrayList<Member_Location_Bean> membersLocations;
    private ProgressDialog progressDialog;
    private GoogleMap map;
    private UserPreferences userPreferences;
    private Criteria criteria;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.members_location, container, false);
        userPreferences = UserPreferences.getInstance(getActivity());

        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately




        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        markers = new ArrayList<>();

        //MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapView.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity()).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        //Now lets connect to the API
        mGoogleApiClient.connect();
    }

    @Override
    public void onPause() {
        super.onPause();


        //Disconnect from API onPause()
        if (mGoogleApiClient.isConnected()) {
            // LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, (com.google.android.gms.location.LocationListener) getActivity() );
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        try {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (location == null) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (com.google.android.gms.location.LocationListener) this);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

            }
        }
        catch (Exception er){
            er.printStackTrace();
        }
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
            /*
             * Google Play services can resolve some errors it detects.
             * If the error has a resolution, try sending an Intent to
             * start a Google Play services activity that can resolve
             * error.
             */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(getActivity(), CONNECTION_FAILURE_RESOLUTION_REQUEST);
                    /*
                     * Thrown if Google Play services canceled the original
                     * PendingIntent
                     */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
                /*
                 * If no resolution is available, display a dialog to the
                 * user with the error.
                 */
        }
    }


    @Override
    public void onMapReady(GoogleMap mapx) {

        this.map = mapx;

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setBuildingsEnabled(true);
        map.setMyLocationEnabled(true);



        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(NETWORK_PROVIDER, 1000, 0, Members_location1.this);

        criteria = new Criteria();
        boolean isNetworkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }
        else{
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
        }


        location = lm.getLastKnownLocation(lm.getBestProvider(criteria, true));



        if (location != null) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();

            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(12)
                    .build();                   // Creates a CameraPosition from the builder
            // Sets the zoom
                   /* .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees*/
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        else{

            lm.requestLocationUpdates(lm.getBestProvider(criteria, true), 1000, 0, this);

            CameraUpdate center=
                    CameraUpdateFactory.newLatLng(new LatLng(20.5937,
                            78.9629));
            CameraUpdate zoom=CameraUpdateFactory.zoomTo(4);

            map.moveCamera(center);
            map.animateCamera(zoom);

//            map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));
//
//            CameraPosition cameraPosition = new CameraPosition.Builder()
//                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
//                    .zoom(15)
//                    .build();                   // Creates a CameraPosition from the builder
//            // Sets the zoom
//                   /* .bearing(90)                // Sets the orientation of the camera to east
//                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees*/
//            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        }

        // call marker array
        AsyncCallWS task = new AsyncCallWS();
        task.execute(map);


        // Adding and showing marker while touching the GoogleMap
      /*  map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                // Clears any existing markers from the GoogleMap
                map.clear();

                // Creating an instance of MarkerOptions to set position
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting position on the MarkerOptions
                markerOptions.position(arg0);

                // Animating to the currently touched position
                map.animateCamera(CameraUpdateFactory.newLatLng(arg0));

                // Adding marker on the GoogleMap
                Marker marker1 =   map.addMarker( new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(markers.get(0).getLat()),Double.parseDouble(markers.get(0).getLong())))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker_icon)));


                // Showing InfoWindow on the GoogleMap
                marker1.showInfoWindow();

            }
                    });*/

    }

    private void animateCameraToMarker(LatLng latLng, boolean isAnimate) {

        try {
            //etSource.setFocusable(false);
            //etSource.setFocusableInTouchMode(false);
            CameraUpdate cameraUpdate = null;

            cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 12);
            if (cameraUpdate != null && map != null) {
                if (isAnimate)
                    map.animateCamera(cameraUpdate);
                else
                    map.moveCamera(cameraUpdate);
            }
            //etSource.setFocusable(true);
            //etSource.setFocusableInTouchMode(true);
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        try {
            LatLng currentcamera = new LatLng(location.getLatitude(), location.getLongitude());
            animateCameraToMarker(currentcamera, true);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            lm.removeUpdates(this);
        }
        catch (Exception er){
            er.getMessage();
        }

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        /*if(marker.equals(marker1)){
            marker1.showInfoWindow();

        }*/

        return true;
    }

    public void setMarker(String a, String b, String c, String d, String e, String f){

        bean_markerDetail markerDetail = new bean_markerDetail();

        markerDetail.setAddress(f);
        markerDetail.setContact(b);
        markerDetail.setDesignation(c);
        markerDetail.setName(a);
        markerDetail.setLat(d);
        markerDetail.setLong(e);

        markers.add(markerDetail);



               /* for(int i=0; i<5; i++){

                    bean_markerDetail markerDetail = new bean_markerDetail();
                    markerDetail.setAddress("");
                    markerDetail.setContact("");
                    markerDetail.setDesignation("");
                    markerDetail.setName("");
                    markerDetail.setLat("");
                    markerDetail.setLong("");

                    markers.add(i,markerDetail);
                }
      */
    }


    private class AsyncCallWS extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {

            try {

//                Geocoder gcd = new Geocoder(getActivity(), Locale.getDefault());
//                List<Address> addresses = gcd.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                if (addresses.size() > 0)
//                {
//                    System.out.println(addresses.get(0).getLocality());
//                }
//                else
//                {
//                    // do your staff
//                }

                members_location_proxy memberLocationProxy = new members_location_proxy();

                if (location != null) {
                    memberLocationProxy.setStrLat(String.valueOf(location.getLatitude()));
                    memberLocationProxy.setStrLong(String.valueOf(location.getLongitude()));
                } else {
                    memberLocationProxy.setStrLong("27.0238");
                    memberLocationProxy.setStrLong("74.2179");

                }

                memberLocationProxy.setStrWorkHobby(userPreferences.getPREFERENCE_HOBBY());

                //validateUserProxie.setStrImeiNo(ApplicationUtil.getInstance().getImeiNo(LoginScreen.this));

                //loginStatus = WebService.invokeLoginWS(empId,empPassword, "authenticateUser");
                if (ApplicationUtil.getInstance().checkInternetConnection(getActivity())) {

                    membersLocations = ApplicationUtil.getInstance().getWebservice().getMemberLocation(memberLocationProxy, getActivity());
                }
            }
            catch (ApplicationException e) {
                e.printStackTrace();
            }

            //responseDto = ApplicationUtilTest.getInstance().getWebservice().gcmIdReg(regiUploadDto);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Getting Centers..");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            //Make Progress Bar invisible
            //loginStatus = inputEmpid.getText().toString();
            try{
                progressDialog.dismiss();

                //Error status is false

                if (membersLocations.size()>0) {
                    //Based on Boolean value returned from WebService


//                    for(int i=0;i<nsdcCenters.size();i++){
//
//                        setMarker(nsdcCenters.get(i).getNsdc_name(), nsdcCenters.get(i).getNsdc_contact_no(), "Partner", nsdcCenters.get(i).getNsdc_lat(), nsdcCenters.get(i).getNsdc_long(), nsdcCenters.get(i).getNsdc_address());
//
//
//
//                    }

                    for(int i=0;i<membersLocations.size();i++){

                        MarkerOptions mo = new MarkerOptions();

                        mo.position(new LatLng(Double.parseDouble(membersLocations.get(i).getLat()), Double.parseDouble(membersLocations.get(i).getLongg())));
                        mo.title(membersLocations.get(i).getName()+ "\n" + membersLocations.get(i).getDistance()+" KM Away");
                        mo.snippet(membersLocations.get(i).getMobileNo() + "\n" + "Status: "+membersLocations.get(i).getMemberStatus() + "\n" + "Address: "+membersLocations.get(i).getAddress());
                        mo.icon(BitmapDescriptorFactory.fromResource(R.mipmap.member_marker));

                        map.addMarker(mo);
                    }



                    map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                        // Use default InfoWindow frame
                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }

                        // Defines the contents of the InfoWindow
                        @Override
                        public View getInfoContents(Marker arg0) {

                            // Getting view from the layout file info_window_layout
                            View v = getActivity().getLayoutInflater().inflate(R.layout.marker_view, null);

                            // Getting the position from the marker
                            //LatLng latLng = arg0.getPosition();

                            // Getting reference to the TextView to set latitude
                            TextView tvname = (TextView) v.findViewById(R.id.tv_name);
                            TextView tvdesig = (TextView) v.findViewById(R.id.tv_designation);
                            tvdesig.setVisibility(View.GONE);
                            TextView tvmobile = (TextView) v.findViewById(R.id.tv_mobile);
                            TextView tvaddress = (TextView) v.findViewById(R.id.tv_address);

                            TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);
                            tvLat.setVisibility(View.GONE);
                            // Getting reference to the TextView to set longitude
                            TextView tvLng = (TextView) v.findViewById(R.id.tv_long);
                            tvLng.setVisibility(View.GONE);

                            tvname.setText("Name :" + arg0.getTitle());
                            tvmobile.setText(arg0.getSnippet().substring(0,10));
                            tvaddress.setText(arg0.getSnippet().substring(10));

                            // Returning the view containing InfoWindow contents
                            return v;

                        }
                    });



                }



                else{

//                    Snackbar snackbar = Snackbar
//                            .make(coordinatorLayout, "Server Not Responding!", Snackbar.LENGTH_LONG)
//                            .setAction("RETRY", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    sendLoginDataToWeb();
//                                }
//                            });
//
//                    // Changing message text color
//                    snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
//
//                    // Changing action button text color
//                    View sbView = snackbar.getView();
//                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                    textView.setTextColor(getResources().getColor(R.color.colorAccent));
//                    snackbar.show();




                    //CSnackBar.getInstance().showSnackBarError(findViewById(R.id.mainLayout),"No Internet connection available",m_Context);
                    //  Snackbar.make(coordinatorLayout, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();//*
                    //  Toast.makeText(LoginScreen.this,"Server Not Responding | Please Try Again", Toast.LENGTH_LONG).show();
                }
                //Re-initialize Error Status to False


            }
            catch (Exception er){

                er.printStackTrace();

//                Snackbar snackbar = Snackbar
//                        .make(coordinatorLayout, "Some Error Occured!", Snackbar.LENGTH_LONG)
//                        .setAction("RETRY", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                sendLoginDataToWeb();
//                            }
//                        });
//
//                // Changing message text color
//                snackbar.setActionTextColor(getResources().getColor(R.color.colorPrimary));
//
//                // Changing action button text color
//                View sbView = snackbar.getView();
//                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
//                textView.setTextColor(getResources().getColor(R.color.colorAccent));
//                snackbar.show();
//
//                er.printStackTrace();
            }
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }
    }



}



