package tqs.stock_portfolio;

import java.util.ArrayList;
import java.util.List;

public class StocksPortfolio {
    private final IStockmarketService stockmarket;
    private List<Stock> stocks;

    public StocksPortfolio(IStockmarketService stockmarket) {
        this.stockmarket = stockmarket;
        this.stocks = new ArrayList<>();
    }

    public void addStock(Stock stock) {
        stocks.add(stock);
    }

    public double totalValue() {
        double totalValue = 0;
        for (Stock stock : stocks) {
            double price = stockmarket.lookUpPrice(stock.getLabel());
            totalValue += price * stock.getQuantity();
        }
        return totalValue;
    }
}
