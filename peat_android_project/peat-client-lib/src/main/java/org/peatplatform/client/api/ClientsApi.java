package org.peatplatform.client.api;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.common.ApiInvoker;
import org.peatplatform.client.model.ClientRegisterResponse;
import org.peatplatform.client.model.ClientRegisterRequest;
import org.peatplatform.client.model.ClientsResponse;
import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class ClientsApi {
  String basePath = "https://dev.peat-platform.org/api/v1/auth";
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

  //error info- code: 200 reason: "OK" model: <none>
  //error info- code: 400 reason: "Bad Request" model: <none>
  public ClientRegisterResponse createClient (ClientRegisterRequest body, String Authorization) throws ApiException {
    // verify required params are set
    if(body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/clients".replaceAll("\\{format\\}","json");

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
        return (ClientRegisterResponse) ApiInvoker.deserialize(response, "", ClientRegisterResponse.class);
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
  //error info- code: 200 reason: "OK" model: <none>
  //error info- code: 400 reason: "Bad Request" model: <none>
  public ClientsResponse readClient (String Authorization) throws ApiException {
    // verify required params are set
    if(Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/clients".replaceAll("\\{format\\}","json");

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
        return (ClientsResponse) ApiInvoker.deserialize(response, "", ClientsResponse.class);
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

