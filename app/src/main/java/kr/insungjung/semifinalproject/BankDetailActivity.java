package kr.insungjung.semifinalproject;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import kr.insungjung.semifinalproject.databinding.ActivityBankDetailBinding;
import kr.insungjung.semifinalproject.datas.Bank;

public class BankDetailActivity extends BaseActivity {

    ActivityBankDetailBinding act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bindViews();
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

    }

    @Override
    public void setValues() {

        ImageView logoImg = act.logoImg;
        TextView bankName = act.bankNameTxt;
        TextView bankCode = act.bankCodeTxt;

        Bank bank = (Bank) getIntent().getSerializableExtra("은행정보");

        Glide.with(mContext).load(bank.logo).into(logoImg);
        bankName.setText(bank.name);
        bankCode.setText(bank.code);

    }

    @Override
    public void bindViews() {

        act = DataBindingUtil.setContentView(this, R.layout.activity_bank_detail);
    }
}
