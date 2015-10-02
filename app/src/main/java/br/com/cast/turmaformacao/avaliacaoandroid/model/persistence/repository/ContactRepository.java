package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.ContactContract;

/**
 * Created by Administrador on 01/10/2015.
 */
public class ContactRepository {

    public static List<Contact> getAll(){
        List<Contact> list = new ArrayList<Contact>();
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase reader = databaseHelper.getReadableDatabase();
        Cursor c = reader.query(ContactContract.CONTACT_TABLE, ContactContract.colums, null, null,
                null, null, null);
        while(c.moveToNext()){
            list.add(ContactContract.getContactFromCursor(c));
        }
        reader.close();
        databaseHelper.close();
        return list;
    }

    public static Long addContact(Contact p){
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase writer = databaseHelper.getWritableDatabase();
            Long id = writer.insert(ContactContract.CONTACT_TABLE, null, ContactContract.getContentValues(p));
            writer.close();
            databaseHelper.close();
            return id;
        }catch(Exception e){
            Log.e(ContactRepository.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public static void deleteContact(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(ContactContract.CONTACT_TABLE, ContactContract.CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }

    public static void updateContact(Contact c){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {c.getId().toString()};
        writer.update(ContactContract.CONTACT_TABLE, ContactContract.getContentValues(c),
                ContactContract.CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
}
