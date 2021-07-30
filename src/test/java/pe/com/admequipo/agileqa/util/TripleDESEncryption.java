package pe.com.admequipo.agileqa.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

public class TripleDESEncryption {

	private static final String DE_SEDE = "DESede";

	public static DESedeKeySpec generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyGenerator keygen = KeyGenerator.getInstance(DE_SEDE);

		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DE_SEDE);

		return (DESedeKeySpec) keyfactory.getKeySpec(keygen.generateKey(), DESedeKeySpec.class);
	}

	public static DESedeKeySpec loadKey(byte[] rawKey) throws InvalidKeyException {
		return new DESedeKeySpec(rawKey);
	}

	public static String encrypt(String clearText, DESedeKeySpec keySpec)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		String cipherTextB64 = null;
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DE_SEDE);
		SecretKey key = keyfactory.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance(DE_SEDE);

		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] cipherText = cipher.doFinal(clearText.getBytes());
		
		org.apache.commons.codec.binary.Base64 b64=new org.apache.commons.codec.binary.Base64();
		cipherTextB64 = b64.encode(cipherText).toString();

		return cipherTextB64;
	}

	public static String decrypt(String cipherTextB64, DESedeKeySpec keySpec)
			throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, IOException {

		String clearText = null;
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(DE_SEDE);
		SecretKey key = keyfactory.generateSecret(keySpec);

		Cipher cipher = Cipher.getInstance(DE_SEDE);
		
		byte[] cipherText = Base64.getDecoder().decode(cipherTextB64);

		
		
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] bclearText = cipher.doFinal(cipherText);
		clearText = new String(bclearText);

		return clearText;
	}

}
