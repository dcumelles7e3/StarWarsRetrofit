package cat.itb.starwarsretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

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
    private List<String> films;
    private TextView tvTitle;
    private TextView tvOpening;

    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private String next;
    private String previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);

        final Bundle bundle = getIntent().getExtras();
        films = bundle.getStringArrayList("filmlist");

        tvOpening = findViewById(R.id.tv_opening);
        tvTitle = findViewById(R.id.tv_title);

        lanzarPeticion(films.get(0));

    }

    private void lanzarPeticion(String url) {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        WebServiceClient client = retrofit.create(WebServiceClient.class);
        Call<Film> call;
            call = client.getFilm(url);
        call.enqueue(new Callback<Film>() {
            @Override
            public void onResponse(Call<Film> call, Response<Film> response) {
                tvTitle.setText(response.body().getTitle());
                tvOpening.setText(response.body().getOpening());
//                next = response.body().getNext();
//                if (next==null){
//                    b_next.setVisibility(View.INVISIBLE);
//                }else {
//                    b_next.setVisibility(View.VISIBLE);
//                }
//                previous = response.body().getPrevious();
//                if (previous==null){
//                    b_back.setVisibility(View.INVISIBLE);
//                }else {
//                    b_back.setVisibility(View.VISIBLE);
//                }
            }

            @Override
            public void onFailure(Call<Film> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
            }
        });
    }
}