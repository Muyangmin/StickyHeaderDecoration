package org.mym.stickyheaderdecoration;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mym.ui.decoration.library.StickyHeaderAdapter;
import org.mym.ui.decoration.library.StickyHeaderDecoration;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = ((RecyclerView) findViewById(R.id.main_recyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter());
        recyclerView.addItemDecoration(new StickyHeaderDecoration(
                ((ItemAdapter) recyclerView.getAdapter())
        ));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    class ItemHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ItemHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.item_textView));
        }

        void displayData(int position) {
            textView.setText("Item " + position);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public HeaderHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.header_textView));
        }

        void displayData(int position) {
            textView.setText("Header " + position);
        }
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemHolder> implements
            StickyHeaderAdapter<HeaderHolder> {

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_main, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.displayData(position);
        }

        @Override
        public int getItemCount() {
            return 50;
        }

        @Override
        public long getHeaderId(int childAdapterPosition) {
            return childAdapterPosition / 5;
        }

        @NonNull
        @Override
        public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_header, parent, false);
            return new HeaderHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(@NonNull HeaderHolder holder, int childAdapterPosition) {
            holder.displayData((int) getHeaderId(childAdapterPosition));
        }
    }
}
