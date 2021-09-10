/**
 * 
 */
package com.gdr.brokerage;

import java.math.BigDecimal;

import javax.xml.ws.BindingProvider;

import com.gdr.brokerage.generated.Amount;
import com.gdr.brokerage.generated.Currency;
import com.gdr.brokerage.generated.CurrencyConverter;
import com.gdr.brokerage.generated.CurrencyConverterService;
import com.gdr.brokerage.generated.CurrencyLookupException;
import com.gdr.brokerage.generated.CurrencyLookupFaultDetail;
import com.gdr.brokerage.generated.ValueOutOfRangeException;
import com.gdr.brokerage.generated.ValueOutOfRangeFaultDetail;
import com.rf.portal.common.LoggingSOAPHandler;

/**
 * Client application for Foreign Exchange service with client 
 * authetication and authorization.
 */
public class ForexClient {

    public static void main(String[] args)  {
        try {
            // TODO: Examine the steps to create the service proxy as usual
            //       (no changes required)
            CurrencyConverterService currencyConverterService = 
                    new CurrencyConverterService();
            CurrencyConverter converterProxy = 
                    currencyConverterService.getCurrencyConverterSoapPort();
            
            // TODO: set the username to "mall" and the password to "teens"
            // HINT: see slide 9-26
            
            
            // Add custom JAX-WS handler to display requests to console
            LoggingSOAPHandler.addToHandlerChain(converterProxy);
            
            Amount from = new Amount();
            from.setValue(new BigDecimal(100));
            from.setCurrency(Currency.USD);
            
            // TODO: Note the usual call to a proxy method to send the SOAP message
            //       (no changes required)
            Amount result = converterProxy.getConvertedAmount(from, Currency.EUR);
            
            System.out.println(toString(from) + " was converted to " + toString(result));
        } 
        catch (CurrencyLookupException e) {
            CurrencyLookupFaultDetail faultInfo = e.getFaultInfo();
            System.err.println(e + ": " + faultInfo.getCurrency());
        } 
        catch (ValueOutOfRangeException e) {
            ValueOutOfRangeFaultDetail faultInfo = e.getFaultInfo();
            System.err.println(e + ": " + faultInfo.getValue());
        }
    }

    private static String toString(Amount from) {
        return from.getValue().setScale(2) + " " + from.getCurrency();
    }
}
