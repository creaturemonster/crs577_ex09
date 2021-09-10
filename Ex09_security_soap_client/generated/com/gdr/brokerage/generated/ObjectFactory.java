
package com.gdr.brokerage.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.gdr.brokerage.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _ConversionFaultInfo_QNAME = new QName("http://gdr.com/forex", "conversionFaultInfo");
    private final static QName _GetConvertedAmount_QNAME = new QName("http://gdr.com/forex", "getConvertedAmount");
    private final static QName _GetConvertedAmountResponse_QNAME = new QName("http://gdr.com/forex", "getConvertedAmountResponse");
    private final static QName _ValueOutOfRangeFaultInfo_QNAME = new QName("http://gdr.com/forex", "valueOutOfRangeFaultInfo");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.gdr.brokerage.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetConvertedAmountResponse }
     * 
     */
    public GetConvertedAmountResponse createGetConvertedAmountResponse() {
        return new GetConvertedAmountResponse();
    }

    /**
     * Create an instance of {@link ValueOutOfRangeFaultDetail }
     * 
     */
    public ValueOutOfRangeFaultDetail createValueOutOfRangeFaultDetail() {
        return new ValueOutOfRangeFaultDetail();
    }

    /**
     * Create an instance of {@link CurrencyLookupFaultDetail }
     * 
     */
    public CurrencyLookupFaultDetail createCurrencyLookupFaultDetail() {
        return new CurrencyLookupFaultDetail();
    }

    /**
     * Create an instance of {@link GetConvertedAmount }
     * 
     */
    public GetConvertedAmount createGetConvertedAmount() {
        return new GetConvertedAmount();
    }

    /**
     * Create an instance of {@link Amount }
     * 
     */
    public Amount createAmount() {
        return new Amount();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CurrencyLookupFaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gdr.com/forex", name = "conversionFaultInfo")
    public JAXBElement<CurrencyLookupFaultDetail> createConversionFaultInfo(CurrencyLookupFaultDetail value) {
        return new JAXBElement<CurrencyLookupFaultDetail>(_ConversionFaultInfo_QNAME, CurrencyLookupFaultDetail.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConvertedAmount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gdr.com/forex", name = "getConvertedAmount")
    public JAXBElement<GetConvertedAmount> createGetConvertedAmount(GetConvertedAmount value) {
        return new JAXBElement<GetConvertedAmount>(_GetConvertedAmount_QNAME, GetConvertedAmount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetConvertedAmountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gdr.com/forex", name = "getConvertedAmountResponse")
    public JAXBElement<GetConvertedAmountResponse> createGetConvertedAmountResponse(GetConvertedAmountResponse value) {
        return new JAXBElement<GetConvertedAmountResponse>(_GetConvertedAmountResponse_QNAME, GetConvertedAmountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ValueOutOfRangeFaultDetail }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://gdr.com/forex", name = "valueOutOfRangeFaultInfo")
    public JAXBElement<ValueOutOfRangeFaultDetail> createValueOutOfRangeFaultInfo(ValueOutOfRangeFaultDetail value) {
        return new JAXBElement<ValueOutOfRangeFaultDetail>(_ValueOutOfRangeFaultInfo_QNAME, ValueOutOfRangeFaultDetail.class, null, value);
    }

}
