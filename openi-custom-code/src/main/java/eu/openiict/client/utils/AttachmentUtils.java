

package eu.openiict.client.utils;


import eu.openiict.client.common.ApiException;
import eu.openiict.client.common.ApiInvoker;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.GeneralSecurityException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;


/**
 *
 * @author dmccarthy
 */
public class AttachmentUtils {

   private HttpClient              client                     = null;
   private boolean                 ignoreSSLCertificates      = false;
   private ApiInvoker              apiInvoker                 = ApiInvoker.getInstance();
   private ClientConnectionManager ignoreSSLConnectionManager;
   private String                  basePath;

   protected AttachmentUtils() {
      initConnectionManager();
   }


   public void setBasePath( String basePath ) {
      this.basePath = basePath;
   }
   

   private HttpClient initClient(String host) {
      if (client == null) {
         if (ignoreSSLCertificates && ignoreSSLConnectionManager != null) {
            // Trust self signed certificates
            client = new DefaultHttpClient(ignoreSSLConnectionManager, new BasicHttpParams());
         } else {
            client = new DefaultHttpClient();
         }
      }
   return client;
   }


   public void setIgnoreSSLCertificates( boolean ignoreSSLCertificates ) {
      this.ignoreSSLCertificates = ignoreSSLCertificates;
   }
   
   
   public InputStream getAttachmentInputStream (String cloudletId, String attachmentId) throws ApiException, IOException {
   
      final HttpEntity   resEntity = this.getAttachment( cloudletId, attachmentId );
      
      return resEntity.getContent();
   }
   
   
   public File getAttachmentFile (File file, String cloudletId, String attachmentId) throws ApiException, IOException {
    
      if (file.exists()){
         throw new ApiException(400, "file already exists: " + file.getAbsolutePath());
      }
      
      final HttpEntity   resEntity = this.getAttachment(cloudletId, attachmentId );

      final BufferedHttpEntity bhe = new BufferedHttpEntity(resEntity );
      final FileOutputStream   os  = new FileOutputStream(file);

      bhe.writeTo(os);

      while (bhe.isStreaming()) {
         bhe.writeTo(os);
      } 
      
      return file;
   }
   
   
   private HttpEntity getAttachment (String cloudletId, String attachmentId) throws ApiException, IOException {
    // verify required params are set
      if(cloudletId == null || attachmentId == null ) {
         throw new ApiException(400, "missing required params");
      }
      else if ( !OPENiUtils.isCloudletId( cloudletId )){
         throw new ApiException(400, "invalid cloudletId format: " + cloudletId);
      }
      else if (!OPENiUtils.isAttachmentId( attachmentId )){
         throw new ApiException(400, "invalid attachmentId format: " + attachmentId);
      }

      // create path and map variables
      final String path = "/attachments/{cloudletId}/{attachmentId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "attachmentId" + "\\}", apiInvoker.escapeString(attachmentId.toString()));
      
      initClient(basePath);

      final String url = basePath + path;

      final HttpGet get = new HttpGet(url);
      get.addHeader("Accept", "application/json");

      final HttpResponse response  = client.execute(get); 
      
      return response.getEntity();
   }
   
   
  private void initConnectionManager() {
    try {
      final SSLContext sslContext = SSLContext.getInstance("SSL");

      // set up a TrustManager that trusts everything
      TrustManager[] trustManagers = new TrustManager[] {
        new X509TrustManager() {
          public X509Certificate[] getAcceptedIssuers() {
            return null;
          }
          public void checkClientTrusted(X509Certificate[] certs, String authType) {}
          public void checkServerTrusted(X509Certificate[] certs, String authType) {}
      }};

      sslContext.init(null, trustManagers, new SecureRandom());

      SSLSocketFactory sf = new SSLSocketFactory((KeyStore)null) {
        private javax.net.ssl.SSLSocketFactory sslFactory = sslContext.getSocketFactory();

        public Socket createSocket(Socket socket, String host, int port, boolean autoClose)
          throws IOException, UnknownHostException {
          return sslFactory.createSocket(socket, host, port, autoClose);
        }

        public Socket createSocket() throws IOException {
          return sslFactory.createSocket();
        }
      };

      sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      Scheme httpsScheme = new Scheme("https", sf, 443);
      SchemeRegistry schemeRegistry = new SchemeRegistry();
      schemeRegistry.register(httpsScheme);
      schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

      ignoreSSLConnectionManager = new SingleClientConnManager(new BasicHttpParams(), schemeRegistry);
    } catch (NoSuchAlgorithmException e) {
      // This will only be thrown if SSL isn't available for some reason.
    } catch (KeyManagementException e) {
      // This might be thrown when passing a key into init(), but no key is being passed.
    } catch (GeneralSecurityException e) {
      // This catches anything else that might go wrong.
      // If anything goes wrong we default to the standard connection manager.
    }
  }
}
