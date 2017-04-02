
package webservicepckg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sqlException complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="sqlException">
 *   &lt;complexContent>
 *     &lt;extension base="{http://webservicepckg/}exception">
 *       &lt;sequence>
 *         &lt;element name="nextException" type="{http://webservicepckg/}sqlException" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sqlException", propOrder = {
    "nextException"
})
public class SqlException1
    extends Exception
{

    protected SqlException1 nextException;

    /**
     * Gets the value of the nextException property.
     * 
     * @return
     *     possible object is
     *     {@link SqlException1 }
     *     
     */
    public SqlException1 getNextException() {
        return nextException;
    }

    /**
     * Sets the value of the nextException property.
     * 
     * @param value
     *     allowed object is
     *     {@link SqlException1 }
     *     
     */
    public void setNextException(SqlException1 value) {
        this.nextException = value;
    }

}
