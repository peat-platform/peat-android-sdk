package eu.openiict.client.api;

import eu.openiict.client.common.ApiException;
import eu.openiict.client.common.ApiInvoker;
import eu.openiict.client.model.Attachment;
import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class AttachmentsApi {
  String basePath = "https://dev.openi-ict.eu/api/v1";
  ApiInvoker apiInvoker = ApiInvoker.getInstance();

  public void addHeader(String key, String value) {
    getInvoker().addDefaultHeader(key, value);
  }

  public ApiInvoker getInvoker() {
    return apiInvoker;
  }

  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }

  public String getBasePath() {
    return basePath;
  }

  private String getMimeType(File file){
    MimeDetector md        = new MagicMimeMimeDetector();
    Collection   mimeTypes =  md.getMimeTypes( file );

    return (mimeTypes.isEmpty()) ? "application/octet-stream" : mimeTypes.iterator().next().toString();
  }

  //error info- code: 200 reason: "Ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: Attachment
  //error info- code: 401 reason: "Unauthorised request" model: Attachment
  public Attachment uploadAttachment (String cloudletId, String objectId, String property, File file) throws ApiException {
    // verify required params are set
    if(cloudletId == null || file == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/attachments/{cloudletId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString()));

    String[] contentTypes = {
      "multipart/form-data"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(contentType.startsWith("multipart/form-data")) {
        boolean hasFields = false;
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if (null != objectId){
          hasFields = true;
          meBuilder.addTextBody( "objectId", objectId);
        }
        if (null != property){
          hasFields = true;
          meBuilder.addTextBody( "property", property);
        }
        if (null != file){
          hasFields = true;
          meBuilder.addBinaryBody( "file", file, ContentType.create( getMimeType( file ) ), file.getName() );
        }
        if(hasFields){
          httpEntity = meBuilder.build();
        }
    }

    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (Attachment) ApiInvoker.deserialize(response, "", Attachment.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      if(ex.getCode() == 404) {
        return null;
      }
      else {
        throw ex;
      }
    }
  }
  //error info- code: 200 reason: "Ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: Attachment
  //error info- code: 401 reason: "Unauthorised request" model: Attachment
  public Attachment getAttachmentMeta (String cloudletId, String attachmentId) throws ApiException {
    // verify required params are set
    if(cloudletId == null || attachmentId == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/attachments/{cloudletId}/{attachmentId}/meta".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "attachmentId" + "\\}", apiInvoker.escapeString(attachmentId.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(contentType.startsWith("multipart/form-data")) {
        boolean hasFields = false;
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if(hasFields){
          httpEntity = meBuilder.build();
        }
    }

    try {
      String response = apiInvoker.invokeAPI(basePath, path, "GET", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (Attachment) ApiInvoker.deserialize(response, "", Attachment.class);
      }
      else {
        return null;
      }
    } catch (ApiException ex) {
      if(ex.getCode() == 404) {
        return null;
      }
      else {
        throw ex;
      }
    }
  }
  //error info- code: 200 reason: "Ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: Attachment
  //error info- code: 401 reason: "Unauthorised request" model: Attachment
  public File getAttachment (String cloudletId, String attachmentId) throws ApiException {
     throw new ApiException(400, "getAttachment not implemented, please use eu.openiict.client.utils.AttachmentUtils instead.");
  }
}

