package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Address;

/**
 * Created by Administrador on 27/07/2015.
 */
public final class CorreioService {
    private static final String URL = "http://correiosapi.apphb.com/cep/";

    private CorreioService() {
        super();
    }

    public static Address getAddressByZipCode(String cep) {
        Address clientAddress = null;
        try {
            java.net.URL url = new URL(URL + cep);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("ACCEPT", "APPLICATION/JSON");
            int responseCode = conn.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            InputStream inputStream = conn.getInputStream();

            ObjectMapper objectMapper = new ObjectMapper();
            clientAddress = objectMapper.readValue(inputStream, Address.class);
            conn.disconnect();
        } catch (IOException e) {
            Log.e(CorreioService.class.getSimpleName() + ".getAddressByZipCode", e.getMessage());
        }
        return clientAddress;
    }
}
