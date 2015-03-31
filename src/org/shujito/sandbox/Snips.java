package org.shujito.sandbox;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;

public class Snips
{
    public static final String TAG = Snips.class.getSimpleName();
    
    @SuppressLint("TrulyRandom")
    public static void something() throws Exception
    {
        byte[] keyBytes = new byte[16];
        byte[] ivBytes = new byte[16];
        SecureRandom.getInstance("SHA1PRNG").nextBytes(keyBytes);
        SecureRandom.getInstance("SHA1PRNG").nextBytes(ivBytes);
        /*
        UUID key = UUID.randomUUID();
        UUID iv = UUID.randomUUID();
        byte[] keyBytes = ByteBuffer
            .allocate(16)
            .putLong(key.getMostSignificantBits())
            .putLong(key.getLeastSignificantBits())
            .array();
        byte[] ivBytes = ByteBuffer
            .allocate(16)
            .putLong(iv.getMostSignificantBits())
            .putLong(iv.getLeastSignificantBits())
            .array();
        //*/
        Cipher cipher = Cipher.getInstance("AES/CTR/NoPadding");
        SecretKeySpec sks = new SecretKeySpec(keyBytes, "AES");
        IvParameterSpec ivps = new IvParameterSpec(ivBytes);
        cipher.init(Cipher.ENCRYPT_MODE, sks, ivps);
        System.out.println("zipping");
        // zip and compress
        ZipOutputStream zos = new ZipOutputStream(
            new BufferedOutputStream(
                new CipherOutputStream(
                    new FileOutputStream("505.zfe"), cipher)));
        File folder = new File("505");
        File[] files = folder.listFiles();
        byte[] buffer = new byte[4096];
        for (File file : files)
        {
            System.out.println("fi:" + file.getName());
            ZipEntry ze = new ZipEntry(file.getName());
            zos.putNextEntry(ze);
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            int len = 0;
            while ((len = is.read(buffer)) > 0)
            {
                zos.write(buffer, 0, len);
            }
            is.close();
            zos.closeEntry();
        }
        zos.close();
        cipher = Cipher.getInstance("AES/CTR/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, sks, ivps);
        System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("unzipping");
        // traverse encrypted zip
        ZipInputStream zis = new ZipInputStream(
            new BufferedInputStream(
                new CipherInputStream(
                    new FileInputStream("505.zfe"), cipher)));
        ZipEntry ze = null;
        while ((ze = zis.getNextEntry()) != null)
        {
            System.out.println("fo:" + ze.getName());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len = 0;
            while ((len = zis.read(buffer)) > 0)
            {
                baos.write(buffer, 0, len);
            }
            baos.close();
            zis.closeEntry();
        }
        zis.close();
        // decipher encrypted zip
        InputStream is = new BufferedInputStream(new CipherInputStream(new FileInputStream("505.zfe"), cipher));
        OutputStream os = new BufferedOutputStream(new FileOutputStream("505.zip"));
        int len = 0;
        while ((len = is.read(buffer)) > 0)
        {
            os.write(buffer, 0, len);
        }
        is.close();
        os.close();
        //String keyString = Base64.encodeToString(keyBytes, Base64.NO_WRAP);
        //String ivString = Base64.encodeToString(ivBytes, Base64.NO_WRAP);
        //System.out.println("key:'" + keyString + "'");
        //System.out.println("iv:'" + ivString + "'");
    }
}
