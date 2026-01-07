import com.opencsv.exceptions.CsvValidationException;
import model.Order;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.opencsv.*;
import model.Priority;
import model.SalesChannel;

public class Utilities {

    public static List<Order> loadOrdersFromCSV(String path) {
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
        List<Order> orders = new ArrayList<>();

        try(FileReader filereader = new FileReader(path)) {
            CSVReader csvReader = new CSVReader(filereader);
            String[] nextRecord;
            csvReader.readNext();
            while ((nextRecord = csvReader.readNext()) != null) {
                String region = nextRecord[0];
                String country = nextRecord[1];
                String itemType = nextRecord[2];
                SalesChannel salesChannel = SalesChannel.valueOf(nextRecord[3].trim());
                Priority orderPriority = Priority.valueOf(nextRecord[4].trim());
                String validDate = nextRecord[5].trim().replace('.', '/');
                Date orderDate = DATE_FORMAT.parse(validDate);
                int unitsSold = Integer.parseInt(nextRecord[6].trim());
                BigDecimal totalProfit = new BigDecimal(nextRecord[7].trim());

                Order order = new Order(
                        region,
                        country,
                        itemType,
                        salesChannel,
                        orderPriority,
                        orderDate,
                        unitsSold,
                        totalProfit
                );

                orders.add(order);

            }
        } catch (IOException | CsvValidationException | ParseException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
