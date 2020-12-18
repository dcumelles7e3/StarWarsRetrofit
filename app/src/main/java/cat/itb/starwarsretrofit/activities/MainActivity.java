package cat.itb.starwarsretrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import cat.itb.starwarsretrofit.R;
import cat.itb.starwarsretrofit.adapter.PeopleAdapter;
import cat.itb.starwarsretrofit.model.Data;
import cat.itb.starwarsretrofit.model.People;
import cat.itb.starwarsretrofit.webservice.WebServiceClient;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private PeopleAdapter adapter;
    private List<People> starships;

    private Button b_back;
    private Button b_next;
    private ImageButton b_search;
    private EditText et_search;


    private Retrofit retrofit;
    private HttpLoggingInterceptor loggingInterceptor;
    private OkHttpClient.Builder httpClientBuilder;
    private String next;
    private String previous;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler_view);
        starships = new ArrayList<People>();
        adapter = new PeopleAdapter(starships);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(layout);

        lanzarPeticion(null);

        et_search = findViewById(R.id.et_search);
        b_search = findViewById(R.id.b_search);
        b_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = et_search.getText().toString();
                lanzarPeticion("https://swapi.dev/api/people/?search="+search);
            }
        });
        b_next = findViewById(R.id.b_next);
        b_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPeticion(next);
            }
        });
        b_back = findViewById(R.id.b_back);
        b_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lanzarPeticion(previous);
            }
        });
    }

    private void lanzarPeticion(String url) {
        loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder = new OkHttpClient.Builder().addInterceptor(loggingInterceptor);

        retrofit = new Retrofit.Builder().baseUrl("https://swapi.dev/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .build();

        WebServiceClient client = retrofit.create(WebServiceClient.class);
        Call<Data> call;
        if (url == null) {
            call = client.getPeople();
        } else {
            call = client.getUrl(url);
        }
        call.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                adapter.setData(response.body().getResults());
                next = response.body().getNext();
                if (next==null){
                    b_next.setVisibility(View.INVISIBLE);
                }else {
                    b_next.setVisibility(View.VISIBLE);
                }
                previous = response.body().getPrevious();
                if (previous==null){
                    b_back.setVisibility(View.INVISIBLE);
                }else {
                    b_back.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("TAG1", "ERROR: " + t.getMessage());
            }
        });
    }
}