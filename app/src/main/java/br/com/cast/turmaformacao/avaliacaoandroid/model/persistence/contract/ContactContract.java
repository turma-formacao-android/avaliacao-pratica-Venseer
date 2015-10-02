package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract;

import android.content.ContentValues;
import android.database.Cursor;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;

/**
 * Created by Administrador on 01/10/2015.
 */
public class ContactContract {

    public static final String CONTACT_TABLE = "contact_table";
    public static final String CONTACT_ID = "id";
    public static final String CONTACT_NAME = "name";

    public static final String[] colums = {CONTACT_ID, CONTACT_NAME};

    public static String getCreateTableScript(){
        StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + CONTACT_TABLE);
        create.append(" ( ");
        create.append(CONTACT_ID + " INTEGER PRIMARY KEY, ");
        create.append(CONTACT_NAME + " TEXT NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(Contact c){
        ContentValues cv = new ContentValues();
        cv.put(CONTACT_ID, c.getId());
        cv.put(CONTACT_NAME, c.getName());
        return cv;
    }

    public static Contact getContactFromCursor(Cursor c){
        Contact contact = new Contact();
        contact.setId(c.getLong(c.getColumnIndex(CONTACT_ID)));
        contact.setName(c.getString(c.getColumnIndex(CONTACT_NAME)));
        return contact;
    }
}
