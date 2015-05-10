package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionStatus {
  /* Response was OK */
  @JsonProperty("value")
  private Boolean value = null;
  @JsonProperty("viewId")
  private String viewId = null;
  public Boolean getValue() {
    return value;
  }
  public void setValue(Boolean value) {
    this.value = value;
  }

  public String getViewId() {
    return viewId;
  }
  public void setViewId(String viewId) {
    this.viewId = viewId;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class SubscriptionStatus {\n");
    sb.append("  value: ").append(value).append("\n");
    sb.append("  viewId: ").append(viewId).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

