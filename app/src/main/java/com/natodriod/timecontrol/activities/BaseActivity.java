package com.natodriod.timecontrol.activities;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

import com.natodriod.timecontrol.R;

/**
 * Created by natiqmustafa on 03.04.2017.
 */

public class BaseActivity extends AppCompatActivity {


    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.str_singning_in));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

}