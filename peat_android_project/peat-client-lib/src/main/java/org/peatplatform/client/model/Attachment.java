package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Attachment {
  /* Attachment id */
  @JsonProperty("@id")
  private String id = null;
  /* location */
  @JsonProperty("location")
  private String location = null;
  /* filename */
  @JsonProperty("filename")
  private String filename = null;
  /* content-length */
  @JsonProperty("content-length")
  private String contentlength = null;
  /* Content-Type */
  @JsonProperty("Content-Type")
  private String ContentType = null;
  /* _date_created */
  @JsonProperty("_date_created")
  private String dateCreated = null;
  /* _date_modified */
  @JsonProperty("_date_modified")
  private String dateModified = null;
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

  public String getFilename() {
    return filename;
  }
  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getContentlength() {
    return contentlength;
  }
  public void setContentlength(String contentlength) {
    this.contentlength = contentlength;
  }

  public String getContentType() {
    return ContentType;
  }
  public void setContentType(String ContentType) {
    this.ContentType = ContentType;
  }

  public String getDateCreated() {
    return dateCreated;
  }
  public void setDateCreated(String dateCreated) {
    this.dateCreated = dateCreated;
  }

  public String getDateModified() {
    return dateModified;
  }
  public void setDateModified(String dateModified) {
    this.dateModified = dateModified;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Attachment {\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  location: ").append(location).append("\n");
    sb.append("  filename: ").append(filename).append("\n");
    sb.append("  contentlength: ").append(contentlength).append("\n");
    sb.append("  ContentType: ").append(ContentType).append("\n");
    sb.append("  _date_created: ").append(dateCreated).append("\n");
    sb.append("  _date_modified: ").append(dateModified).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

