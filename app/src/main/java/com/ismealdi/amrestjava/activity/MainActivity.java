package com.ismealdi.amrestjava.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ismealdi.amrestjava.R;
import com.ismealdi.amrestjava.api.Auth;
import com.ismealdi.amrestjava.model.request.SignInRequest;
import com.ismealdi.amrestjava.model.request.SignUpRequest;
import com.ismealdi.amrestjava.model.response.BaseResponse;
import com.ismealdi.amrestjava.model.schema.User;
import com.ismealdi.amrestjava.util.Dialogs;
import com.ismealdi.amrestjava.util.Networks;
import com.ismealdi.amrestjava.util.Preferences;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Auth auth;
    private Disposable disposable;
    private KProgressHUD progress;

    private EditText inputEmail, inputPassword, inputPasswordConfirm, inputName;
    private Button buttonSignUp, buttonSignIn, buttonSignOut, buttonLoadUser;
    private TextView labelUsers, labelMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initListener();

        isSignIn(new Preferences(this).getToken().isEmpty());
    }

    private void initData() {
        auth = new Networks(getApplicationContext()).bridge().create(Auth.class);
        progress = new Dialogs().initProgressDialog(this);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputPasswordConfirm = findViewById(R.id.inputPasswordConfirm);
        inputName = findViewById(R.id.inputName);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignOut = findViewById(R.id.buttonSignOut);
        buttonLoadUser = findViewById(R.id.buttonLoadUser);

        labelUsers = findViewById(R.id.labelUsers);
        labelMessage = findViewById(R.id.labelMessage);

    }

    private void initListener() {
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();

                if(validate(email, password, "", "", false)) {
                    SignInRequest signInRequest = new SignInRequest(password, email);
                    signIn(signInRequest);
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();
                String confirm = inputPasswordConfirm.getText().toString();
                String name = inputName.getText().toString();

                if(validate(email, password, confirm, name, true)) {
                    SignUpRequest singUpRequest = new SignUpRequest(password, email, name, confirm);
                    signUp(singUpRequest);
                }
            }
        });

        buttonSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        buttonLoadUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUsers();
            }
        });
    }

    private void signUp(SignUpRequest singUpRequest) {
        showProgress();

        disposable = auth.register(singUpRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();
                    message("Berhasi, Hallo" + response.getData().getName());
                    new Preferences(this).storeName(response.getData().getName());
                    new Preferences(this).storeToken(response.getData().getToken());

                    isSignIn(false);
                }, error -> {
                    hideProgress();
                    message(error.getMessage());
                });
    }

    private void getUsers() {
        labelUsers.setText("Processing ..");

        disposable = auth.users().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();
                    String usersText = "";
                    for (User user : response.getData()) {
                        usersText += "," + user.getName();
                    }
                    
                    labelUsers.setText(usersText.substring(1));
                }, error -> {
                    hideProgress();
                    labelUsers.setText("");
                    message(error.getMessage());
                });
    }

    private void signOut() {
        showProgress();

        disposable = auth.signOut().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();
                    message("Keluar");
                    new Preferences(this).storeName("");
                    new Preferences(this).storeToken("");

                    isSignIn(true);
                }, error -> {
                    hideProgress();
                    message(error.getMessage());
                });
    }

    private void signIn(SignInRequest signInRequest) {
        showProgress();

        disposable = auth.signIn(signInRequest).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    hideProgress();
                    message("Berhasi, Hallo" + response.getData().getName());
                    new Preferences(this).storeName(response.getData().getName());
                    new Preferences(this).storeToken(response.getData().getToken());

                    isSignIn(false);
                }, error -> {
                    hideProgress();
                    message(error.getMessage());
                });

    }

    private void isSignIn(boolean state) {
        if(state) {
            buttonSignIn.setVisibility(View.VISIBLE);
            buttonSignUp.setVisibility(View.VISIBLE);
            inputName.setVisibility(View.VISIBLE);
            inputEmail.setVisibility(View.VISIBLE);
            inputPassword.setVisibility(View.VISIBLE);
            inputPasswordConfirm.setVisibility(View.VISIBLE);
            buttonSignOut.setVisibility(View.GONE);
        }else{
            labelMessage.setText("Hallo, " + new Preferences(this).getName());

            buttonSignIn.setVisibility(View.GONE);
            buttonSignUp.setVisibility(View.GONE);
            inputName.setVisibility(View.GONE);
            inputEmail.setVisibility(View.GONE);
            inputPassword.setVisibility(View.GONE);
            inputPasswordConfirm.setVisibility(View.GONE);
            buttonSignOut.setVisibility(View.VISIBLE);
        }
    }

    private void showProgress() {
        if(progress != null) progress.show();
    }

    private void message(String text) {
        labelMessage.setText(text);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                labelMessage.setText(text);
            }
        }, 2500);
    }

    private void hideProgress() {
        if(progress != null) progress.dismiss();
    }

    private Boolean validate( String email, String password, String confirm, String name, Boolean isSignUp) {
        if(email.isEmpty() || password.isEmpty()) {
            message("Email dan password tidak boleh kosong!");
            return false;
        }

        if(password.length() < 8) {
            message("Password kurang dari 8 karakter!");
            return false;
        }

        if(isSignUp) {
            if(!password.equals(confirm)) {
                message("Password tidak sama!");
                return false;
            }

            if(name.isEmpty()) {
                message("Nama tidak boleh kosong!");
                return false;
            }
        }

        return true;
    }
}
