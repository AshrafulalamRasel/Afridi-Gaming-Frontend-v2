package com.itvillage.afridigaming.ui.me;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.itvillage.afridigaming.LoginActivity;
import com.itvillage.afridigaming.PasswordChange;
import com.itvillage.afridigaming.PaymentHistoryActivity;
import com.itvillage.afridigaming.PaymentReportActivity;
import com.itvillage.afridigaming.R;
import com.itvillage.afridigaming.UserBalanceActivity;
import com.itvillage.afridigaming.WithdrawHistoryActivity;
import com.itvillage.afridigaming.dto.response.UserCreateProfileResponse;
import com.itvillage.afridigaming.myProfileAdding;
import com.itvillage.afridigaming.services.GetUserService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MeFragment extends Fragment {

    private MeViewModel mViewModel;
    private TextView userProfileName, availableBalance, totalkill, totalWins, show_email;
    private View view;

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.me_fragment, container, false);
        ConstraintLayout myProfile = view.findViewById(R.id.myProfile);
        ConstraintLayout moneyBag = view.findViewById(R.id.moneyBag);
        ConstraintLayout logoutUser = view.findViewById(R.id.logoutUser);
        ConstraintLayout changePassword = view.findViewById(R.id.changePassword);
        ConstraintLayout help = view.findViewById(R.id.help);
        ConstraintLayout share = view.findViewById(R.id.share);
        ConstraintLayout trns_history = view.findViewById(R.id.trns_history);

        userProfileName = view.findViewById(R.id.userProfileName);
        availableBalance = view.findViewById(R.id.availableBalance);
        totalkill = view.findViewById(R.id.totalkill);
        totalWins = view.findViewById(R.id.totalWins);
        show_email = view.findViewById(R.id.show_email);


        getUserProfile();

        trns_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_trs_choice_dialog, viewGroup, false);

                Button w_history = dialogView.findViewById(R.id.w_history);
                Button p_history = dialogView.findViewById(R.id.p_history);
                Button p_report = dialogView.findViewById(R.id.p_report);

                w_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dialogView.getContext(), WithdrawHistoryActivity.class));
                    }
                });
                p_history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dialogView.getContext(), PaymentHistoryActivity.class));
                    }
                });

                p_report.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(dialogView.getContext(), PaymentReportActivity.class));
                    }
                });

                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String link = getResources().getString(R.string.share_link);
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Afridi Gaming");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + link + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                ViewGroup viewGroup = v.findViewById(android.R.id.content);
                View dialogView = LayoutInflater.from(v.getContext()).inflate(R.layout.custom_add_help_line, viewGroup, false);
                ImageView facebook = dialogView.findViewById(R.id.facebook);
                ImageView youtube = dialogView.findViewById(R.id.youtube);
                ImageView telegram = dialogView.findViewById(R.id.telegram);
                facebook.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = getResources().getString(R.string.facebook);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    }
                });
                youtube.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = getResources().getString(R.string.youtubelink);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    }
                });

                telegram.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String link = getResources().getString(R.string.telegramlink);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                    }
                });
                builder.setView(dialogView);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        moneyBag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), UserBalanceActivity.class));
            }
        });
        logoutUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), LoginActivity.class));
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), myProfileAdding.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(view.getContext(), PasswordChange.class));
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MeViewModel.class);
        // TODO: Use the ViewModel
    }


    @SuppressLint("CheckResult")
    private void getUserProfile() {

        GetUserService getUserService = new GetUserService(view.getContext().getApplicationContext());
        Observable<UserCreateProfileResponse> userCreateProfileResponseObservable =
                getUserService.getUserProfile();

        userCreateProfileResponseObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getUserProfile -> {

                    userProfileName.setText(getUserProfile.getFirstName());
                    availableBalance.setText(Double.toString(getUserProfile.getAcBalance()));
                    totalkill.setText(Integer.toString(getUserProfile.getTotalKill()));
                    totalWins.setText(Double.toString(getUserProfile.getTotalEarn()));
                    show_email.setText(getUserProfile.getMobileNo());

                }, throwable -> {
                    throwable.printStackTrace();
                }, () -> {

                });

    }

}