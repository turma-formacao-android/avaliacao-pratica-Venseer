package br.com.cast.turmaformacao.avaliacaoandroid;

import android.app.Application;

import br.com.cast.turmaformacao.avaliacaoandroid.util.ApplicationUtil;

/**
 * Created by Administrador on 01/10/2015.
 */
public class Agenda extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtil.setContext(getApplicationContext());
    }
}
