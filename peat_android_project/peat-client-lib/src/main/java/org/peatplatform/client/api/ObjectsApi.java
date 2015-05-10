package org.peatplatform.client.api;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.common.ApiInvoker;
import org.peatplatform.client.model.PeatObject;
import org.peatplatform.client.model.PeatObjectList;
import org.peatplatform.client.model.ObjectResponse;
import org.peatplatform.client.model.ObjectErrorResponse;
import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class ObjectsApi {
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

  //error info- code: 400 reason: "Request Error" model: ObjectErrorResponse
  //error info- code: 401 reason: "Unauthorised request" model: ObjectErrorResponse
  //error info- code: 500 reason: "Data could not be added to the cloudlet" model: ObjectErrorResponse
  public ObjectResponse createObject (String cloudletId, PeatObject body, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString()));

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
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  //error info- code: 400 reason: "Request Error" model: ObjectErrorResponse
  //error info- code: 401 reason: "Unauthorised request" model: ObjectErrorResponse
  //error info- code: 500 reason: "Data could not be added to the cloudlet" model: ObjectErrorResponse
  public ObjectResponse createObjectWithAuth (PeatObject body, String Authorization) throws ApiException {
    // verify required params are set
    if(body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects".replaceAll("\\{format\\}","json");

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
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  public PeatObject getObject (String cloudletId, String objectId, Boolean resolve, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || objectId == null || resolve == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(!"null".equals(String.valueOf(resolve)))
      queryParams.put("resolve", String.valueOf(resolve));
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
        return (PeatObject) ApiInvoker.deserialize(response, "", PeatObject.class);
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
  public ObjectResponse setObject (String cloudletId, String objectId, PeatObject body, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || objectId == null || body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

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
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  public ObjectResponse removeObject (String cloudletId, String objectId, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || objectId == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

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
      String response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  public PeatObject getObjectByAuthToken (String objectId, Boolean resolve, String Authorization) throws ApiException {
    // verify required params are set
    if(objectId == null || resolve == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(!"null".equals(String.valueOf(resolve)))
      queryParams.put("resolve", String.valueOf(resolve));
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
        return (PeatObject) ApiInvoker.deserialize(response, "", PeatObject.class);
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
  public ObjectResponse setObjectByAuthToken (String objectId, PeatObject body, String Authorization) throws ApiException {
    // verify required params are set
    if(objectId == null || body == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

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
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  public ObjectResponse removeObjectByAuth (String objectId, String Authorization) throws ApiException {
    // verify required params are set
    if(objectId == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{objectId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString()));

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
      String response = apiInvoker.invokeAPI(basePath, path, "DELETE", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (ObjectResponse) ApiInvoker.deserialize(response, "", ObjectResponse.class);
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
  public Map getObjectProperty (String cloudletId, String objectId, String property, Boolean resolve, Boolean meta, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || objectId == null || property == null || resolve == null || meta == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}/{objectId}/{property}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString())).replaceAll("\\{" + "objectId" + "\\}", apiInvoker.escapeString(objectId.toString())).replaceAll("\\{" + "property" + "\\}", apiInvoker.escapeString(property.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    if(!"null".equals(String.valueOf(resolve)))
      queryParams.put("resolve", String.valueOf(resolve));
    if(!"null".equals(String.valueOf(meta)))
      queryParams.put("meta", String.valueOf(meta));
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
        return (Map) ApiInvoker.deserialize(response, "", Map.class);
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
  public PeatObjectList listObjects (String cloudletId, Integer offset, Integer limit, String type, Boolean id_only, String with_property, String property_filter, String only_show_properties, String Authorization) throws ApiException {
    // verify required params are set
    if(cloudletId == null || Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects/{cloudletId}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "cloudletId" + "\\}", apiInvoker.escapeString(cloudletId.toString()));

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
    if(!"null".equals(String.valueOf(type)))
      queryParams.put("type", String.valueOf(type));
    if(!"null".equals(String.valueOf(id_only)))
      queryParams.put("id_only", String.valueOf(id_only));
    if(!"null".equals(String.valueOf(with_property)))
      queryParams.put("with_property", String.valueOf(with_property));
    if(!"null".equals(String.valueOf(property_filter)))
      queryParams.put("property_filter", String.valueOf(property_filter));
    if(!"null".equals(String.valueOf(only_show_properties)))
      queryParams.put("only_show_properties", String.valueOf(only_show_properties));
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
        return (PeatObjectList) ApiInvoker.deserialize(response, "", PeatObjectList.class);
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
  public PeatObjectList listObjectsWithAuthToken (Integer offset, Integer limit, String type, Boolean id_only, String with_property, String property_filter, String only_show_properties, String Authorization, String order) throws ApiException {
    // verify required params are set
    if(Authorization == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/objects".replaceAll("\\{format\\}","json");

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
    if(!"null".equals(String.valueOf(type)))
      queryParams.put("type", String.valueOf(type));
    if(!"null".equals(String.valueOf(id_only)))
      queryParams.put("id_only", String.valueOf(id_only));
    if(!"null".equals(String.valueOf(with_property)))
      queryParams.put("with_property", String.valueOf(with_property));
    if(!"null".equals(String.valueOf(property_filter)))
      queryParams.put("property_filter", String.valueOf(property_filter));
    if(!"null".equals(String.valueOf(only_show_properties)))
      queryParams.put("only_show_properties", String.valueOf(only_show_properties));
    if(!"null".equals(String.valueOf(order)))
      queryParams.put("order", String.valueOf(order));
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
        return (PeatObjectList) ApiInvoker.deserialize(response, "", PeatObjectList.class);
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

