import model.Order;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class DataBase {
    private static Connection conn;
	private static Statement statmt;
	private static ResultSet resSet;

    public static void Connection(){
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:Orders.s3db");
//            System.out.println("База Подключена!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void CreateTable(){
        try {
            statmt = conn.createStatement();
            statmt.execute(
                    "CREATE TABLE IF NOT EXISTS Orders (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "region TEXT NOT NULL, " +
                        "country TEXT NOT NULL, " +
                        "type TEXT NOT NULL, " +
                        "sales_channel TEXT NOT NULL, " +
                        "priority TEXT NOT NULL, " +
                        "order_date TEXT NOT NULL, " +
                        "units_sold INTEGER NOT NULL, " +
                        "total_profit DECIMAL(15, 2) NOT NULL" +
                        ");"
            );
//            System.out.println("Таблица создана или уже существует.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void WriteDB(List<Order> orders){
        for (var order : orders){
            try {
                statmt.execute("INSERT INTO Orders (" +
                        "region, country, type, sales_channel, priority, order_date, units_sold, total_profit" +
                        ") VALUES (" +
                        "'" + escape(order.getRegion()) + "', " +
                        "'" + escape(order.getCountry()) + "', " +
                        "'" + escape(order.getType()) + "', " +
                        "'" + order.getSalesChannel().name() + "', " +
                        "'" + order.getPriority().name() + "', " +
                        "'" + formatDate(order.getDate()) + "', " +
                        order.getUnitsSold() + ", " +
                        order.getTotalProfit() +
                        ");");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private static String escape(String value) {
        return value.replace("'", "");
    }
    private static String formatDate(java.util.Date date) {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static void EuraziaIncomeLider(){
        try {
            resSet = statmt.executeQuery(
                "SELECT country, SUM(total_profit) AS total_income " +
                "FROM Orders " +
                "WHERE region IN ('Europe', 'Asia') " +
                "GROUP BY country " +
                "ORDER BY total_income DESC " +
                "LIMIT 1;"
            );
            if (resSet.next()) {
                String country = resSet.getString("country");
                double totalIncome = resSet.getDouble("total_income");

                System.out.println(
                        "Страна с самым высоким общим доходом в евразии: " +
                        country + ", доход = " + totalIncome
                );
            } else {
                System.out.println("Данные не найдены");
            }

        resSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void AfricaIncomeBetwin420and440() {
        try {
            resSet = statmt.executeQuery(
                "SELECT country, SUM(total_profit) AS total_income " +
                "FROM Orders " +
                "WHERE region IN ('Middle East and North Africa', 'Sub-Saharan Africa') " +
                "GROUP BY country " +
                "HAVING SUM(total_profit) BETWEEN 420000 AND 440000 " +
                "ORDER BY total_income DESC " +
                "LIMIT 1;"
            );

            if (resSet.next()) {
                String country = resSet.getString("country");
                double totalIncome = resSet.getDouble("total_income");

                System.out.println(
                    "Страна с доходом 420–440 тыс. (MENA + Sub-Saharan Africa): " +
                    country + ", доход = " + totalIncome
                );
            } else {
                System.out.println("Подходящих стран не найдено");
            }

            resSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Integer> getUnitsSoldByRegion() {
        Map<String, Integer> unitsMap = new HashMap<>();

        try {
            resSet = statmt.executeQuery(
                        "SELECT region, SUM(units_sold) AS total_units_sold " +
                            "FROM Orders " +
                            "GROUP BY region;"
            );

            while (resSet.next()) {
                String region = resSet.getString("region");
                int totalUnits = resSet.getInt("total_units_sold");
                unitsMap.put(region, totalUnits);
            }

            resSet.close();

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения данных для графика", e);
        }
        return unitsMap;
    }
}
