package fr.dut.ptut2021.adapters.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import fr.dut.ptut2021.R;

public class MyViewHolderUser extends RecyclerView.ViewHolder {

    RoundedImageView avatar, settingIcon;
    TextView name;
    boolean isInSetting;

    public MyViewHolderUser(@NonNull View itemView, boolean isInSetting) {
        super(itemView);
        this.isInSetting = isInSetting;
        avatar = itemView.findViewById(R.id.userAvatar);
        settingIcon = itemView.findViewById(R.id.icon_modif);
        name = itemView.findViewById(R.id.userName);
        if(isInSetting){
            settingIcon.setVisibility(View.VISIBLE);
            avatar.setAlpha(0.7f);
        }
    }
}
