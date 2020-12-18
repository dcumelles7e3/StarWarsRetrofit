package cat.itb.starwarsretrofit.webservice;

import cat.itb.starwarsretrofit.model.Data;
import cat.itb.starwarsretrofit.model.Film;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WebServiceClient {
    @GET("people")
    Call<Data> getPeople();

    @GET()
    Call<Data> getUrl(@Url String url);

    @GET()
    Call<Film> getFilm(@Url String url);
}
