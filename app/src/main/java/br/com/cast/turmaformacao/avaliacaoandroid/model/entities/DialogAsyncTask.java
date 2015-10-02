package br.com.cast.turmaformacao.avaliacaoandroid.model.entities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Administrador on 30/09/2015.
 */
public abstract class DialogAsyncTask<Params, Progress, Result> extends AsyncTask {

    Context context;
    ProgressDialog p;

    public DialogAsyncTask(Context c) {
        context = c;
        p = ProgressDialog.show(c, null, "Loading...", true, false);
    }

    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (p.isShowing()) {
            p.dismiss();
        }
        onComplete(o);
    }

    public abstract void onComplete(Object result);

}
