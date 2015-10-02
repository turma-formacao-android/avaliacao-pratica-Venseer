package br.com.cast.turmaformacao.avaliacaoandroid.control;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.ShapeDrawable;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import br.com.cast.turmaformacao.avaliacaoandroid.R;
import br.com.cast.turmaformacao.avaliacaoandroid.control.adapters.ContactAdapter;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.DialogAsyncTask;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service.ContactBusinessService;
import br.com.cast.turmaformacao.avaliacaoandroid.util.ConstantUtils;

public class AgendaActivity extends AppCompatActivity {

    private ListView listView;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_agenda);
        initialize();
        bindMethods();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndUpdateList();
        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }
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

    private void bindMethods(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Contact c = ((ContactAdapter)listView.getAdapter()).getItem(i);
                final FrameLayout frameView = new FrameLayout(AgendaActivity.this);
                final AlertDialog alertDialog = new AlertDialog.Builder(AgendaActivity.this)
                        .setView(frameView).create();
                LayoutInflater inflater = alertDialog.getLayoutInflater();
                View dialoglayout = inflater.inflate(R.layout.alert_contact, frameView);
                TextView name = (TextView)dialoglayout.findViewById(R.id.alertContactName);
                name.setText(c.getName());
                LinearLayout phoneScrollLinear = (LinearLayout)dialoglayout.findViewById(R.id.alertScrollViewPhones);
                for(final String phone : c.getPhoneList()){
                    LinearLayout linearLayoutAdd = new LinearLayout(AgendaActivity.this);
                    Drawable icon = getResources().getDrawable(android.R.drawable.sym_action_call);
                    ImageView imageView = new ImageView(AgendaActivity.this);
                    imageView.setImageDrawable(icon);
                    linearLayoutAdd.setOrientation(LinearLayout.HORIZONTAL);
                    TextView textView = new TextView(AgendaActivity.this);
                    textView.setText(phone);

                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_CALL);

                            intent.setData(Uri.parse("tel:" + phone.trim()));
                            AgendaActivity.this.startActivity(intent);
                        }
                    });

                    linearLayoutAdd.addView(imageView);
                    linearLayoutAdd.addView(textView);

                    linearLayoutAdd.setPadding(0,0,0,10);
                    phoneScrollLinear.addView(linearLayoutAdd);
                }
                LinearLayout emailScrollLinear = (LinearLayout)dialoglayout.findViewById(R.id.alertScrollViewEmails);
                for(String email : c.getEmailList()){
                    TextView textView = new TextView(AgendaActivity.this);
                    textView.setText(email);
                    emailScrollLinear.addView(textView);
                }
                LinearLayout socialNetworkNameScrollLinear = (LinearLayout)dialoglayout.findViewById(R.id.alertScrollViewSocialNetworksName);
                LinearLayout socialNetworkValueScrollLinear = (LinearLayout)dialoglayout.findViewById(R.id.alertScrollViewSocialNetworksValue);
                for(SocialNetwork sn : c.getSocialNetworks()){
                    TextView textView = new TextView(AgendaActivity.this);
                    textView.setText(sn.getSocialNetoworkName());
                    socialNetworkNameScrollLinear.addView(textView);
                    textView = new TextView(AgendaActivity.this);
                    textView.setText(sn.getValue());
                    socialNetworkValueScrollLinear.addView(textView);
                }
                TextView addressType = (TextView)dialoglayout.findViewById(R.id.alertAddressType);
                addressType.setText(c.getAddress().getAddressType());
                TextView address = (TextView)dialoglayout.findViewById(R.id.alertAddress);
                address.setText(c.getAddress().getAddress());
                TextView alertZipCode = (TextView)dialoglayout.findViewById(R.id.alertZipCode);
                alertZipCode.setText(c.getAddress().getZipCode());
                Button buttonDelete = (Button)dialoglayout.findViewById(R.id.alertButtonDelete);
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final DialogAsyncTask<Object, Void, Object> dialogAsyncTask =
                                new DialogAsyncTask<Object, Void, Object>(AgendaActivity.this) {
                            @Override
                            public void onComplete(Object result) {
                                getAndUpdateList();
                                alertDialog.dismiss();
                            }

                            @Override
                            protected Object doInBackground(Object[] objects) {
                                ContactBusinessService.deleteContact((Long)objects[0]);
                                return Void.TYPE;
                            }
                        };
                        dialogAsyncTask.execute(c.getId());
                    }
                });
                Button buttonEdit = (Button)dialoglayout.findViewById(R.id.alertButtonEdit);
                buttonEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pd = ProgressDialog.show(AgendaActivity.this, null, getString(R.string.generic_message_loading), true, false);
                        Intent changeScreenIntent = new Intent(AgendaActivity.this, AgendaFormActivity.class);
                        changeScreenIntent.putExtra(ConstantUtils.CONTACT_KEY, c);
                        startActivity(changeScreenIntent);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
    }
}
