package noteshare.noteshare;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class LoginActivity extends ActionBarActivity {

    private EditText emailET, passwordET;
    private Button signInButton;
    private TextView forgotTV, newUserTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        emailET = (EditText) findViewById(R.id.login_email);
        passwordET = (EditText) findViewById(R.id.login_password);
        signInButton = (Button) findViewById(R.id.login_sign_in_btn);
        forgotTV = (TextView) findViewById(R.id.login_forgot);
        newUserTV = (TextView) findViewById(R.id.login_new);

        emailET.setBackgroundDrawable(null);
        passwordET.setBackgroundDrawable(null);

        newUserTV.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

}
