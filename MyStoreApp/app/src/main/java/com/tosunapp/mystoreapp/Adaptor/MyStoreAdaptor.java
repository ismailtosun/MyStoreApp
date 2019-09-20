package com.tosunapp.mystoreapp.Adaptor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tosunapp.mystoreapp.Model.Store;
import com.tosunapp.mystoreapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MyStoreAdaptor extends RecyclerView.Adapter<MyStoreAdaptor.ViewHolder> {

    private List<Store> storeList;
    private static MyStoreAdaptor.MyClickListener sClickListener;
    private final Context context;

    public MyStoreAdaptor(Context context, List<Store> storeList) {
        this.storeList = storeList;
        this.context = context;
    }



    public interface MyClickListener {
        void onItemClick(int position, View v);
    }


    @NonNull
    @Override
    public MyStoreAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_item_my_store, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStoreAdaptor.ViewHolder holder, int position) {

        Store store = storeList.get(position);

        holder.date.setText(store.getDate());
        holder.month.setText(getMonthNameFromNumber(store.getMonth()));
        holder.marketName.setText(store.getMarketName());
        holder.orderName.setText(store.getOrderName());
        String productPrice = String.valueOf(store.getProductPrice()) + " TL";
        holder.productPrice.setText(productPrice);
        holder.productState.setText(store.getProductState());
        holder.orderDetail.setText(store.getProductDetail().getOrderDetail());
        String summaryPrice = String.valueOf(store.getProductDetail().getSummaryPrice()) + " TL";
        holder.summaryPrice.setText(summaryPrice);

        switch (store.getProductState()) {
            case "Yolda":

                holder.ln_productState.setBackgroundColor(Color.parseColor("#66cd00"));
                holder.productState.setTextColor(Color.parseColor("#66cd00"));
            break;

            case "Hazırlanıyor":
                holder.ln_productState.setBackgroundColor(Color.parseColor("#FFA500"));
                holder.productState.setTextColor(Color.parseColor("#FFA500"));
            break;

            case "Onay Bekliyor":

                holder.ln_productState.setBackgroundColor(Color.parseColor("#ee2c2c"));
                holder.productState.setTextColor(Color.parseColor("#ee2c2c"));
            break;
        }

        if (store.isClicked()) {
            holder.ln_summary.setVisibility(View.VISIBLE);
        } else {
            holder.ln_summary.setVisibility(View.GONE);
        }
    }


    public String getMonthNameFromNumber(String number) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMMM");
        cal.set(Calendar.MONTH, Integer.parseInt(number));
        String month_name = month_date.format(cal.getTime());
        return month_name;
    }


    @Override
    public int getItemCount() {
        return storeList.size();
    }


    public void setOnItemClickListener(MyStoreAdaptor.MyClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View container;
        TextView date, month, marketName, orderName, productPrice, productState, orderDetail, summaryPrice;
        LinearLayout ln_summary,ln_productState;

        public ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.tv_date);
            month = itemView.findViewById(R.id.tv_month);
            marketName = itemView.findViewById(R.id.tv_marketName);
            orderName = itemView.findViewById(R.id.tv_orderName);
            productPrice = itemView.findViewById(R.id.tv_productPrice);
            productState = itemView.findViewById(R.id.tv_productState);
            orderDetail = itemView.findViewById(R.id.tv_orderDetail);
            summaryPrice = itemView.findViewById(R.id.tv_summaryPrice);
            ln_summary = itemView.findViewById(R.id.ln_summary);
            ln_productState = itemView.findViewById(R.id.ln_productState);
            container = itemView;


            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }
    }


}

