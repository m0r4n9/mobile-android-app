package ru.mirea.sevostyanov.data.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import ru.mirea.sevostyanov.data.model.CryptoCurrency;

public interface CryptoRepository {
    LiveData<List<CryptoCurrency>> getCryptoCurrencies();
}
