package Search;

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
public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchCardViewHolder> {

    private ArrayList<SearchItemsClass> items;
    private Context context;
    TextView tv;

    public SearchRecyclerViewAdapter(ArrayList<SearchItemsClass> items,Context context){
        this.items = items;
        this.context = context;
        Log.d("adapter","created");
    }

    @Override
    public SearchCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.card_search, parent, false);
        SearchCardViewHolder vH = new SearchCardViewHolder(context, v);
        return vH;
    }

    @Override
    public void onBindViewHolder(SearchCardViewHolder holder, int position) {

        final SearchItemsClass item = items.get(position);

        holder.views.setText(String.valueOf(item.getViews()));
        holder.downloads.setText(String.valueOf(item.getDownloads()));

    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }
}
