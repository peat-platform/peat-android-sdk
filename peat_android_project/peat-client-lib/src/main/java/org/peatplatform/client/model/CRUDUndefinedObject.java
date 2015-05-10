package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CRUDUndefinedObject {
  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class CRUDUndefinedObject {\n");
    sb.append("}\n");
    return sb.toString();
  }
}

