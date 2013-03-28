package com.uvad.demo.myrepresentatives.database.tables;

/**
 * This interface represents the columns and SQLite statements for the RepresentativeTable.
 * This table is represented in the sqlite database as Representative column.
 * 				  
 * Generated Class. Do not modify!
 * 
 * @author MDSDACP Team - goetzfred@fh-bingen.de 
 * @date 2013.03.28
 */
public interface RepresentativeTable {
	String TABLE_NAME = "representative";

	String ID = "_id";
	String NAME = "name";
	String PARTY = "party";
	String STATE = "state";
	String DISTRICT = "district";
	String PHONE = "phone";
	String OFFICE = "office";
	String LINK = "link";
	String ZIP = "zip";
	String UNIQUEID = "uniqueid";

	String[] ALL_COLUMNS = new String[]{ID, NAME, PARTY, STATE, DISTRICT,
			PHONE, OFFICE, LINK, ZIP, UNIQUEID};

	String SQL_CREATE = "CREATE TABLE " + TABLE_NAME + " ( " + ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT" + "," + NAME + " TEXT" + ","
			+ PARTY + " TEXT" + "," + STATE + " TEXT" + "," + DISTRICT
			+ " TEXT" + "," + PHONE + " TEXT" + "," + OFFICE + " TEXT" + ","
			+ LINK + " TEXT" + "," + ZIP + " TEXT" + "," + UNIQUEID + " TEXT"
			+ " )";

	String SQL_INSERT = "INSERT INTO " + TABLE_NAME + " (" + NAME + "," + PARTY
			+ "," + STATE + "," + DISTRICT + "," + PHONE + "," + OFFICE + ","
			+ LINK + "," + ZIP + "," + UNIQUEID
			+ ") VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

	String WHERE_ID_EQUALS = ID + "=?";
}
