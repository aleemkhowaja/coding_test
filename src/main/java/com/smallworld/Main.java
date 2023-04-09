package com.smallworld;

import com.smallworld.dao.TransactionDAO;
import com.smallworld.model.Transaction;
import com.smallworld.service.TransactionDataFetcher;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        TransactionDAO transactionDAO = TransactionDAO.getInstance();
        TransactionDataFetcher transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        System.out.println("Sum of the amounts of all transactions =" + transactionDataFetcher.getTotalTransactionAmount());
        System.out.println("Amounts of all transactions sent by the specified client =" + transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby"));
        System.out.println("Highest transaction amount =" + transactionDataFetcher.getMaxTransactionAmount());
        System.out.println("Transactions indexed by beneficiary name =" + transactionDataFetcher.getTransactionsByBeneficiaryName());
        System.out.println("all solved issue messages = " + transactionDataFetcher.getAllSolvedIssueMessages());
        List<Transaction> top3TransactionsByAmount = transactionDataFetcher.getTop3TransactionsByAmount();
        System.out.println("highest amount sorted by amount descending =" + top3TransactionsByAmount);
        transactionDataFetcher.getTopSender();
    }
}
