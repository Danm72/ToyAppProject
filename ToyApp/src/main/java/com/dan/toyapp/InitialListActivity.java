package com.dan.toyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;

import com.dan.toyapp.entity.City;
import com.dan.toyapp.fragment.CitiesListFragment;
import com.dan.toyapp.fragment.map.CityMapFragment;
import com.dan.toyapp.fragment.CountriesListFragment;
import com.dan.toyapp.fragment.map.CountryMapFragment;
import com.dan.toyapp.fragment.PeopleListFragment;
import com.google.android.gms.maps.model.LatLng;


import static com.dan.toyapp.constants.Constants.CITIESLISTFRAGMENT;
import static com.dan.toyapp.constants.Constants.COUNTRIESLISTFRAG;
import static com.dan.toyapp.constants.Constants.PEOPLELISTFRAGMENT;


/**
 * An activity representing a list of Person. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link } representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link com.dan.toyapp.fragment.CountriesListFragment} and the item details
 * (if present) is a {@link com.dan.toyapp.fragment.map.CityMapFragment}.
 * <p/>
 * This activity also implements the required
 * {@link com.dan.toyapp.fragment.CountriesListFragment.Callbacks} taskinterfaces
 * to listen for item selections.
 */
public class InitialListActivity extends ActionBarActivity
        implements CountriesListFragment.Callbacks, CitiesListFragment.Callbacks, PeopleListFragment.Callbacks {
    private static String TAG = "InitialActivity";

    FragmentManager fragmentManager = getSupportFragmentManager();

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_list_frame);

        if (savedInstanceState == null) {


            Fragment countryList = new CountriesListFragment();
//            countryList.setArguments(arguments);
            countryList.setHasOptionsMenu(true);

            final FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.list_frame_layout, countryList, "CountriesFrag");
            transaction.commit();

            if (findViewById(R.id.map_detail_container) != null) {
                // The detail container view will be present only in the
                // large-screen layouts (res/values-large and
                // res/values-sw600dp). If this view is present, then the
                // activity should be in two-pane mode.
                mTwoPane = true;

                // In two-pane mode, list items should be given the
                // 'activated' state when touched.
//            ((CountriesListFragment) fragmentManager
//                    .findFragmentByTag("CountriesFrag"))
//                    .setActivateOnItemClick(true);

            }
        } else {
           mTwoPane = savedInstanceState.getBoolean("PANE");
        }


        // TODO: If exposing deep links into your app, handle intents here.

    }

    /**
     * Callback method from {@link com.dan.toyapp.fragment.CountriesListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id, int returningClass) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            if (returningClass == COUNTRIESLISTFRAG) {
                replaceListWithCities(id);

                createCountryMapFragment(id);

            } else if (returningClass == CITIESLISTFRAGMENT) {

                City city = parseString(id);
                createCityMapFragment(city);
                replaceListWithPeople(Integer.parseInt(city.getId()));

            } else if (returningClass == PEOPLELISTFRAGMENT) {

            }


        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            int cityID;

            if (returningClass == COUNTRIESLISTFRAG) {
                replaceListWithCities(id);

            } else if (returningClass == CITIESLISTFRAGMENT) {
                City city = parseString(id);

                replaceListWithPeople(Integer.parseInt(city.getId()));
            } else if (returningClass == PEOPLELISTFRAGMENT) {

            }

        }
    }

    private void createCityMapFragment(City city) {
        Fragment fragment = new CityMapFragment(new LatLng(city.getLatitude(), city.getLongitude()), this);
        fragment.setHasOptionsMenu(true);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.map_detail_container, fragment, "CityMapFrag");
        transaction.addToBackStack("CountryMapFrag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        transaction.remove(fragmentManager.findFragmentByTag("CountryMapFrag"));

        transaction.commit();
    }

    private void replaceMap(Fragment fragment, String tag){
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.map_detail_container, fragment, tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.commit();
    }
    private void replaceList(Fragment fragment, String tag){
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_frame_layout, fragment, tag);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        transaction.commit();
    }

    private void createCountryMapFragment(String country) {
        Fragment fragment = new CountryMapFragment(country,this);

        fragment.setHasOptionsMenu(true);
        final FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.map_detail_container, fragment, "CountryMapFrag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

//        transaction.addToBackStack("MapSupportFragmentExtension");
        transaction.commit();
    }

    private void replaceListWithCities(String countryCode) {
        CitiesListFragment citiesListFragment = new CitiesListFragment();
        Bundle arguments = new Bundle();
        arguments.putString("countryCode",
                countryCode);

        citiesListFragment.setArguments(arguments);
        citiesListFragment.setHasOptionsMenu(true);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_frame_layout, citiesListFragment, "CitiesListFrag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("CountriesListFragment");
//        transaction.remove(fragmentManager.findFragmentByTag("CountriesListFragment"));

        transaction.commit();
    }

    private void replaceListWithPeople(int cityCode) {
        PeopleListFragment fragment = new PeopleListFragment();
        fragment.setHasOptionsMenu(true);
        Bundle arguments = new Bundle();
        arguments.putInt("cityCode",
                cityCode);

        fragment.setArguments(arguments);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.list_frame_layout, fragment, "PeopleListFrag");
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.addToBackStack("CitiesListFragment");
//        transaction.remove(fragmentManager.findFragmentByTag("CitiesListFragment"));
        transaction.commit();
    }

    private City parseString(String s) {
        City mItem;
        String[] splitArgs = s.split(",");
        mItem = new City();

        mItem.setId(splitArgs[0]);
        mItem.setCountry(splitArgs[1]);
        mItem.setRegion(splitArgs[2]);
        mItem.setCity(splitArgs[3]);
        mItem.setLatitude(Float.parseFloat(((String.valueOf(splitArgs[4])))));
        mItem.setLongitude(Float.parseFloat((String.valueOf(splitArgs[5]))));


        // Load the dummy content specified by the fragment
        // arguments. In a real-world scenario, use a Loader
        // to load content from a content provider.
        return mItem;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("PANE", mTwoPane);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
