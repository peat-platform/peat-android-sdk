package eu.openiict.client.async.models;

/**
 * Created by dmccarthy on 14/11/14.
 */
public interface ICloudletIdResponse {

    public void onSuccess(String cloudletID);

    public void onFailure();

}
