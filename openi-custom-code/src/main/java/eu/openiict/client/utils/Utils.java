

package eu.openiict.client.utils;


import eu.openiict.client.api.AttachmentsApi;
import eu.openiict.client.api.CloudletsApi;
import eu.openiict.client.api.ObjectsApi;
import eu.openiict.client.api.SearchApi;
import eu.openiict.client.api.SubscriptionApi;
import eu.openiict.client.api.TypesApi;
import eu.openiict.client.model.OPENiObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author dmccarthy
 */
public class Utils {

   public static final String CLOUDLET_PATTERN_MATCH              = "^c_[a-z0-9]{32}$";
   public static final String CLOUDLET_PATTERN_EXTRACT            = "c_[a-z0-9]{32}";

   public static final String TYPE_CLOUDLET_PATTERN_MATCH         = "^t_[a-z0-9]{32}-[0-9]{1,10000}$";
   public static final String TYPE_CLOUDLET_PATTERN_EXTRACT       = "t_[a-z0-9]{32}-[0-9]{1,10000}";

   public static final String OBJECT_CLOUDLET_PATTERN_MATCH       = "^0[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}$";
   public static final String OBJECT_CLOUDLET_PATTERN_EXTRACT     = "0[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}";

   public static final String ATTACHMENT_CLOUDLET_PATTERN_MATCH   = "^a[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}$";
   public static final String ATTACHMENT_CLOUDLET_PATTERN_EXTRACT = "a[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}";

   public static final String DEFAULT_IMAGES_OBJECT_ID            = "00000001-5203-4f5b-df3e-7f06c795775d";
   public static final String DEFAULT_VIDEOS_OBJECT_ID            = "00000002-5203-4f5b-df3e-7f06c795775d";
   public static final String DEFAULT_PDFS_OBJECT_ID              = "00000003-5203-4f5b-df3e-7f06c795775d";
   public static final String DEFAULT_AUDIO_OBJECT_ID             = "00000004-5203-4f5b-df3e-7f06c795775d";
   
   public Utils() {
   }

   private static boolean         ignoreSSL;
   
   private static AttachmentsApi  aApi;
   private static AttachmentUtils aUtils;
   private static CloudletsApi    cApi;
   private static ObjectsApi      oApi;
   private static SearchApi       sApi;
   private static SubscriptionApi subApi;
   private static TypesApi        tApi;

   
   public static void setIgnoreSSL( boolean ignoreSSLVal ) {
      ignoreSSL = ignoreSSLVal;
   }

   
   public static CloudletsApi getCloudletsApi(){

      if (null == cApi){
         cApi = new CloudletsApi();
         cApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }
      return cApi;
   }
   
   
   public static AttachmentsApi getAttachmentApi(){

      if (null == aApi){
         aApi = new AttachmentsApi();
         aApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }
      return aApi;
   }
   
   
   public static AttachmentUtils getAttachmentUtils(){

      if (null == aUtils){
         aUtils = new AttachmentUtils();
         aUtils.setIgnoreSSLCertificates( ignoreSSL );
      }
      return aUtils;
   }
   
   
   public static ObjectsApi getObjectApi(){

      if (null == oApi){
         oApi = new ObjectsApi();
         oApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }

      return oApi;
   }

   
   public static SearchApi getSearchApi(){

      if (null == sApi){
         sApi = new SearchApi();
         sApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }
      return sApi;
   }
   
   
   public static SubscriptionApi getSubscriptionApi(){

      if (null == subApi){
         subApi = new SubscriptionApi();
         subApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }
      return subApi;
   }
   
   
   public static TypesApi getTypesApi(){

      if (null == tApi){
         tApi = new TypesApi();
         tApi.getInvoker().ignoreSSLCertificates( ignoreSSL );
      }
      return tApi;
   }
   
   
   public static final boolean isCloudletId(String id){
      
      if (null == id){
         return false;
      }
      return id.matches( CLOUDLET_PATTERN_MATCH );
   }
   
   public static final boolean isObjectId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( OBJECT_CLOUDLET_PATTERN_MATCH );
   }
   
   public static final boolean isAttachmentId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( ATTACHMENT_CLOUDLET_PATTERN_MATCH );
   }
   
   public static final boolean isTypeId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( TYPE_CLOUDLET_PATTERN_MATCH );
   }

   public static final String extractAttachmentId(String id){
      
      if (null == id){
         return "";
      }
      
      final Pattern pattern = Pattern.compile(ATTACHMENT_CLOUDLET_PATTERN_MATCH);
      final Matcher matcher = pattern.matcher(id);
      
      if(matcher.matches()) {
         return matcher.group(1);
      }
      
      return "";
   }

   public static final String extractObjectId(String id){
            
      if (null == id){
         return "";
      }
      
      final Pattern pattern = Pattern.compile(OBJECT_CLOUDLET_PATTERN_EXTRACT);
      final Matcher matcher = pattern.matcher(id);
      
      if(matcher.matches()) {
         return matcher.group(1);
      }
      
      return "";
   }

   public static final String extractCloudletId(String id){
            
      if (null == id){
         return "";
      }
      
      final Pattern pattern = Pattern.compile(CLOUDLET_PATTERN_EXTRACT);
      final Matcher matcher = pattern.matcher(id);
      
      if(matcher.matches()) {
         return matcher.group(1);
      }
      
      return "";
   }

   public static final String extractTypeId(String id){
            
      if (null == id){
         return "";
      }
      
      final Pattern pattern = Pattern.compile(TYPE_CLOUDLET_PATTERN_EXTRACT);
      final Matcher matcher = pattern.matcher(id);
      
      if(matcher.matches()) {
         return matcher.group(1);
      }
      
      return "";
   }
   
   
   public static final JSONArray getObjectData(final OPENiObject oObj){
      
      return (null != oObj.getData()) ? null : new JSONArray( oObj );
      
   }
}
