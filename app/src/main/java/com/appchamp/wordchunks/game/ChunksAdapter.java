package com.appchamp.wordchunks.game;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appchamp.wordchunks.R;
import com.appchamp.wordchunks.models.realm.Chunk;
import com.orhanobut.logger.Logger;

import java.util.List;

import static com.appchamp.wordchunks.util.Constants.CHUNK_STATE_GONE;
import static com.appchamp.wordchunks.util.Constants.CHUNK_STATE_NORMAL;
import static com.appchamp.wordchunks.util.Constants.NUMBER_OF_CHUNKS;


public class ChunksAdapter extends RecyclerView.Adapter<ChunksAdapter.ViewHolder> {

    private List<Chunk> chunks;
    private ChunksAdapter.OnItemClickListener listener;

    ChunksAdapter(List<Chunk> chunks) {
        setChunks(chunks);
    }

    public void updateChunks(List<Chunk> chunks) {
        setChunks(chunks);
        notifyDataSetChanged();
    }

    private void setChunks(List<Chunk> chunks) {
        if (chunks != null) {
            this.chunks = chunks;
        } else {
            Logger.d("chunks cannot be null");
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    void setOnItemClickListener(ChunksAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ChunksAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View levelView = inflater.inflate(R.layout.item_chunk, parent, false);
        return new ChunksAdapter.ViewHolder(levelView);
    }

    @Override
    public void onBindViewHolder(ChunksAdapter.ViewHolder holder, int i) {
        int chunkPos = chunks.get(i).getPosition();
        final Chunk chunk = chunks.get(chunkPos);
        final long chunkState = chunk.getState();

        // Normal chunk state
        if (chunkState == CHUNK_STATE_NORMAL) {
            holder.rlChunk.setBackgroundColor(
                    holder.itemView.getResources().getColor(R.color.white));
        }
        // Clicked chunk state
        else if (chunkState > CHUNK_STATE_NORMAL) {
            holder.rlChunk.setBackgroundColor(
                    holder.itemView.getResources().getColor(R.color.white_30_percent));
        }
        // Gone chunk state
        else if (chunkState == CHUNK_STATE_GONE) {
            holder.rlChunk.setVisibility(View.INVISIBLE);
        }
        holder.tvChunk.setText(chunk.getChunk());
    }

    @Override
    public int getItemCount() {
        return NUMBER_OF_CHUNKS;
    }

    @Override
    public long getItemId(int i) {
        return chunks.get(i).getPosition();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rlChunk;
        TextView tvChunk;

        ViewHolder(View itemView) {
            super(itemView);

            rlChunk = (RelativeLayout) itemView.findViewById(R.id.rlChunk);
            tvChunk = (TextView) itemView.findViewById(R.id.tvChunk);

            // Setup the click listener
            itemView.setOnClickListener(v -> {
                // Triggers click upwards to the adapter on click
                if (listener != null) {
                    int position = chunks.get(getAdapterPosition()).getPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView, position);
                    }
                }
            });
        }
    }
}