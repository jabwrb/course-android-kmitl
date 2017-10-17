package kmitl.lab07.weerabhat58070128.lazyinstagram.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import kmitl.lab07.weerabhat58070128.lazyinstagram.R;

public class GridViewHolder extends RecyclerView.ViewHolder {

    public ImageView image;

    public GridViewHolder(View itemView) {
        super(itemView);
        image = (ImageView) itemView.findViewById(R.id.image);
    }
}
