package io.submanager.converter;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;

public class CredentialConverter implements AttributeConverter<String, String> {

    @Autowired
    private StringEncryptor credentialEncryptor;

    @Override
    public String convertToDatabaseColumn(String unencryptedValue) {
        return credentialEncryptor.encrypt(unencryptedValue);
    }

    @Override
    public String convertToEntityAttribute(String encryptedValue) {
        return credentialEncryptor.decrypt(encryptedValue);
    }
}
