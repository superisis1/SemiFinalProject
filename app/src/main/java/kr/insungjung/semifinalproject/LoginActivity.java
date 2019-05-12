package kr.insungjung.semifinalproject;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import kr.insungjung.semifinalproject.databinding.ActivityLoginBinding;
import kr.insungjung.semifinalproject.utils.ConnectServer;
import kr.insungjung.semifinalproject.utils.ContextUtil;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        act.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                1. 아이디와 비번을 뭐라고 입력했는지? 받아오기

                String inputId = act.loginIdEdt.getText().toString();
                String inputPw = act.loginPwEdt.getText().toString();

//                1.2 - 입력받은 ID를 SharedPreferences에 저장.
                ContextUtil.setUserInputId(mContext, inputId);


//                2. 받아온 아이디와 비번이 정말로 올바른 회원지? 검사
//                 아이디/비번이 모두 동일한 사람이 회원 명부에 있는지? 검사.

                ConnectServer.postRequestSignIn(mContext, inputId, inputPw, new ConnectServer.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    int code = json.getInt("code");

                                    if (code == 200) {
//                                        로그인 성공!

                                        JSONObject data = json.getJSONObject("data");
                                        String token = data.getString("token");

                                        if (act.autoLoginCheckBox.isChecked()) {
//                                          자동로그인을 하려고 한다. 사용자가 표시
//                                            로그인 성공 토큰값을 SharedPreference에 저장

                                            ContextUtil.setUserToken(mContext, token);

                                        }

                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        intent.putExtra("userToken", token);
                                        startActivity(intent);
                                        finish();


                                    } else {
//                                        로그인 실패. 왜 실패했는지 AlertDialog

                                        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                                        alert.setTitle("로그인 실패 알림");
                                        alert.setMessage(json.getString("message"));
                                        alert.setPositiveButton("확인", null);
                                        alert.show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    }
                });

//                test123 / Test!123
//                iu0001 / Test!123
//                testorder1 / testorder1
//                gggg1111 / gggg1111

            }
        });
    }

    @Override
    public void setValues() {

        String savedUserId = ContextUtil.getUserInputId(mContext);

        act.loginIdEdt.setText(savedUserId);


        String savedToken = ContextUtil.getUserToken(mContext);
        Log.d("저장된토큰값", String.format("토큰값 : %s", savedToken));
    }

    @Override
    public void bindViews() {
        act = DataBindingUtil.setContentView(this, R.layout.activity_login);
    }
}
