package fr.dut.ptut2021.adapters.theme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import fr.dut.ptut2021.R;
import fr.dut.ptut2021.models.Theme;

public class ThemeAdapter extends RecyclerView.Adapter<MyViewHolderTheme> {

    Context  context;
    List<Theme> listTheme;

    public ThemeAdapter(Context context, List<Theme> listTheme) {
        this.context = context;
        this.listTheme = listTheme;
    }

    @NonNull
    @Override
    public MyViewHolderTheme onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolderTheme(LayoutInflater.from(context).inflate(R.layout.item_theme, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderTheme holder, int position) {
        holder.name.setText(listTheme.get(position).getThemeName());
        holder.image.setImageResource(listTheme.get(position).getThemeImage());
    }

    @Override
    public int getItemCount() {
        return listTheme.size();
    }
}
