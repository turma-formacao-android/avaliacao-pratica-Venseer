package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Address;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.DatabaseHelper;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.AddressContract;

/**
 * Created by Administrador on 01/10/2015.
 */
public class AddressRepository {

    public static Address getAddress(Long idContact){
        Address address = new Address();
        String[] whereArgs = {idContact.toString()};
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase reader = databaseHelper.getReadableDatabase();
        Cursor c = reader.query(AddressContract.ADDRESS_TABLE, AddressContract.colums,
                AddressContract.ADDRESS_CONTACT_ID+" = ?", whereArgs, null, null, null);
        if(c.isBeforeFirst() || c.moveToNext())
            address = AddressContract.getAddressFromCursor(c);
        reader.close();
        databaseHelper.close();
        return address;
    }

    public static void addAddress(Address ad, Long idContact){
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
            SQLiteDatabase writer = databaseHelper.getWritableDatabase();
            writer.insert(AddressContract.ADDRESS_TABLE, null, AddressContract.getContentValues(ad, idContact));
            writer.close();
            databaseHelper.close();
        }catch(Exception e){
            Log.e(AddressRepository.class.getSimpleName(), e.getMessage());
        }
    }

    public static void deleteAddress(Long id){
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance();
        SQLiteDatabase writer = databaseHelper.getWritableDatabase();
        String[] whereArgs = {id.toString()};
        writer.delete(AddressContract.ADDRESS_TABLE, AddressContract.ADDRESS_CONTACT_ID + " = ?", whereArgs);
        writer.close();
        databaseHelper.close();
    }
    
}
