package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Subscription {
  /* The id of the cloudlet the subscribed object belongs too. */
  @JsonProperty("cloudletid")
  private String cloudletid = null;
  /* Peat Type ID */
  @JsonProperty("typeid")
  private String typeid = null;
  /* The id of the subscribed Object. */
  @JsonProperty("objectid")
  private String objectid = null;
  /* The Object/Data to watch for changes */
  @JsonProperty("data")
  private String data = null;
  /* The Notification type. [Email, SMS, NOTIFICATION] */
  @JsonProperty("notification_type")
  private String notificationType = null;
  /* Where to send the notification */
  @JsonProperty("endpoint")
  private String endpoint = null;
  public String getCloudletid() {
    return cloudletid;
  }
  public void setCloudletid(String cloudletid) {
    this.cloudletid = cloudletid;
  }

  public String getTypeid() {
    return typeid;
  }
  public void setTypeid(String typeid) {
    this.typeid = typeid;
  }

  public String getObjectid() {
    return objectid;
  }
  public void setObjectid(String objectid) {
    this.objectid = objectid;
  }

  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }

  public String getNotificationType() {
    return notificationType;
  }
  public void setNotificationType(String notificationType) {
    this.notificationType = notificationType;
  }

  public String getEndpoint() {
    return endpoint;
  }
  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Subscription {\n");
    sb.append("  cloudletid: ").append(cloudletid).append("\n");
    sb.append("  typeid: ").append(typeid).append("\n");
    sb.append("  objectid: ").append(objectid).append("\n");
    sb.append("  data: ").append(data).append("\n");
    sb.append("  notification_type: ").append(notificationType).append("\n");
    sb.append("  endpoint: ").append(endpoint).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

