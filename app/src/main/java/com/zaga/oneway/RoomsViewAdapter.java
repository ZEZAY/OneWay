package com.zaga.oneway;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomsViewAdapter extends RecyclerView.Adapter<RoomsViewAdapter.RoomViewHolder> {

    private ArrayList<Room> rooms;
    // create OnItemClickListener
    private OnItemClickListener listener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public RoomsViewAdapter(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView roomName;

        private RoomViewHolder(View itemView, final OnItemClickListener roomListener) {
            super(itemView);
            // R.id.gameName is in the """card_game.xml"""
            roomName = itemView.findViewById(R.id.tv_room_name);

            // set onItemClick
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (roomListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            roomListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RoomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_card, parent, false);

        RoomViewHolder gvh = new RoomViewHolder(view, listener);
        return gvh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RoomViewHolder holder, int position) {
        // - get element from your data set at this position
        Room card = rooms.get(position);
        // - replace the contents of the view with that element
        holder.roomName.setText(card.getRoomName());
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public void updateData(ArrayList<Room> room) {
        rooms.clear();
        rooms.addAll(room);
        notifyDataSetChanged();
    }

    public void addItem(int position, Room room) {
        rooms.add(position, room);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        rooms.remove(position);
        notifyItemRemoved(position);
    }

}
