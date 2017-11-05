package kmitl.lab09.weerabhat58070128.moneyflow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import kmitl.lab09.weerabhat58070128.moneyflow.R;
import kmitl.lab09.weerabhat58070128.moneyflow.model.TransactionInfo;

public class TransactionInfoRecyclerViewAdapter extends RecyclerView.Adapter<TransactionInfoRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<TransactionInfo> data;

    private TransactionInfoRecyclerViewAdapterListener listener;

    public interface TransactionInfoRecyclerViewAdapterListener {
        void onItemClickedListener(TransactionInfo transactionInfo);
    }

    public TransactionInfoRecyclerViewAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        listener = (TransactionInfoRecyclerViewAdapterListener) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_transactioninfo, parent, false);

        ViewHolder holder = new ViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TransactionInfo transactionInfo = data.get(position);

        if (transactionInfo.getType().equals("income")) {
            holder.tv_type.setText("+");
        } else {
            holder.tv_type.setText("-");
        }

        holder.tv_describe.setText(transactionInfo.getDescribe());
        holder.tv_amount.setText(String.valueOf(transactionInfo.getAmount()));

        if (position % 2 != 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#eeeeee"));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClickedListener(transactionInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<TransactionInfo> getData() {
        return data;
    }

    public void setData(List<TransactionInfo> data) {
        this.data = data;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_type;
        TextView tv_describe;
        TextView tv_amount;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            tv_describe = (TextView) itemView.findViewById(R.id.tv_describe);
            tv_amount = (TextView) itemView.findViewById(R.id.tv_amount);
        }
    }
}
