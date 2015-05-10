package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth_Credentials {
  /* The user's username */
  @JsonProperty("username")
  private String username = null;
  /* The user's password */
  @JsonProperty("password")
  private String password = null;
  /* The developers identifier */
  @JsonProperty("api_key")
  private String apiKey = null;
  /* The developers secret key */
  @JsonProperty("secret")
  private String secret = null;
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
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
    sb.append("class Auth_Credentials {\n");
    sb.append("  username: ").append(username).append("\n");
    sb.append("  password: ").append(password).append("\n");
    sb.append("  api_key: ").append(apiKey).append("\n");
    sb.append("  secret: ").append(secret).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

