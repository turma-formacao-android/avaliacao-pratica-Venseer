package br.com.cast.turmaformacao.avaliacaoandroid.util;

import android.content.Context;

/**
 * Created by Tass on 21/09/2015.
 */
public class ApplicationUtil {

    private static Context applicationContext;
    private ApplicationUtil(){
        super();
    }

    public static void setContext(Context context){
        applicationContext = context;
    }
    public static Context getContext(){
        return applicationContext;
    }
}
