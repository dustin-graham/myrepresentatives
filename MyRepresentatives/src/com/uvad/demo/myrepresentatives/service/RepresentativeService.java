package com.uvad.demo.myrepresentatives.service;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Name;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import com.uvad.demo.myrepresentatives.contentprovider.Provider;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;
import com.uvad.demo.myrepresentatives.service.synchronizer.RemotePreProcessor;
import com.uvad.demo.myrepresentatives.service.synchronizer.RepresentativePreprocessor;
import com.uvad.demo.myrepresentatives.service.synchronizer.RepresentativeSynchronizer;
import com.uvad.demo.myrepresentatives.service.synchronizer.Synchronizer;

import de.greenrobot.event.EventBus;

public class RepresentativeService extends BaseService {

    public static final String ARG_ZIP = "arg_zip";
    private static final String REQUIRED_SERVICE_OUTPUT_FORMAT = "json";

    public class RepresentativeServiceEvent {
	public boolean success;
	public String message;

	public RepresentativeServiceEvent(boolean success, String message) {
	    this.success = success;
	    this.message = message;
	}

    }

    public class RemoteResponse {
	public List<RemoteRepresentative> results;
    }

    public class RemoteRepresentative extends RemoteObject {
	public String name;
	public String party;
	public String state;
	public String district;
	public String phone;
	public String office;
	public String link;
	// this doesn't come back in the service but it's a unique identifier
	public String uniqueId;
	// this doesn't come back in the service but we need it to group the
	// representatives
	public String zip;

	@Override
	public String getIdentifier() {
	    return uniqueId;
	}
    }

    public interface RepresentativeClient {
	@GET("/getall_mems.php")
	RemoteResponse getRepresentatives(@Name("zip") String zipCode, @Name("output") String outputType);
    }

    public RepresentativeService() {
	super("RepresentativeService");
    }

    public RepresentativeService(Context c) {
	super("RepresentativeService", c);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
	if (intent.getAction().equals(Intent.ACTION_SYNC)) {
	    String zip = intent.getStringExtra(ARG_ZIP);
	    if (zip == null || zip.length() != 5) {
		EventBus.getDefault().post(
			new RepresentativeServiceEvent(false, "Please specify a valid 5 digit zip code"));
		return;
	    }
	    RepresentativeClient client = ServiceClient.getInstance().getClient(getContext(),
		    RepresentativeClient.class);
	    RemoteResponse response = client.getRepresentatives(zip, REQUIRED_SERVICE_OUTPUT_FORMAT);
	    List<RemoteRepresentative> reps = response.results;
	    if (reps != null && reps.size() > 0) {
		// synchronize!
		Cursor localRepCursor = getLocalRepCursor(zip);
		synchronizeRemoteRecords(reps, localRepCursor,
			localRepCursor.getColumnIndex(RepresentativeTable.UNIQUEID),
			new RepresentativeSynchronizer(getContext()), new RepresentativePreprocessor(zip));
	    } else {
		EventBus.getDefault().post(
			new RepresentativeServiceEvent(false, "There where no representatives for this zip code"));
	    }
	}
    }

    public Cursor getLocalRepCursor(String zipCode) {
	Cursor localRepCursor = getContext().getContentResolver().query(Provider.REPRESENTATIVE_CONTENT_URI, null,
		RepresentativeTable.ZIP + " = ?", new String[] { zipCode }, null);
	localRepCursor.moveToFirst();
	return localRepCursor;
    }

    public void synchronizeRemoteRecords(List<RemoteRepresentative> remoteReps, Cursor localReps,
	    int remoteIdentifierColumn, Synchronizer<RemoteRepresentative> synchronizer,
	    RemotePreProcessor<RemoteRepresentative> preProcessor) {
	preProcessor.preProcessRemoteRecords(remoteReps);
	synchronizer.synchronize(getContext(), remoteReps, localReps, remoteIdentifierColumn);
    }

}
