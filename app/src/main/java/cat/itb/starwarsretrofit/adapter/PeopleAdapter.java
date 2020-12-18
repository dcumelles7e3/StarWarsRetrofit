package cat.itb.starwarsretrofit.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cat.itb.starwarsretrofit.R;
import cat.itb.starwarsretrofit.activities.FilmActivity;
import cat.itb.starwarsretrofit.activities.MainActivity;
import cat.itb.starwarsretrofit.model.People;

public class PeopleAdapter extends RecyclerView.Adapter<PeopleAdapter.PeopleHolder> {
    private List<People> people;

    public PeopleAdapter(List<People> people) {
        this.people = people;
    }

    @NonNull
    @Override
    public PeopleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new PeopleHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PeopleHolder holder, int position) {
        holder.tvName.setText(people.get(position).getName());
        holder.tvHeight.setText(people.get(position).getHeight());
        holder.tvEye.setText(people.get(position).getEyeColor());
        holder.tvBirth.setText(people.get(position).getBirth());
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public void setData(List<People> people){
        this.people = people;
        notifyDataSetChanged();
    }

    public class PeopleHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvHeight;
        TextView tvEye;
        TextView tvBirth;

        public PeopleHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name_value);
            tvHeight = itemView.findViewById(R.id.tv_height_value);
            tvEye = itemView.findViewById(R.id.tv_eye_value);
            tvBirth = itemView.findViewById(R.id.tv_birth_value);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), FilmActivity.class);
                    ArrayList<String> films = (ArrayList) people.get(getAdapterPosition()).getFilms();
                    intent.putStringArrayListExtra("filmlist",films);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
