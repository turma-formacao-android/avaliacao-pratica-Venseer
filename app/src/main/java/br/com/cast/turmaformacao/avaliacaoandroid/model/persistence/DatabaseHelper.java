package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.AddressContract;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.ContactContract;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.EmailContract;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.PhoneContract;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.contract.SocialNetworkContract;
import br.com.cast.turmaformacao.avaliacaoandroid.util.ApplicationUtil;


/**
 * Created by Tass on 21/09/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "addressbook";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHelper getInstance(){
        return new DatabaseHelper(ApplicationUtil.getContext());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContactContract.getCreateTableScript());
        db.execSQL(EmailContract.getCreateTableScript());
        db.execSQL(SocialNetworkContract.getCreateTableScript());
        db.execSQL(AddressContract.getCreateTableScript());
        db.execSQL(PhoneContract.getCreateTableScript());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
