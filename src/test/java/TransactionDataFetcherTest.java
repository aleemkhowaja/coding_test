import com.smallworld.service.TransactionDataFetcher;
import com.smallworld.dao.TransactionDAO;
import com.smallworld.model.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

public class TransactionDataFetcherTest {

    private TransactionDataFetcher transactionDataFetcher;
    private TransactionDAO transactionDAO;

    @BeforeEach
    void init() {
    }

    @Test
    void testGetTotalTransactionAmount() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        double result = transactionDataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(648.2, result);
    }

    @Test
    void testGetTotalTransactionAmountWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        double result = transactionDataFetcher.getTotalTransactionAmount();
        Assertions.assertEquals(0.0, result);
    }

    @Test
    void testGetTotalTransactionAmountSentBy() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        double result = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        Assertions.assertEquals(580.4, result);
    }

    @Test
    void testGetTotalTransactionAmountSentByWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        double result = transactionDataFetcher.getTotalTransactionAmountSentBy("Tom Shelby");
        Assertions.assertEquals(0.0, result);
    }

    @Test
    void testGetMaxTransactionAmount() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        double result = transactionDataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(430.2, result);
    }

    @Test
    void testGetMaxTransactionAmountWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        double result = transactionDataFetcher.getMaxTransactionAmount();
        Assertions.assertEquals(0.0, result);
    }

    @Test
    void testCountUniqueClients() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        long result = transactionDataFetcher.countUniqueClients();
        Assertions.assertEquals(5, result);
    }

    @Test
    void testCountUniqueClientsWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        long result = transactionDataFetcher.countUniqueClients();
        Assertions.assertEquals(0, result);
    }

    @Test
    @DisplayName("check if any client name issue not resolved")
    void testHasOpenComplianceIssues() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        boolean result = transactionDataFetcher.hasOpenComplianceIssues("Tom Shelby");
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("check if any client name issue resolved")
    void testHasOpenComplianceIssuesResolved() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        boolean result = transactionDataFetcher.hasOpenComplianceIssues("Aunt Polly");
        Assertions.assertFalse(result);
    }

    @Test
    @DisplayName("check if any client name issue resolved with null transaction list")
    void testHasOpenComplianceIssuesResolvedWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        boolean result = transactionDataFetcher.hasOpenComplianceIssues("Aunt Polly");
        Assertions.assertFalse(result);
    }

    @Test
    void testGetTransactionsByBeneficiaryName() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        Map<String, Transaction> transactionMap = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertNotNull(transactionMap);
        Assertions.assertEquals(3, transactionMap.size());
    }

    @Test
    void testGetTransactionsByBeneficiaryNameIfTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        Map<String, Transaction> transactionMap = transactionDataFetcher.getTransactionsByBeneficiaryName();
        Assertions.assertNotNull(transactionMap);
        Assertions.assertTrue(transactionMap.isEmpty());
    }

    @Test
    void testGetUnsolvedIssueIds() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getData()).thenReturn(transactionList);
        Set<Integer> unsolvedIssueIds = transactionDataFetcher.getUnsolvedIssueIds();
        Assertions.assertNotNull(unsolvedIssueIds);
        Assertions.assertEquals(2, unsolvedIssueIds.size());
    }

    @Test
    void testGetUnsolvedIssueIdsWhenNullTransactionData() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getData()).thenReturn(null);
        Set<Integer> unsolvedIssueIds = transactionDataFetcher.getUnsolvedIssueIds();
        Assertions.assertNotNull(unsolvedIssueIds);
        Assertions.assertTrue(unsolvedIssueIds.isEmpty());
    }

    @Test
    void testGetAllSolvedIssueMessages() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getData()).thenReturn(transactionList);
        List<String> allSolvedIssueMessages = transactionDataFetcher.getAllSolvedIssueMessages();
        Assertions.assertNotNull(allSolvedIssueMessages);
        Assertions.assertEquals(2, allSolvedIssueMessages.size());
    }

    @Test
    void testGetAllSolvedIssueMessagesWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getData()).thenReturn(null);
        List<String> allSolvedIssueMessages = transactionDataFetcher.getAllSolvedIssueMessages();
        Assertions.assertNotNull(allSolvedIssueMessages);
        Assertions.assertTrue(allSolvedIssueMessages.isEmpty());
    }

    @Test
    void testGetTop3TransactionsByAmount() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        List<Transaction> transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        Assertions.assertNotNull(transactions);
        Assertions.assertEquals(3, transactions.size());
        Assertions.assertEquals(430.2, transactions.get(0).getAmount());
    }

    @Test
    void testGetTop3TransactionsByAmountWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        List<Transaction> transactions = transactionDataFetcher.getTop3TransactionsByAmount();
        Assertions.assertNotNull(transactions);
        Assertions.assertTrue(transactions.isEmpty());
    }

    @Test
    void testGetTopSender() throws IOException {
        List<Transaction> transactionList = TestUtil.getTransactionUniqueData();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(transactionList);
        Map<String, Double> topSenderNameAndTotalAmount = transactionDataFetcher.getTopSender();
        Assertions.assertNotNull(topSenderNameAndTotalAmount);
        Assertions.assertEquals(1, topSenderNameAndTotalAmount.size());
        Assertions.assertEquals("Tom Shelby", topSenderNameAndTotalAmount.keySet().stream().findAny().get());
        Assertions.assertEquals(580.4, topSenderNameAndTotalAmount.get(topSenderNameAndTotalAmount.keySet().stream().findAny().get()));
    }

    @Test
    void testGetTopSenderWhenTransactionListNull() throws IOException {
        transactionDAO = Mockito.mock(TransactionDAO.class);
        transactionDataFetcher = new TransactionDataFetcher(transactionDAO);
        when(transactionDAO.getUniqueTransaction()).thenReturn(null);
        Map<String, Double> topSenderNameAndTotalAmount = transactionDataFetcher.getTopSender();
        Assertions.assertNotNull(topSenderNameAndTotalAmount);
        Assertions.assertTrue(topSenderNameAndTotalAmount.isEmpty());
    }
}