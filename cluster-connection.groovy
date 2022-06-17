import org.apache.jmeter.util.JMeterUtils;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import com.datastax.oss.driver.api.core.CqlSession;

def createSSLHandler(String certfile) {
	try {
   	CertificateFactory cf = CertificateFactory.getInstance("X.509");
    FileInputStream fis = new FileInputStream(certfile);
    X509Certificate ca;
    try {
      ca = (X509Certificate) cf.generateCertificate(fis);
    } catch (Exception e) {
      log.error("Exception generating certificate from input file: " + e);
      return null;
    } finally {
      fis.close();
    }

    // Create a KeyStore containing our trusted CAs
    String keyStoreType = KeyStore.getDefaultType();
    KeyStore keyStore = KeyStore.getInstance(keyStoreType);
    keyStore.load(null, null);
    keyStore.setCertificateEntry("ca", ca);

    // Create a TrustManager that trusts the CAs in our KeyStore
    String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
    tmf.init(keyStore);

    SSLContext sslContext = SSLContext.getInstance("TLS");
    sslContext.init(null, tmf.getTrustManagers(), null);
    return sslContext;
	} catch (Exception e) {
    log.error("Exception creating sslContext: " + e);
    return null;
	}
}

String nodes = vars.get("clusterNodes");
String keyspace = vars.get("keyspace");
String localDc = vars.get("localDc");
String cert = vars.get("cert");
String user = vars.get("user");
String pass = vars.get("password");

CqlSession session = CqlSession.builder()
  .addContactPoint(new InetSocketAddress(nodes, 9042))
  .withSslContext(createSSLHandler(cert))
  .withAuthCredentials(user, pass)
  .withLocalDatacenter(localDc)
  .build();

// global JMeter variables are shared in the same ThreadGroup but session
// must be shared by all ThreadGroups
// => use JMeter properties hashtable in order to reuse it in other scripts
Properties properties = JMeterUtils.getJMeterProperties();
properties.put("session",session);