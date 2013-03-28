package com.uvad.demo.myrepresentatives;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;
import com.uvad.demo.myrepresentatives.service.RepresentativeService;

/**
 * A list fragment representing a list of Representatives. This fragment also
 * supports tablet devices by allowing list items to be given an 'activated'
 * state upon selection. This helps indicate which item is currently being
 * viewed in a {@link RepresentativeDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class RepresentativeListFragment extends ListFragment implements LoaderCallbacks<Cursor> {
    private static final String DEFAULT_ZIP = "84043";
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
	public void onItemSelected(Long id);
    }

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    private CursorLoader mLoader;
    private CursorAdapter mAdapter;
    private String mZipCode = DEFAULT_ZIP;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null,
		new String[] { RepresentativeTable.NAME }, new int[] { android.R.id.text1 }, 0);

	setListAdapter(mAdapter);
	setZipCode(mZipCode);
    }

    private void performRepSync() {
	//fire off a request to synchronize the representatives at the current zip code
	Intent i = new Intent(getActivity(), RepresentativeService.class);
	i.putExtra(RepresentativeService.ARG_ZIP, mZipCode);
	i.setAction(Intent.ACTION_SYNC);
	getActivity().startService(i);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
	super.onViewCreated(view, savedInstanceState);

	// Restore the previously serialized activated item position.
	if (savedInstanceState != null && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
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

	// Reset the active callbacks interface to the dummy implementation.
	mCallbacks = null;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
	super.onListItemClick(listView, view, position, id);
	if (mCallbacks == null)
	    return;

	// Notify the active callbacks interface (the activity, if the
	// fragment is attached to one) that an item has been selected.
	mCallbacks.onItemSelected(id);
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
	getListView().setChoiceMode(activateOnItemClick ? ListView.CHOICE_MODE_SINGLE : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
	if (position == ListView.INVALID_POSITION) {
	    getListView().setItemChecked(mActivatedPosition, false);
	} else {
	    getListView().setItemChecked(position, true);
	}

	mActivatedPosition = position;
    }
    
    public void setZipCode(String zipCode) {
	mZipCode = zipCode;
	performRepSync();
	if (mLoader != null) {
	    getLoaderManager().restartLoader(0, null, this);
	} else {
	    getLoaderManager().initLoader(0, null, this);
	}
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
	mLoader = new CursorLoader(getActivity(), Provider.REPRESENTATIVE_CONTENT_URI, new String[] {
		RepresentativeTable.ID, RepresentativeTable.NAME, RepresentativeTable.ZIP },
		RepresentativeTable.ZIP + " = ?", new String[] { mZipCode }, RepresentativeTable.NAME + " ASC");
	return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
	mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
	mAdapter.swapCursor(null);
    }
}
