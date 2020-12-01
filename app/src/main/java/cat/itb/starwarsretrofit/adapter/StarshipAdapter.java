package cat.itb.starwarsretrofit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.itb.starwarsretrofit.R;
import cat.itb.starwarsretrofit.model.Starship;

public class StarshipAdapter extends RecyclerView.Adapter<StarshipAdapter.StarshipHolder> {
    private List<Starship> starships;

    public StarshipAdapter(List<Starship> starships) {
        this.starships = starships;
    }

    @NonNull
    @Override
    public StarshipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new StarshipHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StarshipHolder holder, int position) {
        holder.tvName.setText(starships.get(position).getName());
        holder.tvManufacturer.setText(starships.get(position).getManufacturer());
        holder.tvSpeed.setText(starships.get(position).getSpeed());
    }

    @Override
    public int getItemCount() {
        return starships.size();
    }

    public void setData(List<Starship> starships){
        this.starships = starships;
        notifyDataSetChanged();
    }

    public class StarshipHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvManufacturer;
        TextView tvSpeed;

        public StarshipHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_value);
            tvManufacturer = itemView.findViewById(R.id.tv_manufacturer_value);
            tvSpeed = itemView.findViewById(R.id.tv_speed_value);
        }
    }
}
