package eu.openiict.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OPENiObject {
  /* The Objects identifier. */
  @JsonProperty("_id")
  private String _id = null;
  /* OPENi Type that this object is an instance of. */
  @JsonProperty("_openi_type")
  private String _openi_type = null;
  /* The Objects values. */
  @JsonProperty("_data")
  private Map _data = null;
  /* Revision number associated with this version of the Object, this number must be presented when updating the object */
  @JsonProperty("_revision")
  private String _revision = null;
  public String get_id() {
    return _id;
  }
  public void set_id(String _id) {
    this._id = _id;
  }

  public String get_openi_type() {
    return _openi_type;
  }
  public void set_openi_type(String _openi_type) {
    this._openi_type = _openi_type;
  }

  public Map get_data() {
    return _data;
  }
  public void set_data(Object _dataObj) {
     _data = (Map) _dataObj;
  }

  public String get_revision() {
    return _revision;
  }
  public void set_revision(String _revision) {
    this._revision = _revision;
  }

  @Override
  public String toString()  {
    StringBuilder sb = new StringBuilder();
    sb.append("class OPENiObject {\n");
    sb.append("  _id: ").append(_id).append("\n");
    sb.append("  _openi_type: ").append(_openi_type).append("\n");
    sb.append("  _data: ").append(_data).append("\n");
    sb.append("  _revision: ").append(_revision).append("\n");
    sb.append("}\n");
    return sb.toString();
  }
}

