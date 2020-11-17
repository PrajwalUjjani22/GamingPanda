package com.ujjani.gamingpanda;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements ProductData.OnItemClickListener{


    public static final String TAG = "TAG";
    private RecyclerView recycle_list,recycle_list2,recycle_list3,recycle_list4;
    private List<Item> dataList;
    private List<Item> dataList2;
    private List<Item> dataList3;
    private List<Item> dataList4;
    ProductData mProductDataAdapter;
    ProductData2 mProductDataAdapter2;
    ProductData3 mProductDataAdapter3;
    ProductData4 mProductDataAdapter4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataList = new ArrayList<>();
        dataList2 = new ArrayList<>();
        dataList3 = new ArrayList<>();
        dataList4 = new ArrayList<>();
        recycle_list = findViewById(R.id.recycler);
        recycle_list2 = findViewById(R.id.recycler2);
        recycle_list3 = findViewById(R.id.recycler3);
        recycle_list4 = findViewById(R.id.recycler4);

        uploadalbum2();
    }

    private void uploadalbum2() {

        OkHttpClient.Builder okClient = new OkHttpClient.Builder();
        okClient.connectTimeout(60000, TimeUnit.MILLISECONDS);
        okClient.writeTimeout(60000, TimeUnit.MILLISECONDS);
        okClient.readTimeout(60000, TimeUnit.MILLISECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okClient.interceptors().add(interceptor);

        okClient.interceptors().add(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
                response.newBuilder()
                        .header("Cache-Control", "only-if-cached")
                        .build();
                return response;
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gaming-panda.df.r.appspot.com/")
                .client(okClient.build())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();




        FileUploadService client2 = retrofit.create(FileUploadService.class);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"key\": \"abcd\"}");
        Call<ListResponse> call = client2.ursend(body);

        call.enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {
                Log.e(TAG, "onResponse:response==> " + response.isSuccessful() + "==>" + response.errorBody());

                if (response.isSuccessful()) {


                    for (int i = 0; i < response.body().getResponse().size(); i++ ) {
                        dataList = response.body().getResponse().get(0).getItems();
                        dataList2 = response.body().getResponse().get(1).getItems();
                        dataList3 = response.body().getResponse().get(2).getItems();
                        dataList4 = response.body().getResponse().get(3).getItems();

                        Log.e(TAG, "onResponse: ==>+" + new Gson().toJson(response.body()));
                        Log.e(TAG, "onResponseeee: ==>+" + new Gson().toJson(response.body().getResponse().get(i).getItems().get(i).getTitle()));
                        mProductDataAdapter = new ProductData(MainActivity.this, dataList);
                        recycle_list.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        recycle_list.setAdapter(mProductDataAdapter);
                        mProductDataAdapter.setOnItemClickListener(MainActivity.this);
                        mProductDataAdapter.notifyDataSetChanged();

                        mProductDataAdapter2 = new ProductData2(MainActivity.this, dataList2);
                        recycle_list2.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        recycle_list2.setAdapter(mProductDataAdapter2);
                        mProductDataAdapter2.notifyDataSetChanged();


                        mProductDataAdapter3 = new ProductData3(MainActivity.this, dataList3);
                        recycle_list3.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        recycle_list3.setAdapter(mProductDataAdapter3);
                        mProductDataAdapter3.notifyDataSetChanged();

                        mProductDataAdapter4 = new ProductData4(MainActivity.this, dataList4);
                        recycle_list4.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
                        recycle_list4.setAdapter(mProductDataAdapter4);
                        mProductDataAdapter4.notifyDataSetChanged();
                        ListResponse response1 = new ListResponse();
                        response1.setResponse(response.body().getResponse());

                        com.ujjani.gamingpanda.Response response2 = new com.ujjani.gamingpanda.Response();
                    }


                } else {

                }
            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {

                Log.e(TAG, "onFailure:==> " + t.toString());

            }
        });




    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onShowItemClick(int position) {

    }

    @Override
    public void onDeleteItemClick(int position) {

    }
}