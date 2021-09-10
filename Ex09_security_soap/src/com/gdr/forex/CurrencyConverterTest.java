package com.gdr.forex;

import java.math.BigDecimal;

import org.junit.*;
import static org.junit.Assert.*;

public class CurrencyConverterTest {

    private CurrencyConverterImpl converter;
    
    @Before
    public void setUp(){
        converter = new CurrencyConverterImpl();
    }
    
    @Test
    public void testUSDtoUSD() throws Exception {
        BigDecimal fromValue = new BigDecimal("10.5");
        
        Amount result = converter.getConvertedAmount(new Amount(fromValue,Currency.USD), Currency.USD);
        
        Currency expectedCurrency = Currency.USD;
        BigDecimal expectedAmount = new BigDecimal("10.5");
        assertEquals(expectedCurrency, result.getCurrency());
        assertEquals(expectedAmount, result.getValue());
    }

    @Test
    public void testUSDtoEuro() throws Exception {
        converter.setUsdConversionRate(Currency.EUR, new BigDecimal("2.0"));
        Amount fromAmount = new Amount(new BigDecimal("200"), Currency.USD);
        
        Amount result = converter.getConvertedAmount(fromAmount, Currency.EUR);
        
        assertEquals(Currency.EUR, result.getCurrency());
        assertEquals(new BigDecimal("100.0"), result.getValue());
    }
    
    @Test
    public void testEURtoUSD() throws Exception {
        converter.setUsdConversionRate(Currency.EUR, new BigDecimal("1.5"));
        Amount fromAmount = new Amount(new BigDecimal("100"), Currency.EUR);
        Amount expectedResult = new Amount(new BigDecimal("150.0"), Currency.USD);
        
        Amount actualResult = converter.getConvertedAmount(fromAmount, Currency.USD);
        
        assertEquals(expectedResult, actualResult);
    }
    
    @Test(expected=CurrencyLookupException.class)
    public void testNegativeAmount() throws Exception {
        Amount fromAmount = new Amount(new BigDecimal("-1"), Currency.USD);
        converter.getConvertedAmount(fromAmount, Currency.JPY);
    }
}
