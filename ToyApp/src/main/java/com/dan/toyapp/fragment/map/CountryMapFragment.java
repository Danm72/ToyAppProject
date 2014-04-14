package com.dan.toyapp.fragment.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dan.toyapp.R;
import com.dan.toyapp.entity.City;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * A fragment representing a single People detail screen.
 * This fragment is either contained in a {@link com.dan.toyapp.InitialListActivity}
 * in two-pane mode (on tablets) or a {@link com.dan.toyapp.MapDockDetailActivity}
 * on handsets.
 */
public class CountryMapFragment extends SupportMapFragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String TAG = "CountryMapFrag";

    GoogleMap mMap;
    String params;
    LatLng mCountryPosition;
    Context context;

    static View rootView;


    /**
     * The content this fragment is presenting.
     */
    private City mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountryMapFragment(String params, Context context) {
        this.params = params;
        this.context = context;
    }

    public CountryMapFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!= null){
            params = savedInstanceState.getString("PARAM");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("PARAM", params);

        Log.d(TAG, "SAVING");
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        if (rootView != null) {
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if (parent != null)
//                parent.removeView(rootView);
//        }
//        final LatLng europe = new LatLng(-46.0000, 7.0000);
//
//        try {
//            rootView = inflater.inflate(R.layout.fragment_person_detail, container, false);
//
//            initialiseMap(mCountryPosition);
//        } catch (InflateException e) {
//        /* map is already there, just return view as it is */
//            Log.d(TAG, "Map is already there");
//            Log.d(TAG, mItem.toString());
//            initialiseMap(mCountryPosition);
//            Log.d(TAG, "Map existed, trying to edit");
//
//
//        }
//
//        // Show the dummy content as text in a TextView.
////        if (mItem != null) {
////            ((TextView) rootView.findViewById(R.id.city_cityname)).setText(mItem.getCity());
////            ((TextView) rootView.findViewById(R.id.city_id)).setText(mItem.getId());
////            ((TextView) rootView.findViewById(R.id.city_region)).setText(mItem.getRegion());
////
////
////        }
//
//        return rootView;
//    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View v = super.onCreateView(arg0, arg1, arg2);
        initMap();
        return v;
    }

    private void initMap() {
        UiSettings settings = getMap().getUiSettings();
        settings.setAllGesturesEnabled(false);
        settings.setMyLocationButtonEnabled(false);

        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.ENGLISH);
        try {
            if (params.length() > 0) {
                List<Address> addressList = geocoder.getFromLocationName(params, 1);
                Address address = addressList.get(0);
                mCountryPosition = new LatLng(address.getLatitude(), address.getLongitude());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        initialiseMap(mCountryPosition);

    }

//    private void InitialiseMap(final LatLng europe) {
//        mMap = ((SupportMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//
//        if (mMap != null) {
//            final LatLngBounds.Builder builder = new LatLngBounds.Builder();
//
//            LatLng latlng = new LatLng(mItem.getLatitude(), mItem.getLongitude());
//            builder.include(latlng);
//            final LatLngBounds bounds = builder.build();
//            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
//
//                @Override
//                public void onCameraChange(CameraPosition arg0) {
//                    // Move camera.
//                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(europe, 5));
//
//
//                    CameraPosition cameraPosition = new CameraPosition.Builder()
//                            .target(bounds.northeast)      // Sets the center of the map to Mountain View
//                            .zoom(10)                   // Sets the zoom
//                            .bearing(90)                // Sets the orientation of the camera to east
//                            .tilt(30)                   // Sets the tilt of the camera to 30 degrees
//                            .build();                   // Creates a CameraPosition from the builder
//                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//
//                    // Remove listener to prevent position reset on camera move.
//                    mMap.setOnCameraChangeListener(null);
//                }
//
//            });
//        }
//    }

    private void initialiseMap(final LatLng mPosition) {
        final GoogleMap mMap = getMap();

        if (mMap != null) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(mPosition)      // Sets the center of the map to Mountain View
                    .zoom(0)                   // Sets the zoom
                    .bearing(0)                // Sets the orientation of the camera to east
                    .tilt(0)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder

            CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

            mMap.animateCamera(update, 2500, new GoogleMap.CancelableCallback() {
                @Override
                public void onFinish() {

                }

                @Override
                public void onCancel() {

                }
            });

            mMap.addMarker(new MarkerOptions()
                    .position(mPosition)
                    .title(params.split(",")[0]));

        }
    }
}
