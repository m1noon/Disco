package com.minoon.disco.sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.minoon.disco.sample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a13587 on 15/06/27.
 */
public class SampleAdapter extends RecyclerView.Adapter<SampleAdapter.SampleFragmentViewHolder> {
    private static final String TAG = SampleAdapter.class.getSimpleName();

    private List<String> mDataSet;

    public SampleAdapter() {
        mDataSet = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            mDataSet.add("Button-" + i);
        }
    }

    @Override
    public SampleFragmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cell_sample, null);
        final SampleFragmentViewHolder holder = new SampleFragmentViewHolder(view);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), holder.button.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(SampleFragmentViewHolder holder, int position) {
        holder.button.setText(mDataSet.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class SampleFragmentViewHolder extends RecyclerView.ViewHolder {

        Button button;

        public SampleFragmentViewHolder(View itemView) {
            super(itemView);
            button = (Button) itemView.findViewById(R.id.cell_fragment_sample_btn);
        }
    }
}
