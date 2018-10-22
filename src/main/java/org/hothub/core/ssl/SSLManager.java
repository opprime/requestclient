package org.hothub.core.ssl;

import org.hothub.pojo.FileBody;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by org.hothub on 2018/03/02.
 */
public class SSLManager {

    public static class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }



    public static class TrustTargetCerts implements X509TrustManager {

        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public TrustTargetCerts(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(trustManagerFactory.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }

    }


    public static class TrustAllHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }


    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslFactory = null;

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,  new TrustManager[] { new TrustAllCerts() }, new SecureRandom());

            sslFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslFactory;
    }






    private static final String CLIENT_KET_PASSWORD = "Mg3QMEckjDwx58cZ";

    public static SSLSocketFactory createSSLSocketFactory(FileBody ceritficate) {
        SSLSocketFactory sslFactory = null;

        try {
            char[] password = CLIENT_KET_PASSWORD.toCharArray(); // Any password will work.

            KeyStore keyStore = initKeyStore(ceritficate, password);
            KeyManagerFactory keyManagerFactory = keyManagerFactory(keyStore, password);

            TrustManager[] trustManagers = trustTargetCerts(keyStore);


            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers,null);

            sslFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sslFactory;
    }




    private static KeyStore initKeyStore(FileBody fileBody, char[] password) throws FileNotFoundException, GeneralSecurityException {
        if (fileBody == null || fileBody.isEmpty()) {
            throw new IllegalArgumentException("please provide non-empty fileBody of trusted certificates");
        }


        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");

        FileInputStream inputStream = new FileInputStream(fileBody.getFile());
        Collection<? extends Certificate> certificates =
                fileBody.getFile() != null
                        ? certificateFactory.generateCertificates(inputStream)
                        : certificateFactory.generateCertificates(new ByteArrayInputStream(fileBody.getFileByte()));


        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }



        KeyStore keyStore = newEmptyKeyStore(fileBody, password);


        int index = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        return keyStore;
    }



    private static KeyManagerFactory keyManagerFactory(KeyStore keyStore, char[] password) throws GeneralSecurityException {
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);

        return keyManagerFactory;
    }

    private static TrustManager[] trustTargetCerts(KeyStore keyStore) throws KeyStoreException, NoSuchAlgorithmException {
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        }

        return trustManagers;
    }



    private static KeyStore newEmptyKeyStore(FileBody fileBody, char[] password) throws GeneralSecurityException {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());

            InputStream in = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }



    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }

        return null;
    }


}
