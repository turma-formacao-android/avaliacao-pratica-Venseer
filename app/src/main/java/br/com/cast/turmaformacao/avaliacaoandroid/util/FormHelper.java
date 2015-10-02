package br.com.cast.turmaformacao.avaliacaoandroid.util;

import android.widget.EditText;

/**
 * Created by Tass on 21/09/2015.
 */
public final class FormHelper {

    private FormHelper(){
        super();
    }

    public static Boolean validateRequired(String requiredMessage, EditText... editTexts){
        boolean isValid = true;
        for (EditText editText: editTexts){
            if(editText.getText().toString().trim().isEmpty()){
                editText.setError(requiredMessage);
                isValid = false;
            }
        }
        return isValid;
    }
    public static Boolean validadeEqualFields(String equalFieldsMessage, EditText... editTexts){
        boolean isValid = true;
        String temporaryValue = null;
        for (EditText editText: editTexts){
            if(temporaryValue == null){
                temporaryValue = editText.getText().toString().trim();
            }
            else{
                if(!temporaryValue.equals(editText.getText().toString().trim())){
                    editText.setError(equalFieldsMessage);
                    isValid = false;
                }
            }
        }
        return isValid;
    }
}
