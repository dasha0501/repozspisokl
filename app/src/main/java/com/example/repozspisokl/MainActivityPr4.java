package com.example.repozspisokl;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPr4 extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main3);

        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.editText);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    public void onClick(View view) {
        mProgressBar.setVisibility(View.VISIBLE);

        GitHubServicePr4 gitHubService = GitHubServicePr4.retrofit.create(GitHubServicePr4.class);
        // часть слова
        final Call<GitResult> call =
                gitHubService.getUsers(editText.getText().toString());

        call.enqueue(new Callback<GitResult>() {
            @Override
            public void onResponse(Call<GitResult> call, Response<GitResult> response) {
                // response.isSuccessful() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    GitResult result = response.body();

                    // Получаем json из github-сервера и конвертируем его в удобный вид
                    // Покажем только первого пользователя
                    String user = "Аккаунт Github: " + result.getItems().get(0).getLogin();
                    mTextView.setText(user);
                    Log.i("Git", String.valueOf(result.getItems().size()));

                    mProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    int statusCode = response.code();

                    // handle request errors yourself
                    ResponseBody errorBody = response.errorBody();
                    try {
                        mTextView.setText(errorBody.string());
                        mProgressBar.setVisibility(View.INVISIBLE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<GitResult> call, Throwable throwable) {
                mTextView.setText("Что-то пошло не так: " + throwable.getMessage());
            }
        });
    }
    public void Back(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivityPr3.class);
        startActivity(intent);
    }
}