package com.uvad.demo.myrepresentatives.test;

import java.util.List;

import android.database.Cursor;
import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;
import com.uvad.demo.myrepresentatives.service.RepresentativeService;
import com.uvad.demo.myrepresentatives.service.synchronizer.RepresentativePreprocessor;
import com.uvad.demo.myrepresentatives.service.synchronizer.RepresentativeSynchronizer;

public class RepresentativeSyncTest extends AndroidTestCase {

    private RepresentativeService mRepService;
    private String mSampleRepServiceResponse = "{ \"results\": [{\"name\": \"Chris Stewart\", \"party\": \"R\", \"state\": \"UT\", \"district\": \"2\", \"phone\": \"202-225-9730\", \"office\": \"323 Cannon House Office Building\", \"link\": \"http://stewart.house.gov\" }, {\"name\": \"Jason Chaffetz\", \"party\": \"R\", \"state\": \"UT\", \"district\": \"3\", \"phone\": \"202-225-7751\", \"office\": \"2464 Rayburn House Office Building\", \"link\": \"http://chaffetz.house.gov\" }, {\"name\": \"Orrin Hatch\", \"party\": \"R\", \"state\": \"UT\", \"district\": \"Senior Seat\", \"phone\": \"202-224-5251\", \"office\": \"104 Hart Senate Office Building\", \"link\": \"http://www.hatch.senate.gov\" }, {\"name\": \"Mike Lee\", \"party\": \"R\", \"state\": \"UT\", \"district\": \"Junior Seat\", \"phone\": \"202-224-5444\", \"office\": \"316 Hart Senate Office Building\", \"link\": \"http://www.lee.senate.gov\" }]}";

    @Override
    protected void setUp() throws Exception {
	super.setUp();
	//clear the database
	getContext().getContentResolver().delete(Provider.REPRESENTATIVE_CONTENT_URI, null, null);

	mRepService = new RepresentativeService(getContext());
    }

    public void testRepresentativeSync() {
	Gson gson = new Gson();
	RepresentativeService.RemoteResponse response = gson.fromJson(mSampleRepServiceResponse,
		RepresentativeService.RemoteResponse.class);
	assertNotNull(response);
	List<RepresentativeService.RemoteRepresentative> reps = response.results;
	assertNotNull(reps);
	assertTrue(reps.size() == 4);
	Cursor localCursor = mRepService.getLocalRepCursor("84043");
	RepresentativePreprocessor preProcessor = new RepresentativePreprocessor("84043");
	mRepService.synchronizeRemoteRecords(reps, localCursor,
		localCursor.getColumnIndex(RepresentativeTable.UNIQUEID),
		new RepresentativeSynchronizer(mContext), preProcessor);
	localCursor.close();
	
	Cursor syncedCursor = mRepService.getLocalRepCursor("84043");
	assertNotNull(syncedCursor);
	assertEquals(reps.size(), syncedCursor.getCount());
    }

}
