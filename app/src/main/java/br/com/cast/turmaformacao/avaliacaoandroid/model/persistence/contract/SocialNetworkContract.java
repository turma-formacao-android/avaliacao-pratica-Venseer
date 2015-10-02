package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;

/**
 * Created by Administrador on 01/10/2015.
 */
public class SocialNetworkContract {

    public static final String SOCIAL_NETWORK_TABLE = "social_network_Table";
    public static final String SOCIAL_NETWORK_ID = "id";
    public static final String SOCIAL_NETWORK_CONTACT_ID = "contact_id";
    public static final String SOCIAL_NETWORK_NAME = "network";
    public static final String SOCIAL_NETWORK_VALUE = "value";

    public static final String[] colums = {SOCIAL_NETWORK_ID, SOCIAL_NETWORK_NAME,
            SOCIAL_NETWORK_CONTACT_ID, SOCIAL_NETWORK_VALUE};

    public static String getCreateTableScript(){
        StringBuilder create = new StringBuilder();
        create.append(" CREATE TABLE " + SOCIAL_NETWORK_TABLE);
        create.append(" ( ");
        create.append(SOCIAL_NETWORK_ID + " INTEGER PRIMARY KEY, ");
        create.append(SOCIAL_NETWORK_CONTACT_ID + " INTEGER NOT NULL, ");
        create.append(SOCIAL_NETWORK_NAME + " TEXT NOT NULL, ");
        create.append(SOCIAL_NETWORK_VALUE + " TEXT NOT NULL ");
        create.append(" ); ");
        return create.toString();
    }

    public static ContentValues getContentValues(SocialNetwork sn, Long idContact){
        ContentValues cv = new ContentValues();
        cv.put(SOCIAL_NETWORK_NAME, sn.getSocialNetoworkName());
        cv.put(SOCIAL_NETWORK_VALUE, sn.getValue());
        cv.put(SOCIAL_NETWORK_CONTACT_ID, idContact);
        return cv;
    }

    public static List<SocialNetwork> getSocialNetworkListFromCursor(Cursor c){
        List<SocialNetwork> list = new ArrayList<SocialNetwork>();
        while(c.moveToNext()) {
            list.add(getSocialNetworkFromCursor(c));
        }
        return list;
    }
    public static SocialNetwork getSocialNetworkFromCursor(Cursor c){
        SocialNetwork sn = new SocialNetwork();
        sn.setSocialNetoworkName(c.getString(c.getColumnIndex(SOCIAL_NETWORK_NAME)));
        sn.setValue(c.getString(c.getColumnIndex(SOCIAL_NETWORK_VALUE)));
        return sn;
    }
}
