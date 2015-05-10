package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import org.peatplatform.client.model.Meta;
import org.peatplatform.client.model.Type;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TypeList {
  @JsonProperty("meta")
  private Meta meta = null;
  @JsonProperty("result")
  private List<Type> result = new ArrayList<Type>();
  public Meta getMeta() {
    return meta;
  }
  public void setMeta(Meta meta) {
    this.meta = meta;
  }

  public List<Type> getResult() {
    return result;
  }
  public void setResult(List<Type> result) {
    this.result = result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class TypeList {\n");
    sb.append("  meta: ").append(meta).append("\n");
    sb.append("  result: ").append(result).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

