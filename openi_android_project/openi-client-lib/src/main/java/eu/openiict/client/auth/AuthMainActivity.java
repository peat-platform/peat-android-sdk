package eu.openiict.client.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import eu.openiict.client.R;

public class AuthMainActivity extends Activity implements OnClickListener {

	Button btnSignIn;
	Button btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.auth_activity_main);
        
        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }
	@Override
	public void onClick(View v) {
		Intent i = null;
        int i1 = v.getId();
        if (i1 == R.id.btnSingIn) {
            //i = new Intent(this, SignInActivity.class);

        } else if (i1 == R.id.btnSignUp) {
            //i = new Intent(this, SignUpActivity.class);

        }
		startActivity(i);
	}


    
}
