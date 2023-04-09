package com.smallworld.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smallworld.model.Transaction;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionDAO {
    List<Transaction> transactionList = new ArrayList<>();
    List<Transaction> uniqueTransactionList = new ArrayList<>();
    private static TransactionDAO transactionDAO = null;

    private TransactionDAO() {}

    public static TransactionDAO getInstance() {
        if (null == transactionDAO) {
            transactionDAO = new TransactionDAO();
        }
        return transactionDAO;
    }

    public List<Transaction> getData() throws IOException {
        if (transactionList.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(this.getClass().getClassLoader()
                    .getResource("transactions.json").getFile());
            transactionList = objectMapper.readValue(file, new TypeReference<List<Transaction>>() {
            });
        }
        return transactionList;
    }

    public List<Transaction> getUniqueTransaction() throws IOException {
        if (uniqueTransactionList.isEmpty()) {
            ObjectMapper objectMapper = new ObjectMapper();
            File file = new File(this.getClass().getClassLoader()
                    .getResource("transactions.json").getFile());
            uniqueTransactionList = objectMapper.readValue(file, new TypeReference<List<Transaction>>() {
            });
            uniqueTransactionList = uniqueTransactionList.stream()
                    .filter(distinctByKey(Transaction::getMtn)).collect(Collectors.toList());
        }
        return uniqueTransactionList;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
