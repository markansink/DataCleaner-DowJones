//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.31 at 10:37:07 AM CEST 
//


package org.datacleaner.components.dowjones.xml;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DateValue" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *       &lt;attribute name="DateType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "dateValue"
})
@XmlRootElement(name = "Date")
public class Date {

    @XmlElement(name = "DateValue", required = true)
    protected List<DateValue> dateValue;
    @XmlAttribute(name = "DateType", required = true)
    protected String dateType;

    /**
     * Gets the value of the dateValue property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dateValue property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDateValue().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link DateValue }
     */
    public List<DateValue> getDateValue() {
        if (dateValue == null) {
            dateValue = new ArrayList<DateValue>();
        }
        return this.dateValue;
    }

    /**
     * Gets the value of the dateType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDateType() {
        return dateType;
    }

    /**
     * Sets the value of the dateType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDateType(String value) {
        this.dateType = value;
    }

}
