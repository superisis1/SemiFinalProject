package kr.insungjung.semifinalproject.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import kr.insungjung.semifinalproject.R;
import kr.insungjung.semifinalproject.adapters.BankAdapter;
import kr.insungjung.semifinalproject.databinding.FragmentBankListBinding;
import kr.insungjung.semifinalproject.datas.Bank;
import kr.insungjung.semifinalproject.utils.ConnectServer;

public class BankListFragment extends Fragment {

    FragmentBankListBinding binding;

    List<Bank> bankList = new ArrayList<>();
    BankAdapter bankAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bank_list, container, false);

        bankAdapter = new BankAdapter(getActivity(), bankList);
        binding.bankListView.setAdapter(bankAdapter);

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public void onResume() {
        super.onResume();

        // 처음엔 setupEvents 의 onClickListener 안에 들어 있었지만, 생명주기를 고려하여 이 위치로 옮김!!!
        ConnectServer.getRequestInfoBank(getActivity(), new ConnectServer.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                /*실제로 서버에서 돌아온 응답을 메인 액티비티에서 처리*/

                // 먼저 큰덩어리부터 불러온다. (무조건 try/catch 예외처리)
                try {
                    int code = json.getInt("code");

                    /* https://stackoverflow.com/questions/16425146/runonuithread-in-fragment
                     * Fragment 의 경우 그냥 runOnUiThread 만 쓰면 안되고, 앞에 getActivity 를 붙여야 함 */
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (code == 200) {

                                /* Fragment 에서는 context 인자에 getActivity() 를 넣어야 함*/
                                Toast.makeText(getActivity(), "정상적으로 데이터 가져옴", Toast.LENGTH_SHORT).show();

                                try {
                                    JSONObject data = json.getJSONObject("data");
                                    JSONArray banks = data.getJSONArray("banks");

                                    // 누적으로 쌓이지 않도록 미리 한번 비워주는 코드
                                    bankList.clear();

                                    for (int i = 0; i < banks.length(); i++) {
                                        JSONObject bank = banks.getJSONObject(i);

                                               /* String name = bank.getString("name");
                                                Log.d("은행이름", name);*/

                                        Bank bankObj = Bank.getBankFromJson(bank);

                                        bankList.add(bankObj);

                                        bankAdapter.notifyDataSetChanged();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                // 서버에서 주는 에러메세지를 토스트로 출력
                                try {
                                    String message = json.getString("message");
                                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}








