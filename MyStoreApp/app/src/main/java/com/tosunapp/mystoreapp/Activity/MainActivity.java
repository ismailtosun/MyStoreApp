package com.tosunapp.mystoreapp.Activity;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.tosunapp.mystoreapp.Adaptor.MyStoreAdaptor;
import com.tosunapp.mystoreapp.BaseActivity.BaseActivity;
import com.tosunapp.mystoreapp.Model.Store;
import com.tosunapp.mystoreapp.R;
import com.tosunapp.mystoreapp.Rest.ApiClient;
import com.tosunapp.mystoreapp.Rest.ApiInterface;
import com.tosunapp.mystoreapp.Utils.Constant;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity implements MyStoreAdaptor.MyClickListener {

    /**
     * Activity class name holder variable
     */
    private static final String CLASS_NAME = MainActivity.class.getSimpleName();
    /**
     * Variables
     */
    private RecyclerView mrv;
    private MyStoreAdaptor mAdaptor;
    private List<Store> mlist;
    private Button myOrdersBtn,logOutBtn;

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected Activity getActivity() {
        return this;
    }

    @Override
    protected void initView() {

        mlist = new ArrayList<>();

        myOrdersBtn = findViewById(R.id.btn_myOrders);
        logOutBtn = findViewById(R.id.btn_logOut);
        mrv = findViewById(R.id.rv_myStore);
        mAdaptor = new MyStoreAdaptor(this, mlist);
        mrv.setAdapter(mAdaptor);
        mAdaptor.setOnItemClickListener(this);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setListeners();
        getStoreList();

    }

    private void setListeners() {

        myOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStoreList();
            }
        });


        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(getActivity())
                        .setTitle(getString(R.string.warning))
                        .setMessage(getString(R.string.are_you_sure_to_log_out))
                        .setPositiveButton(getString(R.string.okey), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                tinyDb.remove(Constant.isChecked);
                                Intent i = new Intent(getActivity(), LoginActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(R.drawable.warning)
                        .show();

            }
        });

    }


    public void getStoreList() {

        if (isOnline()) {
            showProgressDialog(getString(R.string.please_wait_message));
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<Store>> call = apiService.getStoreList();
            call.enqueue(new Callback<List<Store>>() {
                @Override
                public void onResponse(Call<List<Store>> call, Response<List<Store>> response) {
                    hideProgressDialog();
                    if (response.isSuccessful()) {
                        List<Store> list = response.body();
                        mlist.clear();
                        mlist.addAll(list);
                        mAdaptor.notifyDataSetChanged();
                    } else {
                        getAlert(getString(R.string.error_message));
                    }

                }

                @Override
                public void onFailure(Call<List<Store>> call, Throwable t) {
                    hideProgressDialog();
                    Log.e(CLASS_NAME, t.toString());
                }
            });

        } else {

            Snackbar snackbar = Snackbar.make(mrv, getString(R.string.please_wait_message), Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    //Adaptor item click listener
    @Override
    public void onItemClick(int position, View v) {

        if (mlist.get(position).isClicked()) {
            mlist.get(position).setClicked(false);
        } else {
            mlist.get(position).setClicked(true);
        }
        mAdaptor.notifyItemChanged(position);
    }
}
