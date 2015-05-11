package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Meta {
  /* Uri of the previous page relative to the current page settings. */
  @JsonProperty("previous")
  private String previous = null;
  /* Total items count for the all collection */
  @JsonProperty("total_count")
  private Integer totalCount = null;
  /* Specify the offset to start displaying element on a page. */
  @JsonProperty("offset")
  private Integer offset = null;
  /* Specify the number of element to display per page. */
  @JsonProperty("limit")
  private Integer limit = null;
  /* Uri of the next page relative to the current page settings. */
  @JsonProperty("next")
  private String next = null;
  public String getPrevious() {
    return previous;
  }
  public void setPrevious(String previous) {
    this.previous = previous;
  }

  public Integer getTotalCount() {
    return totalCount;
  }
  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public Integer getOffset() {
    return offset;
  }
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  public Integer getLimit() {
    return limit;
  }
  public void setLimit(Integer limit) {
    this.limit = limit;
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
    sb.append("  previous: ").append(previous).append("\n");
    sb.append("  total_count: ").append(totalCount).append("\n");
    sb.append("  offset: ").append(offset).append("\n");
    sb.append("  limit: ").append(limit).append("\n");
    sb.append("  next: ").append(next).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

