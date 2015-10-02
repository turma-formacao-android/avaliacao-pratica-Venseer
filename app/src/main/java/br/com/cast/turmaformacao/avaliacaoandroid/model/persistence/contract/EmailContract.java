package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;

/**
 * Created by Administrador on 01/10/2015.
 */
public class EmailContract {

    public static final String EMAIL_TABLE = "email";
    public static final String EMAIL_ID = "id";
    public static final String EMAIL_ADDRESS = "email";
    public static final String EMAIL_CONTACT_ID = "contact_id";

    public static final String[] colums = {EMAIL_ID, EMAIL_ADDRESS, EMAIL_CONTACT_ID};

    public static String getCreateTableScript(){
        StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + EMAIL_TABLE);
        create.append(" ( ");
        create.append(EMAIL_ID + " INTEGER PRIMARY KEY, ");
        create.append(EMAIL_CONTACT_ID + " INTEGER NOT NULL, ");
        create.append(EMAIL_ADDRESS + " TEXT NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(String email, Long idContact){
        ContentValues cv = new ContentValues();
        cv.put(EMAIL_ADDRESS, email);
        cv.put(EMAIL_CONTACT_ID, idContact);
        return cv;
    }

    public static List<String> getEmailListFromCursor(Cursor c){
        List<String> list = new ArrayList<>();
        while(c.moveToNext()) {
            list.add(getEmailFromCursor(c));
        }
        return list;
    }

    public static String getEmailFromCursor(Cursor c){
        if(c.isBeforeFirst() || c.moveToNext()){
            return c.getString(c.getColumnIndex(EMAIL_ADDRESS));
        }
        return null;
    }
}
