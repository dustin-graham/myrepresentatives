package com.uvad.demo.myrepresentatives.service.synchronizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.uvad.demo.myrepresentatives.service.RemoteObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public abstract class Synchronizer<T extends RemoteObject> {
	
	protected Context mContext;

	public Synchronizer(Context context) {
		mContext = context;
	}
	
	/*
	 * The ID column for the localItems Cursor must be at index 0!
	 */
	public void synchronize(Context context, List<T> items, Cursor localItems, int remoteIdentifierColumn) {
		Map<String, T> remoteEntities = new HashMap<String, T>();
		for (T entity : items) {
			remoteEntities.put(entity.getIdentifier(), entity);
		}

		List<T> updates = new ArrayList<T>();
		List<Long> deletions = new ArrayList<Long>();
		for (boolean hasItem = localItems.moveToFirst(); hasItem; hasItem = localItems.moveToNext()) {
			String localIdentifier = localItems.getString(remoteIdentifierColumn);
			T matchingEntity = remoteEntities.get(localIdentifier);
			if (matchingEntity == null) {
				// there was no match so this entity should be removed from the
				// local storage
				deletions.add(localItems.getLong(0));
				continue;
			}
			if (this.isRemoteEntityNewerThanLocal(matchingEntity, localItems)) {
				// the remote entity is newer than the local counterpart, mark
				// this one for update
				updates.add(matchingEntity);
			}
			remoteEntities.remove(localIdentifier);
			continue;
		}
		// anything left over in the remoteEntities Map is a new entity that we
		// don't have yet, mark them for insertion
		List<T> inserts = new ArrayList<T>(remoteEntities.values());
		this.performSynchronizationOperations(context, inserts, updates, deletions);
	}

	protected abstract void performSynchronizationOperations(Context context, List<T> inserts, List<T> updates,
			List<Long> deletions);

	protected abstract boolean isRemoteEntityNewerThanLocal(T remote, Cursor c);
	
	protected abstract ContentValues getContentValuesForRemoteEntity(T t);
}
