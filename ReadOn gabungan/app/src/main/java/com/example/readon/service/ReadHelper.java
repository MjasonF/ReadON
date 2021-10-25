package com.example.readon.service;

import com.example.readon.TextAdapter;
import com.example.readon.model.Reads;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadHelper {
    public static void fetchData(TextAdapter adapter, String id) {
        APIService service = APIClient.client();
        Call<List<Reads>> call = service.getreads();

        call.enqueue(new Callback<List<Reads>>() {
            @Override
            public void onResponse(Call<List<Reads>> call, Response<List<Reads>> response) {

                if (!response.isSuccessful()){
                    System.out.println("Code: " + response.code());
                    return;
                }

                List<Reads> reads = response.body();
                List<Reads> newReads = new ArrayList<Reads>();

                for(int i = 0; i < reads.size(); i++) {

                    if (reads.get(i).getType_Id().equals(id)) {
                        newReads.add(reads.get(i));
                    }
                }


                adapter.addTitle(newReads);

            }

            @Override
            public void onFailure(Call<List<Reads>> call, Throwable t) {
                System.out.println(t.getMessage());

            }
        });
    }
}
