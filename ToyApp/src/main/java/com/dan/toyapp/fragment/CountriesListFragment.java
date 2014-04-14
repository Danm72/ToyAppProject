package com.dan.toyapp.fragment;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.dan.toyapp.R;
import com.dan.toyapp.adapter.BufferAdapter;
import com.dan.toyapp.database.CountriesDataSource;
import com.dan.toyapp.entity.Country;
import com.dan.toyapp.entity.interfaces.DisplayFacade;
import com.dan.toyapp.task.taskinterfaces.OnTaskCompleted;
import com.dan.toyapp.task.GetTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.dan.toyapp.constants.Constants.COUNTRIESLISTFRAG;

/**
 * A list fragment representing a list of Person. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link com.dan.toyapp.fragment.map.CityMapFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * taskinterfaces.
 */
public class CountriesListFragment extends ListFragment implements OnTaskCompleted {

    BufferAdapter arrayOfCountries;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback taskinterfaces that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id, int classReturning);
    }

    /**
     * A dummy implementation of the {@link Callbacks} taskinterfaces that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {

        @Override
        public void onItemSelected(String id, int classReturning) {

        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CountriesListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        arrayOfCountries = new BufferAdapter(
                getActivity(),
                R.layout.detailed_listitem);
        // TODO: replace with a real list adapter.
        setListAdapter(arrayOfCountries);
        GetTask.createGetRequest(this.getActivity(), this, "http://honey.computing.dcu.ie/city/countries.php");

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks taskinterfaces to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks taskinterfaces (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(((DisplayFacade) arrayOfCountries.getItem(position)).getExtra(), COUNTRIESLISTFRAG);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    void parseJson(JSONArray jsonArray) {

        CountriesDataSource datasource = new CountriesDataSource(this.getActivity());
        datasource.open();

        for (Object jsonObject : jsonArray) {
            Country tempCountry;
            String object = (String) jsonObject;
            Geocoder geocoder = new Geocoder(this.getActivity(), Locale.ENGLISH);
            try {
                List<Address> addressList = geocoder.getFromLocationName(object, 1);
                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    tempCountry = new Country();//datasource.getCountry(object);
                    tempCountry.setCountryCode(address.getCountryCode());
                    tempCountry.setName(address.getCountryName());

                    arrayOfCountries.addToFront(tempCountry);
                    arrayOfCountries.notifyDataSetChanged();
                }else{
                    Toast.makeText(getParentFragment().getActivity().getApplicationContext() ,"Connection error", Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        datasource.close();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onTaskCompleted(Object obj) {

        HashMap<String, Object> response = (HashMap<String, Object>) obj;
        response.keySet();
        for (String key : response.keySet()) {
            JSONObject object = (JSONObject) response.get(key);
            JSONArray jsonArray = (JSONArray) object.get("result");

            if (key.contains("reverse")) {
                parseJson(jsonArray);
            } else {
                parseJson(jsonArray);
            }
        }
    }
}
