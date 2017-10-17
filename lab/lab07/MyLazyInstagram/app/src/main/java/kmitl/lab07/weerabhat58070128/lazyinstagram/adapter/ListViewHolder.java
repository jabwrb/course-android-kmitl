package kmitl.lab07.weerabhat58070128.lazyinstagram.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import kmitl.lab07.weerabhat58070128.lazyinstagram.R;

public class ListViewHolder extends RecyclerView.ViewHolder{

    public ImageView image;
    public TextView textViewLike;
    public TextView textViewComment;

    public ListViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
        textViewComment = (TextView) itemView.findViewById(R.id.textViewComment);
        textViewLike = (TextView) itemView.findViewById(R.id.textViewLike);
    }
}
