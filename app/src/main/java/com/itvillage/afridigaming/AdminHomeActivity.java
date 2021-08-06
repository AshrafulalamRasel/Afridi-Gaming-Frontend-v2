package com.itvillage.afridigaming;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHomeActivity extends AppCompatActivity {
    LinearLayout game_list, add_money,
            withdraw_request_admin, refund_admin,
            bannerUpdate_admin;
    Button logoutAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        game_list = findViewById(R.id.game_list);
        add_money = findViewById(R.id.add_money);
        withdraw_request_admin = findViewById(R.id.withdraw_request_admin);
        logoutAdmin = findViewById(R.id.logoutAdmin);
        refund_admin = findViewById(R.id.refund_admin);
        bannerUpdate_admin = findViewById(R.id.bannerUpdate);

        refund_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RefundActivity.class));
            }
        });
        bannerUpdate_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BannerUpdateActivity.class));
            }
        });
        logoutAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        game_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GameListActivity.class));
            }
        });
        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MoneyApprovalActivity.class));
            }
        });
        withdraw_request_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ApproveWithdrawRequestAdminActivity.class));
            }
        });

//        notification.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
//            }
//        });

    }
}