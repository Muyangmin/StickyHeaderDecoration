package org.mym.stickyheaderdecoration;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mym.ui.decoration.library.SimpleTextHeader;
import org.mym.ui.decoration.library.StickyHeaderAdapter;
import org.mym.ui.decoration.library.StickyHeaderDecoration;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ItemAdapter mAdapter;
    private StickyHeaderDecoration mDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = ((RecyclerView) findViewById(R.id.main_recyclerView));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter();
        recyclerView.setAdapter(mAdapter);

//        mDecoration = new StickyHeaderDecoration(mAdapter);
        mDecoration = new StickyHeaderDecoration(new SimpleTextHeader() {
            @Override
            protected CharSequence getHeaderContent(int childPos) {
                return "Header " + getHeaderId(childPos);
            }

            @Override
            public long getHeaderId(int childAdapterPosition) {
                return childAdapterPosition / 14;
            }
        });
        recyclerView.addItemDecoration(mDecoration);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_invalidate) {
//            mDecoration.invalidate();
            mAdapter.appendDataAndNotifyChanged();
//            recyclerView.invalidate();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        private int mItemCount = 30;

        void appendDataAndNotifyChanged() {
            mItemCount += 10;
            notifyDataSetChanged();
        }

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
            return mItemCount;
        }

        @Override
        public long getHeaderId(int childAdapterPosition) {
            return childAdapterPosition / 14;
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
