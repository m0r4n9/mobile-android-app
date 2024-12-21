package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.adapters.CryptoAdapter;
import ru.mirea.sevostyanov.data.model.CryptoCurrency;

public class CryptoFragment extends Fragment {
    private RecyclerView recyclerView;
    private CryptoAdapter adapter;
    private RequestQueue requestQueue;
    private static final String API_URL = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,AVAX,ETH,SOL,ADA,DOT,XRP,DOGE,MATIC,LINK&tsyms=USD,RUB";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crypto, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CryptoAdapter();
        recyclerView.setAdapter(adapter);

        requestQueue = Volley.newRequestQueue(requireContext());
        fetchCryptoData();

        return view;
    }

    private void fetchCryptoData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                response -> {
                    try {
                        List<CryptoCurrency> cryptoList = new ArrayList<>();
                        String[] symbols = {"BTC", "AVAX", "ETH", "SOL", "ADA", "DOT", "XRP", "DOGE", "MATIC", "LINK"};

                        for (String symbol : symbols) {
                            if (response.has(symbol)) {
                                JSONObject currencyData = response.getJSONObject(symbol);
                                double usdPrice = currencyData.getDouble("USD");
                                double rubPrice = currencyData.getDouble("RUB");
                                cryptoList.add(new CryptoCurrency(symbol, usdPrice, rubPrice));
                            }
                        }

                        adapter.setCryptoList(cryptoList);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Ошибка при обработке данных", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getContext(), "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show()
        );

        requestQueue.add(request);
    }
}