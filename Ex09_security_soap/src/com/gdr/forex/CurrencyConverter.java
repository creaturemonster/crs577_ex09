package com.gdr.forex;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(name="CurrencyConverter", 
            targetNamespace="http://gdr.com/forex")
public interface CurrencyConverter {

    /**
     * Convert value from one currency to another.
     */
    public @WebResult(name="convertedAmount") Amount getConvertedAmount(
            @WebParam(name="fromAmount") Amount from, 
            @WebParam(name="toCurrency") Currency toCurrency) 
                    throws CurrencyLookupException, ValueOutOfRangeException;

}