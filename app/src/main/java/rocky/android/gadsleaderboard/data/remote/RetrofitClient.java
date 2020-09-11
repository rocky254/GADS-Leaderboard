package rocky.android.gadsleaderboard.data.remote;

import retrofit2.Retrofit;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .build();
        }
        return retrofit;
    }
}