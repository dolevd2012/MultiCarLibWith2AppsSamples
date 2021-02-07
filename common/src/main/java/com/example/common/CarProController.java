package com.example.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class CarProController {

    private CallBack_Car callBack_car;
    private String url = "https://pastebin.com/raw/WypPzJCt";
    Callback<Car> carCallBack = new Callback<Car>() {
        @Override
        public void onResponse(Call<Car> call, Response<Car> response) {
            if (response.isSuccessful()) {
                Car car = response.body();
                if (callBack_car != null) {
                    callBack_car.car(car);
                }
            } else {
                System.out.println(response.errorBody());
            }
        }

        @Override
        public void onFailure(Call<Car> call, Throwable t) {
            t.printStackTrace();
        }
    };

    public void fetchAllCars(CallBack_Car callBack_car) {
        this.callBack_car = callBack_car;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://a/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CarAPI carAPI = retrofit.create(CarAPI.class);

        Call<Car> call = carAPI.getData(url);
        call.enqueue(carCallBack);
    }
    public interface CallBack_Car {
        void car(Car car);
    }

    public interface CarAPI {
        @GET
        Call<Car> getData(@Url String url);
    }
}
