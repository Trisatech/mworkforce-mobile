package com.radya.sfa.view.contact.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.ActivityUtils;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.view.BaseActivity;
import com.radya.sfa.view.contact.Contact;
import com.radya.sfa.view.contact.list.ContactList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactDetailActivity extends BaseActivity {

    @BindView(R.id.icNavLeft)
    ImageView icNavLeft;
    @BindView(R.id.navLeft)
    RelativeLayout navLeft;
    @BindView(R.id.icNavRight)
    ImageView icNavRight;
    @BindView(R.id.navRight)
    RelativeLayout navRight;
    @BindView(R.id.appBarTitle)
    TextView appBarTitle;
    @BindView(R.id.progressLoading)
    ProgressBar progressLoading;

    private Contact contact;
    private Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity);
        ButterKnife.bind(this);

        bundle = getIntent().getExtras();
        if (bundle != null){
            contact = (Contact) bundle.getSerializable(Constant.SERIALIZABLE_NAME);
            bundle.putSerializable(Constant.SERIALIZABLE_NAME, contact);
            initView();
        }
    }

    private void initView() {
        progressLoading.setVisibility(View.GONE);

        Fragment fragment = ContactDetailFragment.newInstance();
        fragment.setArguments(bundle);

        ActivityUtils.replaceFragment(getSupportFragmentManager(), fragment, R.id.contentFrame,  bundle,false);

    }

    @OnClick(R.id.navLeft)
    public void onViewClicked() {
        onBackPressed();
        IntentUtils.backAnimation(this);
    }
}
