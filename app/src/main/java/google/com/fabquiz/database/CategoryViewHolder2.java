package google.com.fabquiz.database;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import google.com.fabquiz.R;
import online.Category.ItemClickListener;


/**
 * Created by user on 30-12-2017.
 */

public class CategoryViewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener{
public TextView category_name;
public ImageView category_image;
public Button button;
private ItemClickListener itemClickListener;

    public CategoryViewHolder2(View itemView) {
        super(itemView);
        category_image=(ImageView) itemView.findViewById(R.id.cat_image);
        category_name=(TextView) itemView.findViewById(R.id.cat_name);
        button=(Button) itemView.findViewById(R.id.button);
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
