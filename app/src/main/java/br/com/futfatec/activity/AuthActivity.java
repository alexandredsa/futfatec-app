package br.com.futfatec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import br.com.futfatec.R;
import br.com.futfatec.content.AppPreferencesData;
import br.com.futfatec.exception.HttpRestException;
import br.com.futfatec.model.auth.ValidationStatus;
import br.com.futfatec.rest.AbstractRestService;
import br.com.futfatec.rest.auth.AuthRestService;

public class AuthActivity extends AppCompatActivity {
    private EditText edtPalavraChave;
    private AuthRestService authRestService;
    private AppPreferencesData preferencesData;
    private ProgressBar progressBar;
    private LinearLayout layoutAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        edtPalavraChave = (EditText) findViewById(R.id.edtPalavraChave);
        layoutAuth = (LinearLayout) findViewById(R.id.layoutAuth);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        preferencesData = new AppPreferencesData(this);
        authRestService = new AuthRestService(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (preferencesData.hasSession()) {
            AuthActivity.this.startActivity(new Intent(AuthActivity.this, MenuActivity.class));
            AuthActivity.this.finish();
        }
    }

    public void enviar(View view) {
        String accessId = edtPalavraChave.getText().toString();

        if (accessId.length() == 0) {
            Toast.makeText(this, getResources().getString(R.string.access_id_vazio), Toast.LENGTH_LONG).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        layoutAuth.setVisibility(View.GONE);
        authRestService.login(new AbstractRestService.OnResponse<ValidationStatus>() {
            @Override
            public void success(ValidationStatus validationStatus) {
                AuthActivity.this.startActivity(new Intent(AuthActivity.this, MenuActivity.class));
                AuthActivity.this.finish();
            }

            @Override
            public void error(HttpRestException restException) {
                progressBar.setVisibility(View.GONE);
                layoutAuth.setVisibility(View.VISIBLE);

                Toast.makeText(AuthActivity.this, getResources().getString(R.string.error_login), Toast.LENGTH_LONG).show();
            }
        }, accessId.toUpperCase());


    }
}
