package com.uvad.demo.myrepresentatives;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class ZipFormDialogFragment extends DialogFragment implements OnEditorActionListener {

    public interface EditZipDialogListener {
	void onZipEntered(String zip);
    }

    private EditZipDialogListener mListener;
    private EditText mZipEditText;

    @Override
    public void onAttach(Activity activity) {
	super.onAttach(activity);

	try {
	    mListener = (EditZipDialogListener) activity;
	} catch (ClassCastException e) {
	    throw new ClassCastException("the activity: " + activity.getClass().getCanonicalName()
		    + " must implement the EditZipDialogListener interface");
	}
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
	LayoutInflater inflater = LayoutInflater.from(getActivity());
	final View v = inflater.inflate(R.layout.dialog_fragment_zip_form, null);
	mZipEditText = (EditText) v.findViewById(R.id.zipCodeValueEditText);
	return new AlertDialog.Builder(getActivity()).setTitle("Set Zip Code").setView(v).setCancelable(true)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			mListener.onZipEntered(mZipEditText.getText().toString());
		    }
		}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		    }
		}).create();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	if (EditorInfo.IME_ACTION_DONE == actionId) {
	    // Return input text to activity
	    mListener.onZipEntered(mZipEditText.getText().toString());
	    this.dismiss();
	    return true;
	}
	return false;
    }

}
