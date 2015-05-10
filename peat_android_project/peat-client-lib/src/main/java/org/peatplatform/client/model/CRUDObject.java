package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.peatplatform.client.model.CRUDUndefinedObject;
@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUDObject {
  /* The Objects DB */
  @JsonProperty("db")
  private String db = null;
  /* The Objects ID */
  @JsonProperty("id")
  private String id = null;
  /* The Objects data */
  @JsonProperty("data")
  private CRUDUndefinedObject data = null;
  public String getDb() {
    return db;
  }
  public void setDb(String db) {
    this.db = db;
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }

  public CRUDUndefinedObject getData() {
    return data;
  }
  public void setData(CRUDUndefinedObject data) {
    this.data = data;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CRUDObject {\n");
    sb.append("  db: ").append(db).append("\n");
    sb.append("  id: ").append(id).append("\n");
    sb.append("  data: ").append(data).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

