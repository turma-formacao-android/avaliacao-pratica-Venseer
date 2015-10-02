package br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.service;

import java.util.List;

import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.Contact;
import br.com.cast.turmaformacao.avaliacaoandroid.model.entities.SocialNetwork;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository.AddressRepository;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository.ContactRepository;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository.EmailRepository;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository.PhoneRepository;
import br.com.cast.turmaformacao.avaliacaoandroid.model.persistence.repository.SocialNetworkRepository;

/**
 * Created by Administrador on 01/10/2015.
 */
public final class ContactBusinessService {

    public static void addContact(Contact c){
        Long id = ContactRepository.addContact(c);
        AddressRepository.addAddress(c.getAddress(), id);
        for(String email : c.getEmailList()){
            EmailRepository.addEmail(email, id);
        }
        for (String phone : c.getPhoneList()){
            PhoneRepository.addPhone(phone, id);
        }
        for (SocialNetwork sn : c.getSocialNetworks()){
            SocialNetworkRepository.addSocialNetwork(sn, id);
        }
    }

    public static List<Contact> getAll(){
        List<Contact> list = ContactRepository.getAll();
        for (Contact c : list) {
            Long idContact = c.getId();
            c.setEmailList(EmailRepository.getAll(idContact));
            c.setAddress(AddressRepository.getAddress(idContact));
            c.setPhoneList(PhoneRepository.getAll(idContact));
            c.setSocialNetworks(SocialNetworkRepository.getAll(idContact));
        }
        return list;
    }

    public static void deleteContact(Long id){
        EmailRepository.deleteEmailByContactId(id);
        PhoneRepository.deletePhoneByContactId(id);
        AddressRepository.deleteAddress(id);
        SocialNetworkRepository.deleteSocialNetworkByContactId(id);
        ContactRepository.deleteContact(id);
    }
}
