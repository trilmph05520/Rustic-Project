package com.vn.model;

import java.math.BigDecimal;

public class ChartDashboardBillOrder {

    private String date;
    private BigDecimal total;

    public ChartDashboardBillOrder() {
    }

    public ChartDashboardBillOrder(String date, BigDecimal total) {
        this.date = date;
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
