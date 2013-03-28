package com.uvad.demo.myrepresentatives;

import java.util.ArrayList;

import android.app.Application;
import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.os.RemoteException;

import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;

public class MyRepresentativesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //generateDummyData();
    }
    
    @SuppressWarnings("unused")
    private void generateDummyData() {
	//delete existing records so we have a clean slate
	getContentResolver().delete(Provider.REPRESENTATIVE_CONTENT_URI, null, null);
	
	String defaultZip = "84043";
	ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
	for (int i = 0; i < 10; i++) {
	    ContentValues values = new ContentValues();
	    values.put(RepresentativeTable.NAME, "Representative " + i);
	    values.put(RepresentativeTable.PARTY, i % 2 == 0 ? "REP" : "DEM");
	    values.put(RepresentativeTable.STATE, "Utah");
	    values.put(RepresentativeTable.DISTRICT, i % 2 == 0 ? 1 : 2);
	    values.put(RepresentativeTable.PHONE, "801-123-4567");
	    values.put(RepresentativeTable.OFFICE, "123 Fake St.");
	    values.put(RepresentativeTable.LINK, "http://whitehouse.gov");
	    values.put(RepresentativeTable.ZIP, defaultZip);
	    operations.add(ContentProviderOperation.newInsert(Provider.REPRESENTATIVE_CONTENT_URI).withValues(values).withYieldAllowed(true).build());
	}
	try {
	    getContentResolver().applyBatch(Provider.AUTHORITY, operations);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (OperationApplicationException e) {
	    e.printStackTrace();
	}
    }

}
