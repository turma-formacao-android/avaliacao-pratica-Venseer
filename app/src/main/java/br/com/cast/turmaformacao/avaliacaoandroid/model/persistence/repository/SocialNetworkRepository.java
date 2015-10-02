package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.SocialNetworkContract;

/**
 * Created by Administrador on 01/10/2015.
 */
public class SocialNetworkRepository {
    public static List<SocialNetwork> getAll(Long idContact){
        List<SocialNetwork> list = new ArrayList<SocialNetwork>();
        String[] whereArgs = {idContact.toString()};
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase reader = databaseHelper.getReadableDatabase();
        Cursor c = reader.query(SocialNetworkContract.SOCIAL_NETWORK_TABLE, SocialNetworkContract.colums,
                SocialNetworkContract.SOCIAL_NETWORK_CONTACT_ID+" = ?", whereArgs, null, null, null);
        while(c.moveToNext()){
            list.add(SocialNetworkContract.getSocialNetworkFromCursor(c));
        }
        reader.close();
        databaseHelper.close();
        return list;
    }

    public static Long addSocialNetwork(SocialNetwork sn, Long idContact){
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase writer = databaseHelper.getWritableDatabase();
            Long id = writer.insert(SocialNetworkContract.SOCIAL_NETWORK_TABLE, null,
                    SocialNetworkContract.getContentValues(sn, idContact));
            writer.close();
            databaseHelper.close();
            return id;
        }catch(Exception e){
            Log.e(SocialNetworkContract.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public static void deleteSocialNetworkByContactId(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(SocialNetworkContract.SOCIAL_NETWORK_TABLE,
                SocialNetworkContract.SOCIAL_NETWORK_CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
    public static void deleteSocialNetwork(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(SocialNetworkContract.SOCIAL_NETWORK_TABLE,
                SocialNetworkContract.SOCIAL_NETWORK_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
}
