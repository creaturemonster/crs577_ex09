/**
 * 
 */
package com.rf.inventory.backend;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Data type corresponding to XML exchanged with the server. The XML looks like this:
 * <item productId="3212" quantity="4" />
 * 
 * @author v.lakshmanan
 *
 */

@XmlRootElement
public class Item {
    private int productId;
    private int quantity;

    public Item(){
        this(-1, -1);
    }
    
    public Item(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    @XmlAttribute
    public int getProductId() {
        return productId;
    }
    
    public void setProductId(int productId) {
        this.productId = productId;
    }
    
    @XmlAttribute
    public int getQuantity() {
        return quantity;
    }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
