package de.minimum.hawapp.app.login;

/**
 * http://www.androidsnippets.com/encrypt-decrypt-between-android-and-php
 */
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MCrypt {

    // Dummy iv
    private final String iv = "ahchieshu3aideem";
    private final IvParameterSpec ivspec;
    private final SecretKeySpec keyspec;
    private Cipher cipher;

    // Dummy secretKey
    private final String SecretKey = "paengi9mahxiique";

    public MCrypt() {
        ivspec = new IvParameterSpec(iv.getBytes());

        keyspec = new SecretKeySpec(SecretKey.getBytes(), "AES");

        try {
            cipher = Cipher.getInstance("AES/CBC/NoPadding");
        }
        catch(final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch(final NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(final String text) throws Exception {
        if (text == null || text.length() == 0) {
            throw new Exception("Empty string");
        }

        byte[] encrypted = null;

        try {
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

            encrypted = cipher.doFinal(padString(text).getBytes());
        }
        catch(final Exception e) {
            throw new Exception("[encrypt] " + e.getMessage());
        }

        return encrypted;
    }

    public byte[] decrypt(final String code) throws Exception {
        if (code == null || code.length() == 0) {
            throw new Exception("Empty string");
        }

        byte[] decrypted = null;

        try {
            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            decrypted = cipher.doFinal(hexToBytes(code));
        }
        catch(final Exception e) {
            throw new Exception("[decrypt] " + e.getMessage());
        }
        return decrypted;
    }

    public static String bytesToHex(final byte[] data) {
        if (data == null) {
            return null;
        }

        final int len = data.length;
        String str = "";
        for(int i = 0; i < len; i++) {
            if ((data[i] & 0xFF) < 16) {
                str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
            }
            else {
                str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
            }
        }
        return str;
    }

    public static byte[] hexToBytes(final String str) {
        if (str == null) {
            return null;
        }
        else if (str.length() < 2) {
            return null;
        }
        else {
            final int len = str.length() / 2;
            final byte[] buffer = new byte[len];
            for(int i = 0; i < len; i++) {
                buffer[i] = (byte)Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
            }
            return buffer;
        }
    }

    private static String padString(String source) {
        final char paddingChar = ' ';
        final int size = 16;
        final int x = source.length() % size;
        final int padLength = size - x;

        for(int i = 0; i < padLength; i++) {
            source += paddingChar;
        }

        return source;
    }
}