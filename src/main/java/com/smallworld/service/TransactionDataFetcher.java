package com.smallworld.service;

import com.smallworld.dao.TransactionDAO;
import com.smallworld.model.Transaction;
import com.smallworld.util.TransactionUtility;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Slf4j
public class TransactionDataFetcher {

    private TransactionDAO transactionDAO;

    public TransactionDataFetcher(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            double sumValue = transactionList.parallelStream().mapToDouble(transaction -> transaction.getAmount()).sum();
            return TransactionUtility.formatDoubleValue(sumValue);
        } catch (Exception e) {
            log.error("Error while getting total transaction amount", e.getCause());
        }
        return 0.0;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName) {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            return transactionList.parallelStream().filter(transaction -> transaction.getSenderFullName().equals(senderFullName))
                    .mapToDouble(transaction -> transaction.getAmount()).sum();
        } catch (Exception e) {
            log.error("Error while getting transaction amount sent by, {}", senderFullName, e.getCause());
        }
        return 0.0;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            return transactionList.parallelStream().mapToDouble(transaction -> transaction.getAmount()).max().getAsDouble();
        } catch (Exception e) {
            log.error("Error while getting max transaction amount", e.getCause());
        }
        return 0.0;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            //get
            Set<String> uniqueClientNames = transactionList.parallelStream().map(transaction -> transaction.getSenderFullName()).collect(Collectors.toSet());
            uniqueClientNames.addAll(transactionList.stream().map(transaction -> transaction.getBeneficiaryFullName()).collect(Collectors.toSet()));
            return uniqueClientNames.size();
        } catch (Exception e) {
            log.error("Error while counting unique clients", e.getCause());
        }
        return 0;
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public boolean hasOpenComplianceIssues(String clientFullName) {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            return transactionList.parallelStream().
                    anyMatch(transaction -> (transaction.getSenderFullName().equals(clientFullName) ||
                            transaction.getBeneficiaryFullName().equals(clientFullName)) && !transaction.isIssueSolved());
        } catch (Exception e) {
            log.error("Error while getting open compliance issues for client={}", clientFullName, e.getCause());
        }
        return false;
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public Map<String, Transaction> getTransactionsByBeneficiaryName() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            return transactionList.parallelStream().collect(Collectors.toMap(Transaction::getBeneficiaryFullName, Function.identity(), (t1, t2) -> t1));
        } catch (Exception e) {
            log.error("Error while getting transactions by beneficiary name}", e.getCause());
        }
        return Collections.EMPTY_MAP;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public Set<Integer> getUnsolvedIssueIds() {
        try {
            List<Transaction> transactionList = transactionDAO.getData();
            return transactionList.parallelStream().filter(transaction -> !transaction.isIssueSolved()).map(transaction -> transaction.getIssueId())
                    .collect(Collectors.toSet());
        } catch (Exception e) {
            log.error("Error while getting unresolved issue Ids", e.getCause());
        }
        return Collections.EMPTY_SET;
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages() {
        try {
            List<Transaction> transactionList = transactionDAO.getData();
            return transactionList.parallelStream().filter(transaction -> transaction.isIssueSolved()).map(transaction -> transaction.getIssueMessage())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while getting all solved issue messages", e.getCause());
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Transaction> getTop3TransactionsByAmount() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            return transactionList.parallelStream().sorted(Comparator.comparingDouble(Transaction::getAmount).reversed()).limit(3).collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while getting top 3 transaction by amount", e.getCause());
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Map<String, Double> getTopSender() {
        try {
            List<Transaction> transactionList = transactionDAO.getUniqueTransaction();
            //get total amount groupby sender name
            Map<String, Double> topSenderNameAndTotalAmount = transactionList.parallelStream()
                    .collect(Collectors.groupingBy(Transaction::getSenderFullName,
                            Collectors.summingDouble(Transaction::getAmount)));

            //get sendername and amount which is max among all entries in map
            Map.Entry<String, Double> maxEntry = Collections.max(topSenderNameAndTotalAmount.entrySet(),
                    Map.Entry.comparingByValue());

            //re-intialize map and add max amount and its sender
            topSenderNameAndTotalAmount = new HashMap<>();
            topSenderNameAndTotalAmount.put(maxEntry.getKey(), maxEntry.getValue());
            return topSenderNameAndTotalAmount;
        } catch (Exception e) {
            log.error("Error while getting top sender which most max amount send among all transactions", e.getCause());
        }
        return Collections.EMPTY_MAP;
    }

}
