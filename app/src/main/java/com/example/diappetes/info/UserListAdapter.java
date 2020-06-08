package com.example.diappetes.info;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diappetes.databinding.ItemUserBinding;
import com.example.diappetes.persistence.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {
    private final List<User> users;

    static class UserViewHolder extends RecyclerView.ViewHolder {
        final ItemUserBinding itemUserBinding;

        UserViewHolder(@NonNull ItemUserBinding itemUserBinding) {
            super(itemUserBinding.getRoot());

            this.itemUserBinding = itemUserBinding;
        }
    }

    public UserListAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        ItemUserBinding itemUserBinding = ItemUserBinding.inflate(layoutInflater);

        return new UserViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);

        holder.itemUserBinding.email.setText(user.email);
        holder.itemUserBinding.username.setText(user.uid);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
