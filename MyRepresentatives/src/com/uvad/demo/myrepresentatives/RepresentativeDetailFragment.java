package com.uvad.demo.myrepresentatives;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;
import com.uvad.demo.myrepresentatives.model.Representative;

/**
 * A fragment representing a single Representative detail screen. This fragment
 * is either contained in a {@link RepresentativeListActivity} in two-pane mode
 * (on tablets) or a {@link RepresentativeDetailActivity} on handsets.
 */
public class RepresentativeDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private Representative mRepresentative;

    private TextView mNameTextView;
    private TextView mPartyTextView;
    private TextView mStateTextView;
    private TextView mDistrictTextView;
    private TextView mPhoneTextView;
    private TextView mOfficeTextView;
    private TextView mLinkTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (getArguments().containsKey(ARG_ITEM_ID)) {
	    // Load the dummy content specified by the fragment
	    // arguments. In a real-world scenario, use a Loader
	    // to load content from a content provider.
	    Long repId = getArguments().getLong(ARG_ITEM_ID);
	    Cursor repCursor = getActivity().getContentResolver().query(Provider.REPRESENTATIVE_CONTENT_URI, null,
		    RepresentativeTable.ID + " = ?", new String[] { String.valueOf(repId) }, null);
	    repCursor.moveToFirst();
	    mRepresentative = new Representative(repCursor);
	}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View rootView = inflater.inflate(R.layout.fragment_representative_detail, container, false);
	
	mNameTextView = (TextView)rootView.findViewById(R.id.representativeNameTextView);
	mPartyTextView = (TextView)rootView.findViewById(R.id.partyTextView);
	mStateTextView = (TextView)rootView.findViewById(R.id.stateValueTextView);
	mDistrictTextView = (TextView)rootView.findViewById(R.id.districtValueTextView);
	mPhoneTextView = (TextView)rootView.findViewById(R.id.phoneValueTextView);
	mOfficeTextView = (TextView)rootView.findViewById(R.id.officeValueTextView);
	mLinkTextView = (TextView)rootView.findViewById(R.id.linkValueTextView);
	
	mNameTextView.setText(mRepresentative.getName());
	mPartyTextView.setText(mRepresentative.getParty());
	mStateTextView.setText(mRepresentative.getState());
	mDistrictTextView.setText(String.valueOf(mRepresentative.getDistrict()));
	mPhoneTextView.setText(mRepresentative.getPhone());
	mOfficeTextView.setText(mRepresentative.getOffice());
	mLinkTextView.setText(mRepresentative.getLink());

	return rootView;
    }
}
