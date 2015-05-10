package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
  /* limit */
  @JsonProperty("limit")
  private Integer limit = null;
  /* offset */
  @JsonProperty("offset")
  private Integer offset = null;
  /* total_count */
  @JsonProperty("total_count")
  private Integer totalCount = null;
  /* prev */
  @JsonProperty("prev")
  private String prev = null;
  /* next */
  @JsonProperty("next")
  private String next = null;
  public Integer getLimit() {
    return limit;
  }
  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public Integer getOffset() {
    return offset;
  }
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getTotalCount() {
    return totalCount;
  }
  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public String getPrev() {
    return prev;
  }
  public void setPrev(String prev) {
    this.prev = prev;
  }

  public String getNext() {
    return next;
  }
  public void setNext(String next) {
    this.next = next;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class Meta {\n");
    sb.append("  limit: ").append(limit).append("\n");
    sb.append("  offset: ").append(offset).append("\n");
    sb.append("  total_count: ").append(totalCount).append("\n");
    sb.append("  prev: ").append(prev).append("\n");
    sb.append("  next: ").append(next).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

