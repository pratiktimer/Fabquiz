package online.Category;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import google.com.fabquiz.R;
/**
 * Created by pratik on 30-12-2017.
 */
public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView category_name;
public ImageView category_image;
private ItemClickListener itemClickListener;
    public CategoryViewHolder(View itemView) {
        super(itemView);
        category_image=(ImageView) itemView.findViewById(R.id.cat_image);
        category_name=(TextView) itemView.findViewById(R.id.cat_name);
        itemView.setOnClickListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition(),false);

    }
}
