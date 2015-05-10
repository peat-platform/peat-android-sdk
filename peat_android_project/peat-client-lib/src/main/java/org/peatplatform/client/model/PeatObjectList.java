package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import org.peatplatform.client.model.Meta;
import org.peatplatform.client.model.PeatObject;
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeatObjectList {
  @JsonProperty("Meta")
  private Meta Meta = null;
  @JsonProperty("result")
  private List<PeatObject> result = new ArrayList<PeatObject>();
  public Meta getMeta() {
    return Meta;
  }
  public void setMeta(Meta Meta) {
    this.Meta = Meta;
  }

  public List<PeatObject> getResult() {
    return result;
  }
  public void setResult(List<PeatObject> result) {
    this.result = result;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class PeatObjectList {\n");
    sb.append("  Meta: ").append(Meta).append("\n");
    sb.append("  result: ").append(result).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

