package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import org.peatplatform.client.model.Type;
import org.peatplatform.client.model.Permissions;
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppPermissions {
  @JsonProperty("app_api_key")
  private String appApiKey = null;
  /* A Single permission object. */
  @JsonProperty("permissions")
  private List<Permissions> permissions = new ArrayList<Permissions>();
  /* Peat Types */
  @JsonProperty("types")
  private List<Type> types = new ArrayList<Type>();
  public String getAppApiKey() {
    return appApiKey;
  }
  public void setAppApiKey(String appApiKey) {
    this.appApiKey = appApiKey;
  }

  public List<Permissions> getPermissions() {
    return permissions;
  }
  public void setPermissions(List<Permissions> permissions) {
    this.permissions = permissions;
  }

  public List<Type> getTypes() {
    return types;
  }
  public void setTypes(List<Type> types) {
    this.types = types;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppPermissions {\n");
    sb.append("  app_api_key: ").append(appApiKey).append("\n");
    sb.append("  permissions: ").append(permissions).append("\n");
    sb.append("  types: ").append(types).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

