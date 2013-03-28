package com.uvad.demo.myrepresentatives.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.uvad.demo.myrepresentatives.database.tables.RepresentativeTable;

/**
 * Generated model class for usage in your application, defined by classifiers in ecore diagram
 * 		
 * Generated Class. Do not modify!
 * 
 * @author MDSDACP Team - goetzfred@fh-bingen.de 
 * @date 2013.03.28	 
 */
public class Representative {

	private Long id;
	private java.lang.String name;
	private java.lang.String party;
	private java.lang.String state;
	private java.lang.String district;
	private java.lang.String phone;
	private java.lang.String office;
	private java.lang.String link;
	private java.lang.String zip;
	private java.lang.String uniqueid;

	private final ContentValues values = new ContentValues();

	public Representative() {
	}

	public Representative(final Cursor cursor) {
		setId(cursor.getLong(cursor.getColumnIndex(RepresentativeTable.ID)));
		setName(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.NAME)));
		setParty(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.PARTY)));
		setState(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.STATE)));
		setDistrict(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.DISTRICT)));
		setPhone(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.PHONE)));
		setOffice(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.OFFICE)));
		setLink(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.LINK)));
		setZip(cursor.getString(cursor.getColumnIndex(RepresentativeTable.ZIP)));
		setUniqueId(cursor.getString(cursor
				.getColumnIndex(RepresentativeTable.UNIQUEID)));

	}

	/**
	 * Set id
	 *
	 * @param id from type java.lang.Long
	 */
	public void setId(final Long id) {
		this.id = id;
		this.values.put(RepresentativeTable.ID, id);
	}

	/**
	 * Get id
	 *
	 * @return id from type java.lang.Long				
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * Set name and set content value
	 *
	 * @param name from type java.lang.String
	 */
	public void setName(final java.lang.String name) {
		this.name = name;
		this.values.put(RepresentativeTable.NAME, name);
	}

	/**
	 * Get name
	 *
	 * @return name from type java.lang.String				
	 */
	public java.lang.String getName() {
		return this.name;
	}

	/**
	 * Set party and set content value
	 *
	 * @param party from type java.lang.String
	 */
	public void setParty(final java.lang.String party) {
		this.party = party;
		this.values.put(RepresentativeTable.PARTY, party);
	}

	/**
	 * Get party
	 *
	 * @return party from type java.lang.String				
	 */
	public java.lang.String getParty() {
		return this.party;
	}

	/**
	 * Set state and set content value
	 *
	 * @param state from type java.lang.String
	 */
	public void setState(final java.lang.String state) {
		this.state = state;
		this.values.put(RepresentativeTable.STATE, state);
	}

	/**
	 * Get state
	 *
	 * @return state from type java.lang.String				
	 */
	public java.lang.String getState() {
		return this.state;
	}

	/**
	 * Set district and set content value
	 *
	 * @param district from type java.lang.String
	 */
	public void setDistrict(final java.lang.String district) {
		this.district = district;
		this.values.put(RepresentativeTable.DISTRICT, district);
	}

	/**
	 * Get district
	 *
	 * @return district from type java.lang.String				
	 */
	public java.lang.String getDistrict() {
		return this.district;
	}

	/**
	 * Set phone and set content value
	 *
	 * @param phone from type java.lang.String
	 */
	public void setPhone(final java.lang.String phone) {
		this.phone = phone;
		this.values.put(RepresentativeTable.PHONE, phone);
	}

	/**
	 * Get phone
	 *
	 * @return phone from type java.lang.String				
	 */
	public java.lang.String getPhone() {
		return this.phone;
	}

	/**
	 * Set office and set content value
	 *
	 * @param office from type java.lang.String
	 */
	public void setOffice(final java.lang.String office) {
		this.office = office;
		this.values.put(RepresentativeTable.OFFICE, office);
	}

	/**
	 * Get office
	 *
	 * @return office from type java.lang.String				
	 */
	public java.lang.String getOffice() {
		return this.office;
	}

	/**
	 * Set link and set content value
	 *
	 * @param link from type java.lang.String
	 */
	public void setLink(final java.lang.String link) {
		this.link = link;
		this.values.put(RepresentativeTable.LINK, link);
	}

	/**
	 * Get link
	 *
	 * @return link from type java.lang.String				
	 */
	public java.lang.String getLink() {
		return this.link;
	}

	/**
	 * Set zip and set content value
	 *
	 * @param zip from type java.lang.String
	 */
	public void setZip(final java.lang.String zip) {
		this.zip = zip;
		this.values.put(RepresentativeTable.ZIP, zip);
	}

	/**
	 * Get zip
	 *
	 * @return zip from type java.lang.String				
	 */
	public java.lang.String getZip() {
		return this.zip;
	}

	/**
	 * Set uniqueid and set content value
	 *
	 * @param uniqueid from type java.lang.String
	 */
	public void setUniqueId(final java.lang.String uniqueid) {
		this.uniqueid = uniqueid;
		this.values.put(RepresentativeTable.UNIQUEID, uniqueid);
	}

	/**
	 * Get uniqueid
	 *
	 * @return uniqueid from type java.lang.String				
	 */
	public java.lang.String getUniqueId() {
		return this.uniqueid;
	}

	/**
	 * Get ContentValues
	 *
	 * @return id from type android.content.ContentValues with the values of this object				
	 */
	public ContentValues getContentValues() {
		return this.values;
	}
}
