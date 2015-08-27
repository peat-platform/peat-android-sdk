package org.peatplatform.client.api;

import org.peatplatform.client.common.ApiException;
import org.peatplatform.client.common.ApiInvoker;
import org.peatplatform.client.model.CRUDObject;
import org.peatplatform.client.model.CRUDErrorObject;
import org.peatplatform.client.model.CreateErrorResponse;

import java.util.*;
import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;
import eu.medsea.mimeutil.detector.MimeDetector;

public class CrudApi {
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

  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject create (String authorization, String db, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject query (String authorization, String body) throws ApiException {
    // verify required params are set
    if(authorization == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/query".replaceAll("\\{format\\}","json");

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject createNamedAlt (String authorization, String db, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/create/{db}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject createAlt (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/create/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject readAlt (String authorization, String db, String id) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/read/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
    if(contentType.startsWith("multipart/form-data")) {
        boolean hasFields = false;
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if(hasFields){
          httpEntity = meBuilder.build();
        }
    }

    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject updateAlt (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/update/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject deleteAlt (String authorization, String db, String id) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/delete/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
    if(contentType.startsWith("multipart/form-data")) {
        boolean hasFields = false;
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if(hasFields){
          httpEntity = meBuilder.build();
        }
    }

    try {
      String response = apiInvoker.invokeAPI(basePath, path, "POST", queryParams, null, headerParams, contentType, httpEntity);
      if(response != null){
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject patchAlt (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/patch/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Bad Request" model: CreateErrorResponse
  //error info- code: 401 reason: "Not Authorised" model: CreateErrorResponse
  public CRUDObject upsertAlt (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/upsert/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Object not found" model: CRUDErrorObject
  //error info- code: 401 reason: "Unauthorised request" model: CRUDErrorObject
  public CRUDObject createNamed (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Object not found" model: CRUDErrorObject
  //error info- code: 401 reason: "Unauthorised request" model: CRUDErrorObject
  public CRUDObject get (String authorization, String db, String id) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Object not found" model: CRUDErrorObject
  //error info- code: 401 reason: "Unauthorised request" model: CRUDErrorObject
  public CRUDObject update (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Object not found" model: CRUDErrorObject
  //error info- code: 401 reason: "Unauthorised request" model: CRUDErrorObject
  public CRUDObject patch (String authorization, String db, String id, Object body) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null || body == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
    if(contentType.startsWith("multipart/form-data")) {
        boolean hasFields = false;
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        if(hasFields){
          httpEntity = meBuilder.build();
        }
    }

    try {
      String response = apiInvoker.invokeAPI(basePath, path, "PATCH", queryParams, body, headerParams, contentType, httpEntity);
      if(response != null){
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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
  //error info- code: 400 reason: "Object not found" model: CRUDErrorObject
  //error info- code: 401 reason: "Unauthorised request" model: CRUDErrorObject
  public CRUDObject delete (String authorization, String db, String id) throws ApiException {
    // verify required params are set
    if(authorization == null || db == null || id == null ) {
       throw new ApiException(400, "missing required params");
    }
    // create path and map variables
    String path = "/crud/{db}/{id}".replaceAll("\\{format\\}","json").replaceAll("\\{" + "db" + "\\}", apiInvoker.escapeString(db.toString())).replaceAll("\\{" + "id" + "\\}", apiInvoker.escapeString(id.toString()));

    String[] contentTypes = {
      "application/json"};

    String contentType = contentTypes.length > 0 ? contentTypes[0] : "application/json";

    // query params
    Map<String, String>    queryParams  = new HashMap<String, String>();
    Map<String, String>    headerParams = new HashMap<String, String>();
    HttpEntity             httpEntity   = null;

    headerParams.put("authorization", authorization);
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
        return (CRUDObject) ApiInvoker.deserialize(response, "", CRUDObject.class);
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

