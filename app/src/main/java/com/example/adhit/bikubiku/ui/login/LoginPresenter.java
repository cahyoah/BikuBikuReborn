package com.example.adhit.bikubiku.ui.login;

import android.content.Context;

import com.example.adhit.bikubiku.R;
import com.example.adhit.bikubiku.data.model.User;
import com.example.adhit.bikubiku.data.network.RetrofitClient;
import com.example.adhit.bikubiku.util.ShowAlert;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by adhit on 03/01/2018.
 */

public class LoginPresenter {
    private LoginView loginView;

    public LoginPresenter(LoginView loginView){
        this.loginView = loginView;
    }

    public void Login(final Context context, String username, String password){
        ShowAlert.showProgresDialog(context);
        RetrofitClient.getInstance()
                .getApi()
                .login(username, password)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        loginView.moveActivity(false);
                        if(response.isSuccessful()){
                            JsonObject body = response.body();
                            boolean status = body.get("status").getAsBoolean();
                            if(status){
                                JsonObject userObject = body.get("result").getAsJsonObject();
                                Type type = new TypeToken<User>(){}.getType();
                                User user = new Gson().fromJson(userObject, type);
                                //listGalleryView.showData(carList);
                                loginView.moveActivity(true);
                                loginView.showMessage("Selamat Datang " + user.getNama());
                            }else{
                                String message = body.get("message").getAsString();
                                loginView.showMessageSnackbar(message);
                            }
                        }else {
                            loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        }
                        ShowAlert.closeProgresDialog();
                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        loginView.showMessageSnackbar(context.getResources().getString(R.string.text_login_failed));
                        ShowAlert.closeProgresDialog();
                    }
                });
    }
}