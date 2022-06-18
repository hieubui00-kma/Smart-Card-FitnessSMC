package com.kma.fitnesssmc.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {

    public static @Nullable PublicKey generatePublicKey(byte[] exponentBytes, byte[] modulusBytes) {
        BigInteger exponent = new BigInteger(1, exponentBytes);
        BigInteger modulus = new BigInteger(1, modulusBytes);

        try {
            RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(modulus, exponent);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static @Nullable PublicKey generatePublicKey(byte[] data) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(data);
            KeyFactory factory = KeyFactory.getInstance("RSA");

            return factory.generatePublic(x509EncodedKeySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean accuracy(byte[] signature, @NotNull PublicKey publicKey, @NotNull String code) {
        try {
            Signature verifier = Signature.getInstance("SHA1withRSA");

            verifier.initVerify(publicKey);
            verifier.update(code.getBytes());
            return verifier.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException e) {
            e.printStackTrace();
            return false;
        }
    }
}
