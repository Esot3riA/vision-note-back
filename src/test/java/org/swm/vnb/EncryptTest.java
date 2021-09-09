package org.swm.vnb;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EncryptTest {

    PooledPBEStringEncryptor encryptor;

    private String encryptKey = System.getenv("JASYPT_ENCRYPTOR_PASSWORD");

    @BeforeEach
    public void InitEncryptor() {
        encryptor = new PooledPBEStringEncryptor();

        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(encryptKey);
        config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
    }

    @Test
    public void EncryptAndDecryptTest() {
        String plainText = "plainText";
        System.out.println(System.getenv().entrySet());

        String encryptedText = encryptor.encrypt(plainText);
        System.out.println("Encrypted Text : \n" + encryptedText);
        String decryptedText = encryptor.decrypt(encryptedText);

        Assertions.assertEquals(plainText, decryptedText);
    }
}
