package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.PhoneContract;

/**
 * Created by Administrador on 01/10/2015.
 */
public class PhoneRepository {

    public static List<String> getAll(Long idContact){
        List<String> list = new ArrayList<String>();
        String[] whereArgs = {idContact.toString()};
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase reader = databaseHelper.getReadableDatabase();
        Cursor c = reader.query(PhoneContract.PHONE_TABLE, PhoneContract.colums,
                PhoneContract.PHONE_CONTACT_ID+" = ?", whereArgs, null, null, null);
        while(c.moveToNext()){
            list.add(PhoneContract.getPhoneFromCursor(c));
        }
        reader.close();
        databaseHelper.close();
        return list;
    }

    public static Long addPhone(String email, Long idContact){
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase writer = databaseHelper.getWritableDatabase();
            Long id = writer.insert(PhoneContract.PHONE_TABLE, null, PhoneContract.getContentValues(email, idContact));
            writer.close();
            databaseHelper.close();
            return id;
        }catch(Exception e){
            Log.e(PhoneContract.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public static void deletePhone(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(PhoneContract.PHONE_TABLE, PhoneContract.PHONE_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
    public static void deletePhoneByContactId(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(PhoneContract.PHONE_TABLE, PhoneContract.PHONE_CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
}
