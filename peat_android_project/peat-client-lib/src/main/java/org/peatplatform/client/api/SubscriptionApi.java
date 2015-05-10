package org.peatplatform.client.api;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.common.ApiInvoker;
import org.peatplatform.client.model.SubscriptionStatus;
import org.peatplatform.client.model.Subscription;
import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class SubscriptionApi {
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

  //error info- code: 200 reason: "ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: SubscriptionStatus
  public String getSubscriptionsByCloudlet (String Authorization) throws ApiException {
    // verify required params are set
    if(Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/subscription".replaceAll("\\{format\\}","json");

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
        return (String) ApiInvoker.deserialize(response, "", String.class);
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
  //error info- code: 201 reason: "created" model: <none>
  //error info- code: 400 reason: "Bad Request" model: SubscriptionStatus
  public String addSubscription (Subscription body, String Authorization) throws ApiException {
    // verify required params are set
    if(body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/subscription".replaceAll("\\{format\\}","json");

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
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, body, headerParams, contentType, httpEntity);
      if(response != null){
        return (String) ApiInvoker.deserialize(response, "", String.class);
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
  //error info- code: 200 reason: "ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: SubscriptionStatus
  public String getSubscriptionsByCloudletAndObject (String Authorization, String subscriptionId) throws ApiException {
    // verify required params are set
    if(Authorization == null || subscriptionId == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/subscription/{subscriptionId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "subscriptionId" + "\\}", apiInvoker.escapeString(subscriptionId.toString()));

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
        return (String) ApiInvoker.deserialize(response, "", String.class);
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
  //error info- code: 501 reason: "Not yet Implemented" model: <none>
  public String removeSubscription () throws ApiException {
    // create path and map variables
    String path = "/subscription/{subscriptionId}".replaceAll("\\{format\\}","json");

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
      String response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (String) ApiInvoker.deserialize(response, "", String.class);
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
  //error info- code: 200 reason: "ok" model: <none>
  //error info- code: 400 reason: "Bad Request" model: SubscriptionStatus
  public String setSubscriptionForObject (String Authorization, String subscriptionId, String revision, Subscription body) throws ApiException {
    // verify required params are set
    if(Authorization == null || subscriptionId == null || revision == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/subscription/{subscriptionId}/{revision}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "subscriptionId" + "\\}", apiInvoker.escapeString(subscriptionId.toString())).replaceAll("\\{" + "revision" + "\\}", apiInvoker.escapeString(revision.toString()));

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
      String response = apiInvoker.invokeAPI(basePath, path, "PUT", queryParams, body, headerParams, contentType, httpEntity);
      if(response != null){
        return (String) ApiInvoker.deserialize(response, "", String.class);
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

