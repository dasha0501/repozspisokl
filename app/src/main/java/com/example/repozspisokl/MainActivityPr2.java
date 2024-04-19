package com.example.repozspisokl;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPr2 extends AppCompatActivity {

    private ProgressBar mProgressBar;
    private TextView mTextView;

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);

        mTextView = (TextView) findViewById(R.id.textView);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        editText = (EditText) findViewById(R.id.editText);
        mProgressBar.setVisibility(View.INVISIBLE);
    }


    public void onClick(View view) {
        mProgressBar.setVisibility(View.VISIBLE);

        GitHubServicePr2 gitHubService = GitHubServicePr2.retrofit.create(GitHubServicePr2.class);
        final Call<User> call =
                gitHubService.getUser(editText.getText().toString());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // response.isSuccessfull() is true if the response code is 2xx
                if (response.isSuccessful()) {
                    User user = response.body();

                    // Получаем json из github-сервера и конвертируем его в удобный вид
                    mTextView.setText("Аккаунт Github: " + user.getName() +
                            "\nСайт: " + user.getBlog() +
                            "\nКомпания: " + user.getCompany());

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
            public void onFailure(Call<User> call, Throwable throwable) {
                mTextView.setText("Что-то пошло не так: " + throwable.getMessage());
            }
        });
    }

    public void Next(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivityPr3.class);
        startActivity(intent);
    }
    public void Back(View v)
    {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
