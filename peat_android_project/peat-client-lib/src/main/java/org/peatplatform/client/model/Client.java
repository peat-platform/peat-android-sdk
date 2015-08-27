package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
  /* The client's name */
  @JsonProperty("name")
  private String name = null;
  /* The client's description */
  @JsonProperty("description")
  private String description = null;
  /* Marks the Client as a Service Enabler. */
  @JsonProperty("isSE")
  private Boolean isSE = null;
  /* Developers Cloudlet associated with Client (Used for permissions). */
  @JsonProperty("cloudlet")
  private String cloudlet = null;
  /* api_key used to identify client */
  @JsonProperty("api_key")
  private String apiKey = null;
  /* secret key used to log into system */
  @JsonProperty("secret")
  private String secret = null;
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getIsSE() {
    return isSE;
  }
  public void setIsSE(Boolean isSE) {
    this.isSE = isSE;
  }

  public String getCloudlet() {
    return cloudlet;
  }
  public void setCloudlet(String cloudlet) {
    this.cloudlet = cloudlet;
  }

  public String getApiKey() {
    return apiKey;
  }
  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getSecret() {
    return secret;
  }
  public void setSecret(String secret) {
    this.secret = secret;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Client {\n");
    sb.append("  name: ").append(name).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("  isSE: ").append(isSE).append("\n");
    sb.append("  cloudlet: ").append(cloudlet).append("\n");
    sb.append("  api_key: ").append(apiKey).append("\n");
    sb.append("  secret: ").append(secret).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

