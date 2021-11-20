package fr.dut.ptut2021.adapters.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.User;

public class UserAdapter extends RecyclerView.Adapter<MyViewHolderUser> {

    Context  context;
    boolean isInSetting;
    List<User> listUser;

    public UserAdapter(Context context, List<User> listUser, boolean isInSetting) {
        this.context = context;
        this.listUser = listUser;
        this.isInSetting = isInSetting;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderUser(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false), isInSetting);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUser holder, int position) {
        holder.name.setText(listUser.get(position).getUserName());
        holder.avatar.setImageResource(listUser.get(position).getUserImage());
        holder.settingIcon.setColorFilter(ContextCompat.getColor(context, R.color.black));
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}
