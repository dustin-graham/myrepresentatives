package com.uvad.demo.myrepresentatives.service.synchronizer;

import java.util.List;

import com.uvad.demo.myrepresentatives.service.RepresentativeService;
import com.uvad.demo.myrepresentatives.service.RepresentativeService.RemoteRepresentative;

public class RepresentativePreprocessor extends RemotePreProcessor<RepresentativeService.RemoteRepresentative> {
    private String mZip;
    
    public RepresentativePreprocessor(String zip) {
	mZip = zip;
    }

    @Override
    public void preProcessRemoteRecords(List<RemoteRepresentative> records) {
	for (RemoteRepresentative rep : records) {
	    rep.zip = mZip;
	    rep.uniqueId = rep.name + "_" + mZip;
	}
    }

}
