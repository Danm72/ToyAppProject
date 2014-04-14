package com.dan.toyapp.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.dan.toyapp.R;
import com.dan.toyapp.adapter.BufferAdapter;
import com.dan.toyapp.task.RequestSubject;
import com.dan.toyapp.scrollers.ScrollListenerExtension;

import static com.dan.toyapp.constants.Constants.PEOPLELISTFRAGMENT;

/**
 * A list fragment representing a list of Person. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link com.dan.toyapp.fragment.map.CityMapFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * taskinterfaces.
 */
public class PeopleListFragment extends ListFragment {

    private static String TAG = "PeopleListFrag";
    BufferAdapter arrayOfPeople;
    int cityCode;

    Context context = this.getActivity();

    AbsListView.OnScrollListener listener;


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
        public void onItemSelected(String id, int returningClass);
    }

    /**
     * A dummy implementation of the {@link Callbacks} taskinterfaces that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {

        @Override
        public void onItemSelected(String id, int returningClass) {

        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PeopleListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().setTitle("People");
        arrayOfPeople = new BufferAdapter(
                getActivity(),
                R.layout.detailed_listitem);
        if (getArguments().containsKey("cityCode")) {
            int bundleArgs = getArguments().getInt("cityCode");
            cityCode = bundleArgs;
            RequestSubject requestSubject = new RequestSubject(cityCode + "", "people.php", arrayOfPeople);

            listener = new ScrollListenerExtension(requestSubject);
        }
        // TODO: replace with a real list adapter.
        setListAdapter(arrayOfPeople);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setOnScrollListener(listener);
        getListView().setVerticalScrollBarEnabled(false);

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
        mCallbacks.onItemSelected(arrayOfPeople.getItem(position).toString(), PEOPLELISTFRAGMENT);
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

}

