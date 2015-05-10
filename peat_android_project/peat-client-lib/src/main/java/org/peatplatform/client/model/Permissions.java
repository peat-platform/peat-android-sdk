package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Permissions {
  /* Permission type */
  @JsonProperty("type")
  private String type = null;
  /* Permission reference id */
  @JsonProperty("ref")
  private String ref = null;
  /* Permission access type */
  @JsonProperty("access_type")
  private String accessType = null;
  /* Permission access level */
  @JsonProperty("access_level")
  private String accessLevel = null;
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public String getRef() {
    return ref;
  }
  public void setRef(String ref) {
    this.ref = ref;
  }

  public String getAccessType() {
    return accessType;
  }
  public void setAccessType(String accessType) {
    this.accessType = accessType;
  }

  public String getAccessLevel() {
    return accessLevel;
  }
  public void setAccessLevel(String accessLevel) {
    this.accessLevel = accessLevel;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Permissions {\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  ref: ").append(ref).append("\n");
    sb.append("  access_type: ").append(accessType).append("\n");
    sb.append("  access_level: ").append(accessLevel).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

