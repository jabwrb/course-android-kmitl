package kmitl.lab07.weerabhat58070128.lazyinstagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import kmitl.lab07.weerabhat58070128.lazyinstagram.R;
import kmitl.lab07.weerabhat58070128.lazyinstagram.model.Post;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int LAYOUT_TYPE_GRID = 0;
    public static final int LAYOUT_TYPE_LIST = 1;

    private Context context;
    private Post[] posts;
    private int itemLayout;

    public PostAdapter(Context context, Post[] posts) {
        this.context = context;
        this.posts = posts;
        this.itemLayout = LAYOUT_TYPE_GRID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;

        switch (viewType) {
            case LAYOUT_TYPE_GRID:
                itemView = inflater.inflate(R.layout.item_grid, null, false);
                return new GridViewHolder(itemView);

            case LAYOUT_TYPE_LIST:
                itemView = inflater.inflate(R.layout.item_list, parent, false);
                return new ListViewHolder(itemView);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case LAYOUT_TYPE_GRID:
                GridViewHolder gridViewHolder = (GridViewHolder) holder;
                Glide.with(context).load(posts[position].getUrl()).into(gridViewHolder.image);
                break;

            case LAYOUT_TYPE_LIST:
                ListViewHolder listViewHolder = (ListViewHolder) holder;
                Glide.with(context).load(posts[position].getUrl()).into(listViewHolder.image);
                listViewHolder.textViewLike.setText(posts[position].getLike() + " likes");
                listViewHolder.textViewComment.setText(posts[position].getComment() + " comments");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    @Override
    public int getItemViewType(int position) {
        return itemLayout;
    }

    public void setItemLayout(int itemLayout) {
        this.itemLayout = itemLayout;
    }
}
