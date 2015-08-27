package org.peatplatform.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContextEntry {
  /* Identifier for the context */
  @JsonProperty("@property_name")
  private String propertyName = null;
  /* The type's semantic context (optional?) */
  @JsonProperty("@type")
  private String type = null;
  /* Is this property required or optional */
  @JsonProperty("@required")
  private Boolean required = null;
  /* Is this entry a single value or an array with multiple values */
  @JsonProperty("@multiple")
  private Boolean multiple = null;
  /* This property can only be set to the values listed in the allowed_values array. */
  @JsonProperty("@allowed_values")
  private List<String> allowedValues = new ArrayList<String>();
  /* User friendly description of the object member */
  @JsonProperty("@description")
  private String description = null;
  public String getPropertyName() {
    return propertyName;
  }
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }

  public Boolean getRequired() {
    return required;
  }
  public void setRequired(Boolean required) {
    this.required = required;
  }

  public Boolean getMultiple() {
    return multiple;
  }
  public void setMultiple(Boolean multiple) {
    this.multiple = multiple;
  }

  public List<String> getAllowedValues() {
    return allowedValues;
  }
  public void setAllowedValues(List<String> allowedValues) {
    this.allowedValues = allowedValues;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class ContextEntry {\n");
    sb.append("  property_name: ").append(propertyName).append("\n");
    sb.append("  type: ").append(type).append("\n");
    sb.append("  required: ").append(required).append("\n");
    sb.append("  multiple: ").append(multiple).append("\n");
    sb.append("  allowed_values: ").append(allowedValues).append("\n");
    sb.append("  description: ").append(description).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

