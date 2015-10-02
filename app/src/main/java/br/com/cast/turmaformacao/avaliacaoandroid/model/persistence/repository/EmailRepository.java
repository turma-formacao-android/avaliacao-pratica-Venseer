package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.EmailContract;

/**
 * Created by Administrador on 01/10/2015.
 */
public class EmailRepository {
    public static List<String> getAll(Long idContact){
        List<String> list = new ArrayList<String>();
        String[] whereArgs = {idContact.toString()};
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase reader = databaseHelper.getReadableDatabase();
        Cursor c = reader.query(EmailContract.EMAIL_TABLE, EmailContract.colums,
                EmailContract.EMAIL_CONTACT_ID+" = ?", whereArgs, null, null, null);
        while(c.moveToNext()){
            list.add(EmailContract.getEmailFromCursor(c));
        }
        reader.close();
        databaseHelper.close();
        return list;
    }

    public static Long addEmail(String email, Long idContact){
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase writer = databaseHelper.getWritableDatabase();
            Long id = writer.insert(EmailContract.EMAIL_TABLE, null, EmailContract.getContentValues(email, idContact));
            writer.close();
            databaseHelper.close();
            return id;
        }catch(Exception e){
            Log.e(EmailRepository.class.getSimpleName(), e.getMessage());
        }
        return null;
    }

    public static void deleteEmail(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(EmailContract.EMAIL_TABLE, EmailContract.EMAIL_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
    public static void deleteEmailByContactId(Long idContact){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {idContact.toString()};
        writer.delete(EmailContract.EMAIL_TABLE, EmailContract.EMAIL_CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
}
