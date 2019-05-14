package kr.insungjung.semifinalproject.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import kr.insungjung.semifinalproject.R;
import kr.insungjung.semifinalproject.databinding.FragmentUserInfoBinding;
import kr.insungjung.semifinalproject.utils.ConnectServer;
import kr.insungjung.semifinalproject.utils.ContextUtil;


public class UserInfoFragment extends Fragment {

    FragmentUserInfoBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_info, container, false);


        // 로그인에 성공한 사람의 토큰을 받아오기.
        String token = ContextUtil.getUserToken(getActivity());

        Log.d("사용자토큰값", token);

        // 받아온 토큰을 가지고 /v2/me_info API 호출, 사용자 데이터 표시
        ConnectServer.getRequestMeInfo(getActivity(), token, new ConnectServer.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            int code = json.getInt("code");

                            if (code == 200) {
//                                정상적으로 데이터 수신
                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");

                                // 프사 경로
                                String profile_image = user.getString("profile_image");
                                // 사용자 이름
                                String name = user.getString("name");
                                // 보유 잔고
                                int balance = user.getInt("balance");

                                // 은행 로고를 따기 전에 bank_code JSONObject 부터
                                JSONObject bank_code = user.getJSONObject("bank_code");
                                // 은행 정보 안에 있는 로고 경로
                                String logo = bank_code.getString("logo");
                                // 은행 이름
                                String bankName = bank_code.getString("name");
                                String billing_account = user.getString("billing_account");

                                // 프로필 이미지 출력
                                Glide.with(getActivity()).load(profile_image).into(binding.profileImg);
                                // 사용자 이름 출력
                                binding.userNameTxt.setText(name);
                                // 사용자 보유 잔고를 ,찍는 양식으로 출력
                                binding.userBalnceTxt.setText(String.format("%,d P", balance));
                                // 은행 로고 출력
                                Glide.with(getActivity()).load(logo).into(binding.bankLogoImg);
                                // 은행 이름 출력
                                binding.bankNameTxt.setText(bankName);
                                // 사용자 은행 계좌 출력
                                binding.bankAccountTxt.setText(billing_account);
                            }
                            else {
                                // 비정상 상태
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();


    }
}