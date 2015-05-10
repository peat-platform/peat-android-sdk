

package org.peatplatform.client.utils;


import org.peatplatform.client.api.AttachmentsApi;
import org.peatplatform.client.api.CloudletsApi;
import org.peatplatform.client.api.ObjectsApi;
import org.peatplatform.client.api.SearchApi;
import org.peatplatform.client.api.SubscriptionApi;
import org.peatplatform.client.api.TypesApi;
import org.peatplatform.client.model.PeatObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author dmccarthy
 */
public class PeatUtils {

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
   
   public PeatUtils() {
   }

   private static boolean         ignoreSSL;
   
   private static AttachmentsApi  aApi;
   private static AttachmentUtils aUtils;
   private static CloudletsApi    cApi;
   private static ObjectsApi      oApi;
   private static SearchApi       sApi;
   private static SubscriptionApi subApi;
   private static TypesApi        tApi;

   
   public static void ignoreSSLCert( boolean ignoreSSLVal ) {
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
         aUtils.setBasePath( getObjectApi().getBasePath() );
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
   
   
   public static final JSONObject getObjectData(final PeatObject oObj){
      
      return (null == oObj.getData()) ? null : new JSONObject( oObj.getData() );
      
   }


   public static Map<String, Object> jsonObjectToMap(JSONObject json) throws JSONException {
      final Map<String, Object> retMap = new HashMap<String, Object>();

      if(json != JSONObject.NULL) {
         return toMap(json);
      }

      return retMap;
   }

   private static Map<String, Object> toMap(JSONObject object) throws JSONException {
      final Map<String, Object> map = new HashMap<String, Object>();

      final Iterator<String> keysItr = object.keys();
      while(keysItr.hasNext()) {
         final String key = keysItr.next();
         Object value     = object.get(key);

         if(value instanceof JSONArray) {
            value = toList((JSONArray) value);
         }

         else if(value instanceof JSONObject) {
            value = toMap((JSONObject) value);
         }
         map.put(key, value);
      }
      return map;
   }

   private static List<Object> toList(JSONArray array) throws JSONException {
      final List<Object> list = new ArrayList<Object>();
      for(int i = 0; i < array.length(); i++) {
         Object value = array.get(i);
         if(value instanceof JSONArray) {
            value = toList((JSONArray) value);
         }

         else if(value instanceof JSONObject) {
            value = toMap((JSONObject) value);
         }
         list.add(value);
      }
      return list;
   }
}
