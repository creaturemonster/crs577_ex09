package com.gdr.forex;

import java.math.BigDecimal;

import javax.xml.ws.WebFault;

@WebFault(name = "valueOutOfRangeFaultInfo")
public class ValueOutOfRangeException extends Exception {
    private ValueOutOfRangeFaultDetail faultInfo;

    /** Convenience constructor */
    public ValueOutOfRangeException(String message, BigDecimal value) {
        this(message, new ValueOutOfRangeFaultDetail(value));
    }

    /** Constructor required by JAX-WS specification */
    public ValueOutOfRangeException(String message, ValueOutOfRangeFaultDetail faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /** Constructor required by JAX-WS specification */
    public ValueOutOfRangeException(String message, ValueOutOfRangeFaultDetail faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
    
    public ValueOutOfRangeFaultDetail getFaultInfo() {
        return faultInfo;
    }

    public static class ValueOutOfRangeFaultDetail {
        private BigDecimal value;

        public ValueOutOfRangeFaultDetail(BigDecimal value) {
            super();
            this.value = value;
        }

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }

    }
}
