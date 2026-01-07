import model.Order;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var path = "data.csv";
        List<Order> orderList = Utilities.loadOrdersFromCSV(path);
        DataBase.Connection();
        DataBase.CreateDB();
//        DataBase.WriteDB(orderList);
        var chartData = DataBase.getUnitsSoldByRegion();
        Visualisation.barChartByMap(
            chartData,
            "Продажи по регионам",
            "Регион",
            "Кол-во продаж"
        );
        DataBase.EuraziaIncomeLider();
        DataBase.AfricaIncomeBetwin420and440();
    }
}