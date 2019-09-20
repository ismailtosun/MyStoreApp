package com.tosunapp.mystoreapp.BaseActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.tosunapp.mystoreapp.TinyDB.TinyDB;


/**
 * Created by ismail.tosun
 */

public abstract class BaseActivity extends AppCompatActivity {

    /**
     * Kullanılacak global değişkenleer
     */
    public static SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public static Activity activity;
    public static String TAG = "";
    protected ProgressDialog pDialog;
    public TinyDB tinyDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutResourceId());
        //sharedPreferences
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();
        editor.apply();
        activity = this;//init
        TAG = activity.getLocalClassName();
        tinyDb = new TinyDB(activity);

    }

    /**
     * Sayfanın view kısmı ve controller kısmı bu methodlarda tanımlanır
     *
     * @return
     */
    protected abstract int getLayoutResourceId();

    protected abstract Activity getActivity();

    protected abstract void initView();

    /**
     * hata veya bir bildirim durumunda çıkartılacak alert yapısı oluşturulur.
     *
     * @param message
     */
    public void getAlert(String message) {

        try {

            AlertDialog.Builder alert = new AlertDialog.Builder(activity);
            alert.setIcon(android.R.drawable.ic_menu_info_details).setTitle("Hata!").setMessage(message);
            alert.setNegativeButton("Tamam", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                }
            });

            AlertDialog dialog = alert.create();
            WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    /**
     * internet erişimi
     */

    public boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnected());
        } else {
            return false;
        }
    }



    /**
     * Yükleme veya her hnagi bir dosya indirme soırasında gösterilecek progress bar
     * Alınan parametre string parametresidir mesaj olarak kullanıcıya gösterilir
     */

    public void showProgressDialog(final String mesages) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                pDialog = new ProgressDialog(getActivity());
                pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pDialog.setMessage(mesages);
                pDialog.setCancelable(true);
                pDialog.show();
            }
        });
    }

    public void hideProgressDialog() {
        pDialog.dismiss();
    }



    public void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return true;
    }

}
