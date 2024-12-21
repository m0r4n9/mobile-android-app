package ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import ru.mirea.maiorovsevostyanov.rumireamaiorovsevostyanovnewsapp.R;
import ru.mirea.sevostyanov.data.model.CryptoCurrency;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.CryptoViewHolder> {
    private List<CryptoCurrency> cryptoList;

    public CryptoAdapter() {
        this.cryptoList = new ArrayList<>();
    }

    @NonNull
    @Override
    public CryptoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.crypto_item, parent, false);
        return new CryptoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoViewHolder holder, int position) {
        CryptoCurrency crypto = cryptoList.get(position);
        holder.nameText.setText(crypto.getSymbol());
        holder.priceUsdText.setText(String.format("$%.2f", crypto.getUsdPrice()));
        holder.priceRubText.setText(String.format("₽%.2f", crypto.getRubPrice()));

        String iconUrl;
        switch (crypto.getSymbol()) {
            case "BTC":
                iconUrl = "https://cryptologos.cc/logos/bitcoin-btc-logo.png";
                break;
            case "ETH":
                iconUrl = "https://cryptologos.cc/logos/ethereum-eth-logo.png";
                break;
            case "AVAX":
                iconUrl = "https://cryptologos.cc/logos/avalanche-avax-logo.png";
                break;
            case "SOL":
                iconUrl = "https://cryptologos.cc/logos/solana-sol-logo.png";
                break;
            case "ADA":
                iconUrl = "https://cryptologos.cc/logos/cardano-ada-logo.png";
                break;
            case "DOT":
                iconUrl = "https://cryptologos.cc/logos/polkadot-new-dot-logo.png";
                break;
            case "XRP":
                iconUrl = "https://cryptologos.cc/logos/xrp-xrp-logo.png";
                break;
            case "DOGE":
                iconUrl = "https://cryptologos.cc/logos/dogecoin-doge-logo.png";
                break;
            case "MATIC":
                iconUrl = "https://cryptologos.cc/logos/polygon-matic-logo.png";
                break;
            case "LINK":
                iconUrl = "https://cryptologos.cc/logos/chainlink-link-logo.png";
                break;
            default:
                iconUrl = "";
                break;
        }

        Picasso.get()
                .load(iconUrl)
                .placeholder(R.drawable.brand_binance)
                .error(R.drawable.brand_binance)
                .into(holder.iconView);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public void setCryptoList(List<CryptoCurrency> newCryptoList) {
        this.cryptoList = newCryptoList;
        notifyDataSetChanged();
    }

    static class CryptoViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView priceUsdText;
        TextView priceRubText;
        ImageView iconView;

        CryptoViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.cryptoName);
            priceUsdText = itemView.findViewById(R.id.priceUsd);
            priceRubText = itemView.findViewById(R.id.priceRub);
            iconView = itemView.findViewById(R.id.cryptoIcon);
        }
    }
}