package servicios;

import org.jasypt.util.text.BasicTextEncryptor;

public class Encriptamiento {

    public static String encriptar(String text){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("gs45hjd46d4d54whwhw".toCharArray());
        String myEncryptedText = textEncryptor.encrypt(text);
        return myEncryptedText;
    }

    public static String desencriptar(String text){
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPasswordCharArray("gs45hjd46d4d54whwhw".toCharArray());
        String plainText = textEncryptor.decrypt(text);

        return plainText;
    }
}
