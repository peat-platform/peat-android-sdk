package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.*;
import org.peatplatform.client.model.ContextEntry;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Type {
  /* Unique identifier for the type */
  @JsonProperty("@id")
  private String id = null;
  /* Unique types URI */
  @JsonProperty("@location")
  private String location = null;
  /* An array of Types */
  @JsonProperty("@context")
  private List<ContextEntry> context = new ArrayList<ContextEntry>();
  /* The URL of the refernce type. */
  @JsonProperty("@reference")
  private String reference = null;
  /* Date that the Type was created. */
  @JsonProperty("_date_created")
  private Date dateCreated = null;
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

  public List<ContextEntry> getContext() {
    return context;
  }
  public void setContext(List<ContextEntry> context) {
    this.context = context;
  }

  public String getReference() {
    return reference;
  }
  public void setReference(String reference) {
    this.reference = reference;
  }

  public Date getDateCreated() {
    return dateCreated;
  }
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Type {\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  location: ").append(location).append("\n");
    sb.append("  context: ").append(context).append("\n");
    sb.append("  reference: ").append(reference).append("\n");
    sb.append("  _date_created: ").append(dateCreated).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

