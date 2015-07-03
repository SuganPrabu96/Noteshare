package MyDownloads;

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
public class MyDownloadsCardViewHolder extends RecyclerView.ViewHolder{

    public ImageView share;
    public TextView views, downloads;
    public LinearLayout layout;
    private Context context;

    public MyDownloadsCardViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        share = (ImageView) itemView.findViewById(R.id.card_my_downloads_share);
        views = (TextView) itemView.findViewById(R.id.card_my_downloads_view_count);
        downloads = (TextView) itemView.findViewById(R.id.card_my_downloads_downloads_count);
        layout = (LinearLayout) itemView.findViewById(R.id.card_my_downloads_layout);
    }}

