

package eu.openiict.client.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author dmccarthy
 */
public class Utils {

   public static final String cloudletPatternMatch     = "^c_[a-z0-9]{32}$";
   public static final String cloudletPatternExtract   = "c_[a-z0-9]{32}";

   public static final String typePatternMatch         = "^t_[a-z0-9]{32}-[0-9]{1,10000}$";
   public static final String typePatternExtract       = "t_[a-z0-9]{32}-[0-9]{1,10000}";

   public static final String objectPatternMatch       = "^0[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}$";
   public static final String objectPatternExtract     = "0[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}";

   public static final String attachmentPatternMatch   = "^a[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}$";
   public static final String attachmentPatternExtract = "a[a-f,0-9]{7}-[a-f,0-9]{4}-4[a-f,0-9]{3}-[a-f,0-9]{4}-[a-f,0-9]{12}";

   public Utils() {
   }

   
   public static final boolean isCloudletId(String id){
      
      if (null == id){
         return false;
      }
      return id.matches( cloudletPatternMatch );
   }
   
   public static final boolean isObjectId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( objectPatternMatch );
   }
   
   public static final boolean isAttachmentId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( attachmentPatternMatch );
   }
   
   public static final boolean isTypeId(String id){
      
      if (null == id){
         return false;
      }
      
      return id.matches( typePatternMatch );
   }

   public static final String extractAttachmentId(String id){
      
      if (null == id){
         return "";
      }
      
      final Pattern pattern = Pattern.compile(attachmentPatternMatch);
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
      
      final Pattern pattern = Pattern.compile(objectPatternExtract);
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
      
      final Pattern pattern = Pattern.compile(cloudletPatternExtract);
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
      
      final Pattern pattern = Pattern.compile(typePatternExtract);
      final Matcher matcher = pattern.matcher(id);
      
      if(matcher.matches()) {
         return matcher.group(1);
      }
      
      return "";
   }
}
