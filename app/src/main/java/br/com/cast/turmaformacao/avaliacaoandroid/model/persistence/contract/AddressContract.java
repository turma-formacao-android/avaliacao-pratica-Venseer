package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Address;

/**
 * Created by Administrador on 01/10/2015.
 */
public class AddressContract {
    public static final String ADDRESS_TABLE = "address";
    public static final String ADDRESS_ID = "id";
    public static final String ADDRESS_ZIPCODE = "zipcode";
    public static final String ADDRESS_TYPE = "type";
    public static final String ADDRESS_ADDRESS = "address";
    public static final String ADDRESS_NEIGHBORHOOD = "neighborhood";
    public static final String ADDRESS_CITY ="city";
    public static final String ADDRESS_STATE = "state";
    public static final String ADDRESS_CONTACT_ID = "contact_id";

    public static final String[] colums = {ADDRESS_ID, ADDRESS_ZIPCODE, ADDRESS_TYPE, ADDRESS_ADDRESS,
            ADDRESS_NEIGHBORHOOD, ADDRESS_CITY, ADDRESS_STATE, ADDRESS_CONTACT_ID};

    public static String getCreateTableScript(){
        StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + ADDRESS_TABLE);
        create.append(" ( ");
        create.append(ADDRESS_ID + " INTEGER PRIMARY KEY, ");
        create.append(ADDRESS_CONTACT_ID + " INTEGER NOT NULL, ");
        create.append(ADDRESS_ZIPCODE + " INTEGER NOT NULL, ");
        create.append(ADDRESS_TYPE + " TEXT NOT NULL, ");
        create.append(ADDRESS_ADDRESS + " TEXT NOT NULL, ");
        create.append(ADDRESS_NEIGHBORHOOD + " TEXT NOT NULL, ");
        create.append(ADDRESS_CITY + " TEXT NOT NULL, ");
        create.append(ADDRESS_STATE + " TEXT NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(Address a, Long contactId){
        ContentValues cv = new ContentValues();
        cv.put(ADDRESS_ID, a.getId());
        cv.put(ADDRESS_CONTACT_ID, contactId);
        cv.put(ADDRESS_ZIPCODE, a.getZipCode());
        cv.put(ADDRESS_TYPE, a.getAddressType());
        cv.put(ADDRESS_ADDRESS, a.getAddress());
        cv.put(ADDRESS_NEIGHBORHOOD, a.getNeighborhood());
        cv.put(ADDRESS_CITY, a.getCity());
        cv.put(ADDRESS_STATE, a.getState());
        return cv;
    }

    public static Address getAddressFromCursor(Cursor c){
        Address address = new Address();
        address.setId(c.getLong(c.getColumnIndex(ADDRESS_ID)));
        address.setContactId(c.getLong(c.getColumnIndex(ADDRESS_CONTACT_ID)));
        address.setAddressType(c.getString(c.getColumnIndex(ADDRESS_TYPE)));
        address.setAddress(c.getString(c.getColumnIndex(ADDRESS_ADDRESS)));
        address.setZipCode(c.getString(c.getColumnIndex(ADDRESS_ZIPCODE)));
        address.setCity(c.getString(c.getColumnIndex(ADDRESS_CITY)));
        address.setNeighborhood(c.getString(c.getColumnIndex(ADDRESS_NEIGHBORHOOD)));
        address.setState(c.getString(c.getColumnIndex(ADDRESS_STATE)));
        return address;
    }
}
