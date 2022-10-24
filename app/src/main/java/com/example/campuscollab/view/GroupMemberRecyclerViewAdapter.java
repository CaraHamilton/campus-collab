package com.example.campuscollab.view;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.campuscollab.view.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.campuscollab.databinding.FragmentGroupMemberBinding;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class GroupMemberRecyclerViewAdapter extends RecyclerView.Adapter<GroupMemberRecyclerViewAdapter.ViewHolder> {

    private final List<PlaceholderItem> mValues;

    public GroupMemberRecyclerViewAdapter(List<PlaceholderItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentGroupMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).content);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public PlaceholderItem mItem;

        public ViewHolder(FragmentGroupMemberBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}