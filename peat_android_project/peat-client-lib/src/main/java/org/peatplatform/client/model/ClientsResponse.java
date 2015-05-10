package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import org.peatplatform.client.model.Client;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientsResponse {
  /* An array of Types */
  @JsonProperty("list")
  private List<Client> list = new ArrayList<Client>();
  public List<Client> getList() {
    return list;
  }
  public void setList(List<Client> list) {
    this.list = list;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientsResponse {\n");
    sb.append("  list: ").append(list).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

