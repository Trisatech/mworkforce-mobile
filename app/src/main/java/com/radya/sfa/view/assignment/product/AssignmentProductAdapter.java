package com.radya.sfa.view.assignment.product;

//http://stackoverflow.com/questions/26585941/recyclerview-header-and-footer

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.radya.sfa.Constant;
import com.radya.sfa.R;
import com.radya.sfa.util.IntentUtils;
import com.radya.sfa.util.StringUtils;
import com.radya.sfa.util.ToastUtils;
import com.radya.sfa.view.assignment.search.AssignmentSearchActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssignmentProductAdapter extends RecyclerView.Adapter<AssignmentProductAdapter.MyViewHolder> {

    private ArrayList<AssignmentProduct.Product> data;
    private LayoutInflater inflater;
    private Context context;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Point screenSize;

    public AssignmentProductAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ArrayList<AssignmentProduct.Product> getData() {
        return data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.assignment_product_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final AssignmentProduct.Product item = data.get(position);
        holder.position = position;

        final long identity = System.currentTimeMillis();
        holder.identity = identity;

        holder.txtProcuductName.setText(item.getProductName());
        holder.txtProcuductPrice.setText(StringUtils.getRpCurency((long) item.getProductPrice()));

        String url = item.getProductImage();
        if (url != null){
            Glide.with(context).load(item.getProductImage()).into(holder.imgProduct);
        }else {
            Glide.with(context).load(R.drawable.null_image).into(holder.imgProduct);
        }

        holder.edtOrderCount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(holder.edtOrderCount.getWindowToken(), 0);

                    String q = holder.edtOrderCount.getText().toString();
                    if (!q.equals("")) {
                        ((AssignmentProductActivity) context).updateOrderQty(position, Integer.parseInt(q));
                    }

                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int position;
        long identity;

        @BindView(R.id.imgProduct)
        ImageView imgProduct;
        @BindView(R.id.txtProcuductName)
        TextView txtProcuductName;
        @BindView(R.id.txtProcuductPrice)
        TextView txtProcuductPrice;
        @BindView(R.id.edtOrderCount)
        EditText edtOrderCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(null, v, position, 0);
            }
        }
    }
}
