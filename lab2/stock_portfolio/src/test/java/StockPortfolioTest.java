import static org.mockito.Mockito.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.BeforeAll;
import tqs.stock_portfolio.Stock;
import tqs.stock_portfolio.StocksPortfolio;
import tqs.stock_portfolio.IStockmarketService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {

    private IStockmarketService stockmarket;

    @BeforeAll
    void setUp() {
        stockmarket = mock(IStockmarketService.class);

        when(stockmarket.lookUpPrice("AAPL")).thenReturn(150.0);
        when(stockmarket.lookUpPrice("NVDA")).thenReturn(1000.0);
    }

    @Test
    void testTotalValue() {
        StocksPortfolio portfolio = new StocksPortfolio(stockmarket);

        Stock appleStock = new Stock("AAPL", 10);
        Stock nvidiaStock = new Stock("NVDA", 5);

        portfolio.addStock(appleStock);
        portfolio.addStock(nvidiaStock);

        double totalValue = portfolio.totalValue();

        assertThat(totalValue, is(6500.0));
    }
}
