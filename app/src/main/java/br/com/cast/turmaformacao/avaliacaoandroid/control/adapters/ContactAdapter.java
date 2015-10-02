package br.com.cast.turmaformacao.avaliacaoandroid.control.adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.R;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;

/**
 * Created by Administrador on 01/10/2015.
 */
public abstract class ContactAdapter extends BaseAdapter {

    private List<Contact> listContact;
    private Activity context;

    public void setListContact(List<Contact> list){
        this.listContact.clear();
        this.listContact.addAll(list);
        notifyDataSetChanged();
    }

    public ContactAdapter(List<Contact> list, Activity context){
        this.listContact = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listContact.size();
    }

    @Override
    public Contact getItem(int i) {
        return listContact.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewReturn = context.getLayoutInflater().inflate(R.layout.contact_item, viewGroup, false);
        final Contact contact = getItem(i);
        ((TextView)viewReturn.findViewById(R.id.itemContactName)).setText(contact.getName());
        if(!contact.getEmailList().isEmpty())
            ((TextView)viewReturn.findViewById(R.id.itemFirstEmail)).setText(contact.getEmailList().get(0));
        if(!contact.getPhoneList().isEmpty())
            ((TextView)viewReturn.findViewById(R.id.itemFirstPhone)).setText(contact.getPhoneList().get(0));
        return viewReturn;
    }

    public abstract void onRemove(Contact product);

    public abstract void onEdit(Contact product);
}
