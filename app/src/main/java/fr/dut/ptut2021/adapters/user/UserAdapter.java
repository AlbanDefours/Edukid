package fr.dut.ptut2021.adapters.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.database.app.User;
import fr.dut.ptut2021.utils.GlobalUtils;

public class UserAdapter extends RecyclerView.Adapter<MyViewHolderUser> {

    Context context;
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
        holder.name.setText(GlobalUtils.cutString(listUser.get(position).getUserName(), 15));

        if (listUser.get(position).getUserImageType() == 0) {
            holder.avatar.setImageResource(Integer.parseInt(listUser.get(position).getUserImage()));
        } else {
            try {
                File f = new File(listUser.get(position).getUserImage());
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                holder.avatar.setImageBitmap(b);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        holder.settingIcon.setColorFilter(ContextCompat.getColor(context, R.color.black));
    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}
