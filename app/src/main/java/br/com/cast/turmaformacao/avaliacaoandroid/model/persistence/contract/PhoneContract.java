package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrador on 01/10/2015.
 */
public class PhoneContract {

    public static final String PHONE_TABLE = "phone";
    public static final String PHONE_ID = "id";
    public static final String PHONE_NUMBER = "phone";
    public static final String PHONE_CONTACT_ID = "contact_id";

    public static final String[] colums = {PHONE_ID, PHONE_NUMBER, PHONE_CONTACT_ID};

    public static String getCreateTableScript(){
        StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + PHONE_TABLE);
        create.append(" ( ");
        create.append(PHONE_ID + " INTEGER PRIMARY KEY, ");
        create.append(PHONE_CONTACT_ID + " INTEGER NOT NULL, ");
        create.append(PHONE_NUMBER + " INTEGER NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(String phone, Long idContact){
        ContentValues cv = new ContentValues();
        cv.put(PHONE_NUMBER, phone);
        cv.put(PHONE_CONTACT_ID, idContact);
        return cv;
    }

    public static List<String> getPhoneListFromCursor(Cursor c){
        List<String> list = new ArrayList<>();
        while(c.moveToNext()) {
            list.add(getPhoneFromCursor(c));
        }
        return list;
    }
    public static String getPhoneFromCursor(Cursor c){
        if(c.isBeforeFirst() || c.moveToNext()){
            return c.getString(c.getColumnIndex(PHONE_NUMBER));
        }
        return null;
    }
}
