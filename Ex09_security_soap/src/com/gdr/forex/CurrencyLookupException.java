package com.gdr.forex;

import javax.xml.ws.WebFault;

@WebFault(name = "conversionFaultInfo")
public class CurrencyLookupException extends Exception {
    private CurrencyLookupFaultDetail faultInfo;

    /** Convenience constructor */
    public CurrencyLookupException(String message, Currency currency) {
        this(message, new CurrencyLookupFaultDetail(currency));
    }

    /** Constructor required by JAX-WS specification */
    public CurrencyLookupException(String message, CurrencyLookupFaultDetail faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /** Constructor required by JAX-WS specification */
    public CurrencyLookupException(String message, CurrencyLookupFaultDetail faultInfo, 
                                   Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
    
    public CurrencyLookupFaultDetail getFaultInfo() {
        return faultInfo;
    }

    public static class CurrencyLookupFaultDetail {
        private Currency currency;

        public CurrencyLookupFaultDetail(Currency currency) {
            super();
            this.currency = currency;
        }

        public Currency getCurrency() {
            return currency;
        }

        public void setCurrency(Currency currency) {
            this.currency = currency;
        }

    }
}
