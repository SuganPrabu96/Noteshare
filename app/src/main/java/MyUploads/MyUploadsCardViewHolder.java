package MyUploads;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import noteshare.noteshare.R;

/**
 * Created by Suganprabu on 03-07-2015.
 */
public class MyUploadsCardViewHolder extends RecyclerView.ViewHolder{

    public ImageView share;
    public TextView views, downloads;
    public LinearLayout layout;
    private Context context;

    public MyUploadsCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        share = (ImageView) itemView.findViewById(R.id.card_my_uploads_share);
        views = (TextView) itemView.findViewById(R.id.card_my_uploads_view_count);
        downloads = (TextView) itemView.findViewById(R.id.card_my_uploads_uploads_count);
        layout = (LinearLayout) itemView.findViewById(R.id.card_my_uploads_layout);
    }}

