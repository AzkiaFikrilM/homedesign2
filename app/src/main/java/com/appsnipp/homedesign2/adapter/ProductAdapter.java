package com.appsnipp.homedesign2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.appsnipp.homedesign2.R;
import com.appsnipp.homedesign2.model.ProductModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserViewHolder> {

    private List<ProductModel> dataList;
    private List<ProductModel> dataListFull;
    private OnItemClickListener mListener;
    private Context mContext;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter(Context mContext, List<ProductModel> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
        dataListFull = new ArrayList<>(dataList);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.kelola_list, parent, false);
        return new UserViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        holder.tv_listTitle.setText(dataList.get(position).getNamaProduk());
        holder.tv_listDesc.setText(dataList.get(position).getDeskripsiProduk());
        holder.tv_listCategory.setText(dataList.get(position).getKategoriProduk());

        Picasso.get()
                .load(dataList.get(position).getFotoProduk())
                .placeholder(R.drawable.restaurant)
                .error(R.drawable.restaurant)
                .fit().into(holder.img_data);
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tv_listTitle, tv_listDesc, tv_listCategory;
        ImageView img_data;
        RelativeLayout relativeLayout;

        public UserViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_listTitle = itemView.findViewById(R.id.tv_listTitle);
            tv_listDesc = itemView.findViewById(R.id.tv_listDescription);
            tv_listCategory = itemView.findViewById(R.id.tv_listCategory);
            relativeLayout = itemView.findViewById(R.id.relative_list);
            img_data = itemView.findViewById(R.id.img_listImage);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
