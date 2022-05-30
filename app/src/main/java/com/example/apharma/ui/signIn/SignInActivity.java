package com.example.apharma.ui.signIn;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apharma.ui.main.MainActivity;
import com.example.apharma.R;
import com.example.apharma.ui.signUp.SignUpActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Collections;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    private SignInViewModel viewModel;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) goToMainActivity();
            });

    private FirebaseAuth firebaseAuth;
    private TextInputLayout emailInput;
    private TextInputLayout passwordInput;
    private Button loginButton;
    private ImageButton alternativeSignInButton;
    private TextView registerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
        checkIfSignedIn();

        setContentView(R.layout.activity_sign_in);
        initViews();

        firebaseAuth = FirebaseAuth.getInstance();

        emailInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    emailInput.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordInput.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    passwordInput.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        loginButton.setOnClickListener(view -> {
            String email = emailInput.getEditText().getText().toString();
            String password = passwordInput.getEditText().getText().toString();

            if (email.isEmpty()) {
                emailInput.setError("Please provide an email");
                emailInput.requestFocus();
            } else if (password.isEmpty()) {
                passwordInput.setError("Please provide a password");
                passwordInput.requestFocus();
            } else {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInActivity.this, task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    } else {
                        goToMainActivity();
                    }
                });
            }
        });
        alternativeSignInButton.setOnClickListener(this::signIn);
        registerText.setOnClickListener(v -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
    }

    private void initViews() {
        emailInput = findViewById(R.id.sign_in_input_email);
        passwordInput = findViewById(R.id.sign_in_input_password);
        loginButton = findViewById(R.id.frag_sign_in_button_login);
        alternativeSignInButton = findViewById(R.id.sign_in_button_alternative_login);
        registerText = findViewById(R.id.sign_in_text_register);
    }

    private void checkIfSignedIn() {
        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null)
                goToMainActivity();
        });
    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void signIn(View v) {
        List<AuthUI.IdpConfig> providers = Collections.singletonList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build();

        activityResultLauncher.launch(signInIntent);
    }
}