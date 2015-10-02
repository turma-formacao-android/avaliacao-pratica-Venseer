package br.com.cast.turmaformacao.avaliacaoandroid.model.enums;

/**
 * Created by Administrador on 01/10/2015.
 */
public enum SocialNetworkEnum {

    FACEBUK("Facebuk"),
    TWATTER("Twatter"),
    DUMBLR("Dumblr");

    private SocialNetworkEnum(String socialNetworkName){
        this.socialNetworkName = socialNetworkName;
    }

    private String socialNetworkName;

    public String getSocialNetworkName() {
        return socialNetworkName;
    }

    public SocialNetworkEnum searchByName(String name){
        for(SocialNetworkEnum value : values()){
            if(value.getSocialNetworkName().equals(name))
                return value;
        }
        return null;
    }
}
