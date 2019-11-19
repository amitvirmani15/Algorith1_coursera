import org.bouncycastle.cms.CMSSignedData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class GemAuthUtil {

    private static final Logger LOG = LoggerFactory.getLogger(GemAuthUtil.class);

    private static final Base64.Decoder DECODER = Base64.getDecoder();

    //TODO : gkanika HD-25836 separate public certificate for production env once gem agreed public certificate is saved into system
    private static final String PUBLIC_CERTIFICATE_NAME = "public-certificate.crt";

    private static final String PUBLIC_CERTIFICATE_TYPE = "X.509";

    private static final String TYPE_OF_HEADER = "CMS";

    private static final String SIGNATURE_ALGORITHM = "SHA512withRSA";

    private static final String CERTIFICATE_DEFAULT_ENV = "dev";


    public static void authenticate(String httpHeader, String message, String ipAddress) throws Exception {
        boolean verified = false;
        // Remove header type prefixed in the passed header
        String signedCMSHeader = validateAndRemovePrefix(httpHeader, ipAddress);
        // Decoding signature which is Base64 encoded header
        byte[] signature = DECODER.decode(signedCMSHeader.getBytes());

        CMSSignedData cmsSignedData = new CMSSignedData(signature);
        //SignerInformationStore signers = cmsSignedData.getSignerInfos();
        //Collection signersInformation = signers.getSigners();


        if(!verified) {
            LOG.error("Request from {} is unauthenticated! Signature could not be verified.", ipAddress);

        }
    }

    private static PublicKey getPublicKey() throws Exception {
        CertificateFactory fact = CertificateFactory.getInstance(PUBLIC_CERTIFICATE_TYPE);
        String file;
        ClassLoader classLoader = GemAuthUtil.class.getClassLoader();
        URL resource = classLoader.getResource(PUBLIC_CERTIFICATE_NAME);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            file = resource.getFile();
        }
        FileInputStream is = new FileInputStream(file);
        X509Certificate publicKeyCertificate = (X509Certificate) fact.generateCertificate(is);
        return publicKeyCertificate.getPublicKey();
    }

    private static String validateAndRemovePrefix(String httpHeader, String ipAddress){
        if (httpHeader.startsWith(TYPE_OF_HEADER)) {
            return httpHeader.split(" ")[1];
        }
        else {
            LOG.error("Invalid authorization header detected! Request from {} must include CMS signed Header.", ipAddress);
            return null;
        }
    }

    private static String readPrivateKeyFromResources() {
        String file;
        ClassLoader classLoader = GemAuthUtil.class.getClassLoader();
        URL resource = classLoader.getResource("hydra_dev.pfx");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            file = resource.getFile();
        }
        return file;
    }

    public static void main(String args[]) throws Exception {

        String header = "CMS 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";
        System.out.println(header);
        authenticate(header, "HelloWorld", "127.0.0.1");
    }
}
