package MyUploads;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import noteshare.noteshare.R;

/**
 * Created by Suganprabu on 03-07-2015.
 */
public class MyUploadsRecyclerViewAdapter extends RecyclerView.Adapter<MyUploadsCardViewHolder> {

    private ArrayList<MyUploadsItemsClass> items;
    private Context context;
    TextView tv;

    public MyUploadsRecyclerViewAdapter(ArrayList<MyUploadsItemsClass> items,Context context){
        this.items = items;
        this.context = context;
        Log.d("adapter","created");
    }

    @Override
    public MyUploadsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.card_my_uploads, parent, false);
        MyUploadsCardViewHolder vH = new MyUploadsCardViewHolder(context, v);
        return vH;
    }

    @Override
    public void onBindViewHolder(MyUploadsCardViewHolder holder, int position) {

        final MyUploadsItemsClass item = items.get(position);

        holder.views.setText(String.valueOf(item.getViews()));
        holder.downloads.setText(String.valueOf(item.getDownloads()));

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
