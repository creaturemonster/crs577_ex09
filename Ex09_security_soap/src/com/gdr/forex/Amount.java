/**
 * 
 */
package com.gdr.forex;

import java.math.BigDecimal;

/**
 * 
 * Represents amount in a particular currency
 * 
 * @author v.lakshmanan
 *
 */
public class Amount {
    private BigDecimal internalNumber;
    private Currency money;

    public BigDecimal getValue() {
        return internalNumber;
    }
    public Currency getCurrency() {
        return money;
    }
    public void setValue(BigDecimal value) {
        this.internalNumber = value;
    }
    public void setCurrency(Currency currency) {
        this.money = currency;
    }

    public Amount(BigDecimal value, Currency currency) {
        this.internalNumber = value;
        this.money = currency;
    }


    /** Zero amount in US dollars */
    public Amount(){
        this(BigDecimal.ZERO, Currency.USD);
    }
    
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((internalNumber == null) ? 0 : internalNumber.hashCode());
        result = prime * result + ((money == null) ? 0 : money.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Amount other = (Amount) obj;
        if (internalNumber == null) {
            if (other.internalNumber != null)
                return false;
        } else if (!internalNumber.equals(other.internalNumber))
            return false;
        if (money != other.money)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return internalNumber + " " + money;
    }
}
















