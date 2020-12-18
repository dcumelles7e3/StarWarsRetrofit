package cat.itb.starwarsretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import cat.itb.starwarsretrofit.R;
import cat.itb.starwarsretrofit.model.Data;
import cat.itb.starwarsretrofit.model.Film;
import cat.itb.starwarsretrofit.webservice.WebServiceClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmActivity extends AppCompatActivity {
    private List<String> urlFilms;
    private TextView tvTitle;
    private TextView tvOpening;
    private ListIterator<String> iterator;
    private Button b_back;
    private Button b_next;
    private boolean iterForward;      //prevents from iterator skipping

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        final Bundle bundle = getIntent().getExtras();
        urlFilms = bundle.getStringArrayList("filmlist");
        tvOpening = findViewById(R.id.tv_opening);
        tvTitle = findViewById(R.id.tv_title);

        b_back = findViewById(R.id.b_filmback);
        b_next = findViewById(R.id.b_filmnext);
        iterator = urlFilms.listIterator();
        lanzarPeticion(iterator.next());
        iterForward=true;

        b_back.setVisibility(View.INVISIBLE);
        b_back.setOnClickListener(v -> {
            if (iterForward) {
                iterator.previous();
                iterForward = false;
            }
            lanzarPeticion(iterator.previous());
            if (!iterator.hasPrevious()) {
                b_back.setVisibility(View.INVISIBLE);
            }
            b_next.setVisibility(View.VISIBLE);

        });
        b_next.setOnClickListener(v -> {
            if (!iterForward) {
                iterator.next();
                iterForward = true;
            }
            lanzarPeticion(iterator.next());
            if (!iterator.hasNext()) {
                b_next.setVisibility(View.INVISIBLE);
            }
            b_back.setVisibility(View.VISIBLE);
        });
    }

    private synchronized void lanzarPeticion(String url) {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        WebServiceClient client = retrofit.create(WebServiceClient.class);
        Call<Film> call = client.getFilm(url);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                tvOpening.setText(response.body().getOpening());
                tvTitle.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
            }
        });
    }
}