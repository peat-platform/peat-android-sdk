package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Session {
  /* The user's session */
  @JsonProperty("session")
  private String session = null;
  public String getSession() {
    return session;
  }
  public void setSession(String session) {
    this.session = session;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Session {\n");
    sb.append("  session: ").append(session).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

