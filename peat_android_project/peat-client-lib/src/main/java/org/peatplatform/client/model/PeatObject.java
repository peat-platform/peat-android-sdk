package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Date;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeatObject {
  /* The Object's identifier. */
  @JsonProperty("@id")
  private String id = null;
  /* The Object's identifier URI. */
  @JsonProperty("@location")
  private String location = null;
  /* The Object's Cloudlet Id. */
  @JsonProperty("@cloudlet")
  private String cloudlet = null;
  /* Peat Type that this object is an instance of. */
  @JsonProperty("@type")
  private String type = null;
  /* The Object's values. */
  @JsonProperty("@data")
  private Map data = null;
  /* Date that the Type was created. */
  @JsonProperty("_date_created")
  private Date dateCreated = null;
  /* Date that the Type was created. */
  @JsonProperty("_date_modified")
  private Date dateModified = null;
  /* Revision number associated with this version of the Object, this number must be presented when updating the object */
  @JsonProperty("_revision")
  private String revision = null;
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }

  public String getCloudlet() {
    return cloudlet;
  }
  public void setCloudlet(String cloudlet) {
    this.cloudlet = cloudlet;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public Map getData() {
    return data;
  }
  public void setData(Map data) {
    this.data = data;
  }

  public Date getDateCreated() {
    return dateCreated;
  }
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  public Date getDateModified() {
    return dateModified;
  }
  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }

  public String getRevision() {
    return revision;
  }
  public void setRevision(String revision) {
    this.revision = revision;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeatObject {\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  location: ").append(location).append("\n");
    sb.append("  cloudlet: ").append(cloudlet).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  data: ").append(data).append("\n");
    sb.append("  _date_created: ").append(dateCreated).append("\n");
    sb.append("  _date_modified: ").append(dateModified).append("\n");
    sb.append("  _revision: ").append(revision).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

