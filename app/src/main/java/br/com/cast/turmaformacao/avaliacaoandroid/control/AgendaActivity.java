package br.com.cast.turmaformacao.avaliacaoandroid.control;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.R;
import br.com.cast.turmaformacao.avaliacaoandroid.control.adapters.ContactAdapter;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service.ContactBusinessService;

public class AgendaActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndUpdateList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_new) {
            Intent changeScreenIntent = new Intent(AgendaActivity.this, AgendaFormActivity.class);
            startActivity(changeScreenIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize(){
        listView = (ListView)findViewById(R.id.listViewContacts);
    }
    private void refreshList(List<Contact> list){
        ContactAdapter ca;
        if(listView.getAdapter() == null){
            ca = new ContactAdapter(list, this) {
                @Override
                public void onRemove(Contact product) {
                    getAndUpdateList();
                }

                @Override
                public void onEdit(Contact product) {
                    getAndUpdateList();
                }
            };
            listView.setAdapter(ca);
        }
        else{
            ca = (ContactAdapter)listView.getAdapter();
            ca.setListContact(list);
        }
    }

    private void getAndUpdateList(){
        refreshList(ContactBusinessService.getAll());
    }
}
