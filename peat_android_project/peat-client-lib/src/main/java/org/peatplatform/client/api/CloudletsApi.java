package org.peatplatform.client.api;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.common.ApiInvoker;
import org.peatplatform.client.model.CloudletErrorResponse;
import org.peatplatform.client.model.UserCloudlet;
import org.peatplatform.client.model.ObjectErrorResponse;
import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class CloudletsApi {
  String basePath = "https://dev.peat-platform.org/api/v1";
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

  //error info- code: 400 reason: "Bad Request" model: CloudletErrorResponse
  //error info- code: 401 reason: "Unauthorised request" model: CloudletErrorResponse
  //error info- code: 405 reason: "Method Not allowed" model: CloudletErrorResponse
  //error info- code: 501 reason: "Not yet Implemented" model: <none>
  public UserCloudlet getCloudletId (String Authorization) throws ApiException {
    // verify required params are set
    if(Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/cloudlets".replaceAll("\\{format\\}","json");

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("Authorization", Authorization);
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
        return (UserCloudlet) ApiInvoker.deserialize(response, "", UserCloudlet.class);
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
  //error info- code: 400 reason: "Object not found" model: ObjectErrorResponse
  //error info- code: 401 reason: "Unauthorised request" model: ObjectErrorResponse
  public List<UserCloudlet> getCloudlets (Integer offset, Integer limit) throws ApiException {
    // create path and map variables
    String path = "/cloudlets/all".replaceAll("\\{format\\}","json");

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(!"null".equals(String.valueOf(offset)))
      queryParams.put("offset", String.valueOf(offset));
    if(!"null".equals(String.valueOf(limit)))
      queryParams.put("limit", String.valueOf(limit));
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
        return (List<UserCloudlet>) ApiInvoker.deserialize(response, "List", UserCloudlet.class);
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
  }

