import com.smallworld.model.Transaction;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TestUtil {

    public static List<Transaction> getTransactionData() {
        Transaction t1 = new Transaction(663458l, 430.2, "Tom Shelby", 22, "Alfie Solomons",
                33, 1, false, "Looks like money laundering");
        Transaction t2 = new Transaction(1284564l, 150.2, "Tom Shelby", 22, "Arthur Shelby",
                60, 2, true, "Never gonna give you up");
        Transaction t3 = new Transaction(1284564l, 150.2, "Tom Shelby", 22, "Arthur Shelby",
                60, 3, false, "Looks like money laundering");
        Transaction t4 = new Transaction(96132456l, 67.8, "Aunt Polly", 34, "Aberama Gold",
                58, null, true, null);
        return Arrays.asList(t1, t2, t3, t4);
    }

    public static List<Transaction> getTransactionUniqueData() {
        Transaction t1 = new Transaction(663458l, 430.2, "Tom Shelby", 22, "Alfie Solomons",
                33, 1, false, "Looks like money laundering");
        Transaction t2 = new Transaction(1284564l, 150.2, "Tom Shelby", 22, "Arthur Shelby",
                60, 2, true, "Never gonna give you up");
        Transaction t3 = new Transaction(1284564l, 150.2, "Tom Shelby", 22, "Arthur Shelby",
                60, 3, false, "Looks like money laundering");
        Transaction t4 = new Transaction(96132456l, 67.8, "Aunt Polly", 34, "Aberama Gold",
                58, null, true, null);
        List<Transaction> transactionList = Arrays.asList( t4,t2, t3,t1);

        return transactionList.stream()
                .filter(distinctByKey(Transaction::getMtn)).collect(Collectors.toList());
    }


    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
