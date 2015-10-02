package br.com.cast.turmaformacao.avaliacaoandroid.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.R;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Address;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service.ContactBusinessService;

/**
 * Created by Administrador on 01/10/2015.
 */
public class AgendaFormActivity extends AppCompatActivity {

    EditText formName;
    EditText zipCode;
    EditText addressType;
    EditText address;
    EditText neighborhood;
    EditText city;
    EditText state;
    List<EditText> emailList;
    List<EditText> phoneList;
    List<EditText> socialNetworkListName;
    List<EditText> socialNetworkListValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda);
        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_agenda, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_new_form) {
            bindContactAndAdd();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void bindContactAndAdd() {
        Contact c = bindContact();
        ContactBusinessService.addContact(c);
    }

    @NonNull
    private Contact bindContact() {
        Contact c = new Contact();
        Address a = new Address();
        List<String> phones = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<SocialNetwork> sns = new ArrayList<>();
        c.setName(formName.getText().toString().trim());

        a.setNeighborhood(neighborhood.getText().toString().trim());
        a.setState(state.getText().toString().trim());
        a.setCity(city.getText().toString().trim());
        a.setZipCode(zipCode.getText().toString().trim());
        a.setAddress(address.getText().toString().trim());
        a.setAddressType(addressType.getText().toString().trim());

        for(EditText phone : phoneList){
            phones.add(phone.getText().toString().trim());
        }
        for(EditText email : emailList){
            emails.add(email.getText().toString().trim());
        }
        for(int i = 0; i < socialNetworkListName.size(); i++ ){
            SocialNetwork sn = new SocialNetwork();
            sn.setSocialNetoworkName(socialNetworkListName.get(i).getText().toString().trim());
            sn.setValue(socialNetworkListValue.get(i).getText().toString().trim());
            sns.add(sn);
        }

        c.setPhoneList(phones);
        c.setEmailList(emails);
        c.setAddress(a);
        c.setSocialNetworks(sns);
        return c;
    }

    private void initialize(){
        formName = (EditText)findViewById(R.id.formName);
        zipCode = (EditText)findViewById(R.id.formZipCode);
        addressType = (EditText)findViewById(R.id.formAddressType);
        address = (EditText)findViewById(R.id.formAddress);
        neighborhood = (EditText)findViewById(R.id.formNeighborhood);
        city = (EditText)findViewById(R.id.formCity);
        state = (EditText)findViewById(R.id.formState);
        emailList = new ArrayList<>();
        emailList.add((EditText)findViewById(R.id.formEmail));
        phoneList = new ArrayList<>();
        phoneList.add((EditText)findViewById(R.id.formPhone));
        socialNetworkListName = new ArrayList<>();
        socialNetworkListName.add((EditText)findViewById(R.id.formSocialName));
        socialNetworkListValue = new ArrayList<>();
        socialNetworkListValue.add((EditText)findViewById(R.id.formSocialValue));
    }
}
