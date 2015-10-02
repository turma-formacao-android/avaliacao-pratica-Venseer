package br.com.cast.turmaformacao.avaliacaoandroid.control;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.R;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Address;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.DialogAsyncTask;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service.ContactBusinessService;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service.CorreioService;
import br.com.cast.turmaformacao.avaliacaoandroid.util.ConstantUtils;
import br.com.cast.turmaformacao.avaliacaoandroid.util.FormHelper;

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
    ImageButton    addEmailButton;
    ImageButton    addPhoneButton;
    ImageButton    addSocialButton;
    ImageButton    zipCodeButton;

    private Contact contactForm;

    private Boolean isEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_agenda);
        initialize();
        bindMethods();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_agenda, menu);
        MenuItem menuItem = (MenuItem) menu.findItem(R.id.action_add_new_form);
        if(isEditMode)
            menuItem.setTitle(R.string.generic_message_edit);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_new_form) {
            if (FormHelper.validateRequired("Required", formName, zipCode, addressType,
                    address, neighborhood, city, state)) {
                if (FormHelper.validateRequiredList("Required", phoneList, emailList,
                        socialNetworkListName, socialNetworkListValue)) {
                    DialogAsyncTask asyncTask = new DialogAsyncTask<Object, Void, Void>(AgendaFormActivity.this) {
                        @Override
                        public void onComplete(Object result) {
                            AgendaFormActivity.this.finish();
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            bindContactAndAddOrEdit();
                            return null;
                        }
                    };
                    asyncTask.execute();
                    return true;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindContactAndAddOrEdit() {
        Contact c = bindContact();
        ContactBusinessService.addContactOrEdit(c);
    }

    @NonNull
    private Contact bindContact() {
        if(contactForm == null) {
            contactForm = new Contact();
        }
        Address a = new Address();
        List<String> phones = new ArrayList<>();
        List<String> emails = new ArrayList<>();
        List<SocialNetwork> sns = new ArrayList<>();
        contactForm.setName(formName.getText().toString().trim());

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

        contactForm.setPhoneList(phones);
        contactForm.setEmailList(emails);
        contactForm.setAddress(a);
        contactForm.setSocialNetworks(sns);
        return contactForm;
    }

    private void initialize(){
        isEditMode = false;
        formName = (EditText)findViewById(R.id.formName);
        zipCode = (EditText)findViewById(R.id.formZipCode);
        addressType = (EditText)findViewById(R.id.formAddressType);
        address = (EditText)findViewById(R.id.formAddress);
        neighborhood = (EditText)findViewById(R.id.formNeighborhood);
        city = (EditText)findViewById(R.id.formCity);
        state = (EditText)findViewById(R.id.formState);
        emailList = new ArrayList<>();
        emailList.add((EditText) findViewById(R.id.formEmail));
        phoneList = new ArrayList<>();
        phoneList.add((EditText) findViewById(R.id.formPhone));
        socialNetworkListName = new ArrayList<>();
        socialNetworkListName.add((EditText)findViewById(R.id.formSocialName));
        socialNetworkListValue = new ArrayList<>();
        socialNetworkListValue.add((EditText) findViewById(R.id.formSocialValue));
        addEmailButton = (ImageButton)findViewById(R.id.buttonNewEmail);
        addPhoneButton = (ImageButton)findViewById(R.id.buttonNewPhone);
        addSocialButton = (ImageButton)findViewById(R.id.buttonNewSocial);
        zipCodeButton = (ImageButton)findViewById(R.id.buttonSearchZipCode);

        if(getIntent().getExtras() != null && !getIntent().getExtras().isEmpty()){
            isEditMode = true;
            contactForm = new Contact();
            final Contact c = getIntent().getExtras().getParcelable(ConstantUtils.CONTACT_KEY);
            final Address contactAddress = c.getAddress();
            contactForm.setId(c.getId());
            formName.setText(c.getName());
            zipCode.setText(contactAddress.getZipCode());
            addressType.setText(contactAddress.getAddressType());
            address.setText(contactAddress.getAddress());
            neighborhood.setText(contactAddress.getNeighborhood());
            city.setText(contactAddress.getCity());
            state.setText(contactAddress.getState());
            for(int i = 0; i < c.getPhoneList().size(); i++){
                if(i == 0){
                    ((EditText) findViewById(R.id.formPhone)).setText(c.getPhoneList().get(i));
                }
                else{
                    final LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutPhone);
                    EditText editText = new EditText(AgendaFormActivity.this);
                    editText.setText(c.getPhoneList().get(i));
                    editText.setInputType(InputType.TYPE_CLASS_PHONE);
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (!b && ((EditText) view).getText().toString().trim().length() == 0) {
                                layout.removeView(view);
                                phoneList.remove(view);
                            }
                        }
                    });
                    layout.addView(editText);
                    phoneList.add(editText);
                }
            }
            for(int i = 0; i < c.getEmailList().size(); i++){
                if(i == 0){
                    ((EditText) findViewById(R.id.formEmail)).setText(c.getEmailList().get(i));
                }
                else{
                    final LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutEmail);
                    EditText editText = new EditText(AgendaFormActivity.this);
                    editText.setText(c.getEmailList().get(i));
                    editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            if (!b && ((EditText) view).getText().toString().trim().length() == 0) {
                                layout.removeView(view);
                                emailList.remove(view);
                            }
                        }
                    });
                    layout.addView(editText);
                    emailList.add(editText);
                }
            }
            for(int i = 0; i < c.getSocialNetworks().size(); i++){
                if(i == 0){
                    ((EditText) findViewById(R.id.formSocialName)).setText(c.getSocialNetworks()
                            .get(i).getSocialNetoworkName());
                    ((EditText) findViewById(R.id.formSocialValue)).setText(c.getSocialNetworks()
                            .get(i).getValue());
                }
                else{
                    final LinearLayout layoutName = (LinearLayout)findViewById(R.id.linearLayoutSocialNetwork);
                    final LinearLayout layoutValue = (LinearLayout)findViewById(R.id.linearLayoutSocialValue);
                    EditText editText = new EditText(AgendaFormActivity.this);
                    editText.setText(c.getSocialNetworks().get(i).getSocialNetoworkName());
                    layoutName.addView(editText);
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            EditText networkName = (EditText) view;
                            EditText networkValue = socialNetworkListValue.get(socialNetworkListName.indexOf(view));
                            if (!b &&
                                    networkName.getText().toString().trim().length() == 0
                                    &&
                                    networkValue.getText().toString().trim().length() == 0) {
                                layoutName.removeView(networkName);
                                layoutValue.removeView(networkValue);
                                socialNetworkListName.remove(networkName);
                                socialNetworkListValue.remove(networkValue);
                            }
                        }
                    });
                    socialNetworkListName.add(editText);
                    editText = new EditText(AgendaFormActivity.this);
                    editText.setText(c.getSocialNetworks().get(i).getValue());
                    editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            EditText networkValue = (EditText) view;
                            EditText networkName = socialNetworkListName.get(socialNetworkListValue.indexOf(view));
                            if (!b &&
                                    networkName.getText().toString().trim().length() == 0
                                    &&
                                    networkValue.getText().toString().trim().length() == 0) {
                                layoutName.removeView(networkName);
                                layoutValue.removeView(networkValue);
                                socialNetworkListName.remove(networkName);
                                socialNetworkListValue.remove(networkValue);
                            }
                        }
                    });
                    layoutValue.addView(editText);
                    socialNetworkListValue.add(editText);
                }
            }
        }
    }

    private void bindMethods(){
        addEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutEmail);
                EditText newField = new EditText(AgendaFormActivity.this);
                newField.setHint(R.string.formEmail);
                newField.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                newField.setOnFocusChangeListener(new View.OnFocusChangeListener(){
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b && ((EditText)view).getText().toString().trim().length() == 0){
                            layout.removeView(view);
                            emailList.remove(view);
                        }
                    }
                });
                layout.addView(newField);
                emailList.add(newField);
            }
        });
        addPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout layout = (LinearLayout)findViewById(R.id.linearLayoutPhone);
                EditText newField = new EditText(AgendaFormActivity.this);
                newField.setHint(R.string.formPhoneNumber);
                newField.setInputType(InputType.TYPE_CLASS_PHONE);
                newField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        if(!b && ((EditText)view).getText().toString().trim().length() == 0){
                            layout.removeView(view);
                            phoneList.remove(view);
                        }
                    }
                });
                layout.addView(newField);
                phoneList.add(newField);
            }
        });
        addSocialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final LinearLayout layoutSocialNetwork = (LinearLayout)findViewById(R.id.linearLayoutSocialNetwork);
                final LinearLayout layoutSocialValue = (LinearLayout)findViewById(R.id.linearLayoutSocialValue);
                EditText newNetworkField = new EditText(AgendaFormActivity.this);
                EditText newValueField = new EditText(AgendaFormActivity.this);


                newNetworkField.setHint(R.string.formSocialNetwork);
                newValueField.setHint(R.string.formSocialNetworkValue);
                layoutSocialNetwork.addView(newNetworkField);
                layoutSocialValue.addView(newValueField);

                newNetworkField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        EditText networkName = (EditText) view;
                        EditText networkValue = socialNetworkListValue.get(socialNetworkListName.indexOf(view));
                        if (!b &&
                                networkName.getText().toString().trim().length() == 0
                                &&
                                networkValue.getText().toString().trim().length() == 0) {
                            layoutSocialNetwork.removeView(networkName);
                            layoutSocialValue.removeView(networkValue);
                            socialNetworkListName.remove(networkName);
                            socialNetworkListValue.remove(networkValue);
                        }
                    }
                });

                newValueField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        EditText networkValue = (EditText) view;
                        EditText networkName = socialNetworkListName.get(socialNetworkListValue.indexOf(view));
                        if(!b &&
                                networkName.getText().toString().trim().length() == 0
                                &&
                                networkValue.getText().toString().trim().length() == 0){
                            layoutSocialNetwork.removeView(networkName);
                            layoutSocialValue.removeView(networkValue);
                            socialNetworkListName.remove(networkName);
                            socialNetworkListValue.remove(networkValue);
                        }
                    }
                });

                socialNetworkListName.add(newNetworkField);
                socialNetworkListValue.add(newValueField);
            }
        });
        zipCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogAsyncTask<Object, Void, Object> dialogAsyncTask = new DialogAsyncTask<Object, Void, Object>(AgendaFormActivity.this) {
                    @Override
                    public void onComplete(Object result) {
                        if (result != null) {
                            Address ad = (Address) result;
                            zipCode.setText(ad.getZipCode());
                            addressType.setText(ad.getAddressType());
                            address.setText(ad.getAddress());
                            neighborhood.setText(ad.getNeighborhood());
                            city.setText(ad.getCity());
                            state.setText(ad.getState());
                        }
                    }

                    @Override
                    protected Object doInBackground(Object[] objects) {
                        String zipCode = objects[0].toString().trim();
                        if (!zipCode.isEmpty())
                            return CorreioService.getAddressByZipCode(zipCode);
                        else
                            return null;
                    }
                };
                dialogAsyncTask.execute(zipCode.getText());
            }
        });
    }
}
