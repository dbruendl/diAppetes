package com.example.diappetes.settings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.diappetes.R;
import com.example.diappetes.persistence.model.Setting;

import java.util.List;

import lombok.Getter;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.ViewHolder> {

    private final List<Setting> settings;

    @Getter
    private final MutableLiveData<Setting> settingChecked;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemDescription;
        private final CheckBox enabled;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemDescription = itemView.findViewById(R.id.item_description);
            enabled = itemView.findViewById(R.id.item_enabled);

            itemView.setOnClickListener(v -> enabled.setChecked(!enabled.isChecked()));
        }
    }

    public SettingsAdapter(List<Setting> settings) {
        this.settings = settings;

        settingChecked = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.settings_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Setting setting = settings.get(position);

        holder.itemDescription.setText(setting.description);
        holder.enabled.setChecked(setting.enabled);
        holder.enabled.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setting.setEnabled(isChecked);
            settingChecked.postValue(setting);
        });
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }
}
