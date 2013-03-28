package com.uvad.demo.myrepresentatives.service.synchronizer;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.RemoteException;

import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;
import com.uvad.demo.myrepresentatives.service.RepresentativeService;
import com.uvad.demo.myrepresentatives.service.RepresentativeService.RemoteRepresentative;

public class RepresentativeSynchronizer extends Synchronizer<RepresentativeService.RemoteRepresentative> {

    public RepresentativeSynchronizer(Context context) {
	super(context);
    }

    @Override
    protected void performSynchronizationOperations(Context context, List<RemoteRepresentative> inserts,
	    List<RemoteRepresentative> updates, List<Long> deletions) {
	ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();
	
	for (RepresentativeService.RemoteRepresentative w : inserts) {
	    ContentValues values = this.getContentValuesForRemoteEntity(w);
	    ContentProviderOperation op = ContentProviderOperation.newInsert(Provider.REPRESENTATIVE_CONTENT_URI)
		    .withValues(values).build();
	    operations.add(op);
	}
	
	for (RepresentativeService.RemoteRepresentative w : updates) {
	    ContentValues values = this.getContentValuesForRemoteEntity(w);
	    ContentProviderOperation op = ContentProviderOperation.newUpdate(Provider.REPRESENTATIVE_CONTENT_URI)
		    .withSelection(RepresentativeTable.UNIQUEID + " = ?", new String[] { w.uniqueId }).withValues(values).build();
	    operations.add(op);
	}
	
	for (Long id : deletions) {
	    ContentProviderOperation op = ContentProviderOperation.newDelete(Provider.REPRESENTATIVE_CONTENT_URI)
		    .withSelection(RepresentativeTable.ID + " = ?", new String[] { String.valueOf(id) }).build();
	    operations.add(op);
	}
	
	try {
	    context.getContentResolver().applyBatch(Provider.AUTHORITY, operations);
	} catch (RemoteException e) {
	    e.printStackTrace();
	} catch (OperationApplicationException e) {
	    e.printStackTrace();
	}
	
    }

    @Override
    protected boolean isRemoteEntityNewerThanLocal(RemoteRepresentative remote, Cursor c) {
	//there isn't a versioning mechanism on the service resources so always consider the remote copy new
	return true;
    }

    @Override
    protected ContentValues getContentValuesForRemoteEntity(RemoteRepresentative t) {
	ContentValues values = new ContentValues();
	values.put(RepresentativeTable.NAME, t.name);
	values.put(RepresentativeTable.PARTY, t.party);
	values.put(RepresentativeTable.STATE, t.state);
	values.put(RepresentativeTable.PHONE, t.phone);
	values.put(RepresentativeTable.OFFICE, t.office);
	values.put(RepresentativeTable.LINK, t.link);
	values.put(RepresentativeTable.UNIQUEID, t.uniqueId);
	values.put(RepresentativeTable.ZIP, t.zip);
	return values;
    }

}
