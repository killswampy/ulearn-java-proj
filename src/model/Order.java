package model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private String region;
    private String country;
    private String type;
    private SalesChannel salesChannel;
    private Priority priority;
    private Date date;
    private int unitsSold;
    private BigDecimal totalProfit;

    public Order(String region, String country, String type, SalesChannel salesChannel,
                 Priority priority, Date date, int unitsSold, BigDecimal totalProfit) {
        this.region = region;
        this.country = country;
        this.type = type;
        this.salesChannel = salesChannel;
        this.priority = priority;
        this.date = date;
        this.unitsSold = unitsSold;
        this.totalProfit = totalProfit;
    }

    public String getRegion() {
        return region;
    }

    public String getCountry() {
        return country;
    }

    public String getType() {
        return type;
    }

    public SalesChannel getSalesChannel() {
        return salesChannel;
    }

    public Priority getPriority() {
        return priority;
    }

    public Date getDate() {
        return date;
    }

    public int getUnitsSold() {
        return unitsSold;
    }

    public BigDecimal getTotalProfit() {
        return totalProfit;
    }

    @Override
    public String
    toString() {
        return "Order{" +
                "region='" + region + '\'' +
                ", country='" + country + '\'' +
                ", type='" + type + '\'' +
                ", salesChannel=" + salesChannel +
                ", priority=" + priority +
                ", date=" + date +
                ", unitsSold=" + unitsSold +
                ", totalProfit=" + totalProfit +
                '}';
    }
}
