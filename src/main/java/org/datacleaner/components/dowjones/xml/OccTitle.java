//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.05.31 at 10:37:07 AM CEST 
//


package org.datacleaner.components.dowjones.xml;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="SinceDay" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SinceMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SinceYear" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ToDay" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ToMonth" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ToYear" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="OccCat" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "OccTitle")
public class OccTitle {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "SinceDay")
    protected String sinceDay;
    @XmlAttribute(name = "SinceMonth")
    protected String sinceMonth;
    @XmlAttribute(name = "SinceYear")
    protected String sinceYear;
    @XmlAttribute(name = "ToDay")
    protected String toDay;
    @XmlAttribute(name = "ToMonth")
    protected String toMonth;
    @XmlAttribute(name = "ToYear")
    protected String toYear;
    @XmlAttribute(name = "OccCat")
    protected String occCat;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the sinceDay property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSinceDay() {
        return sinceDay;
    }

    /**
     * Sets the value of the sinceDay property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSinceDay(String value) {
        this.sinceDay = value;
    }

    /**
     * Gets the value of the sinceMonth property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSinceMonth() {
        return sinceMonth;
    }

    /**
     * Sets the value of the sinceMonth property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSinceMonth(String value) {
        this.sinceMonth = value;
    }

    /**
     * Gets the value of the sinceYear property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSinceYear() {
        return sinceYear;
    }

    /**
     * Sets the value of the sinceYear property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSinceYear(String value) {
        this.sinceYear = value;
    }

    /**
     * Gets the value of the toDay property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getToDay() {
        return toDay;
    }

    /**
     * Sets the value of the toDay property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToDay(String value) {
        this.toDay = value;
    }

    /**
     * Gets the value of the toMonth property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getToMonth() {
        return toMonth;
    }

    /**
     * Sets the value of the toMonth property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToMonth(String value) {
        this.toMonth = value;
    }

    /**
     * Gets the value of the toYear property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getToYear() {
        return toYear;
    }

    /**
     * Sets the value of the toYear property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setToYear(String value) {
        this.toYear = value;
    }

    /**
     * Gets the value of the occCat property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOccCat() {
        return occCat;
    }

    /**
     * Sets the value of the occCat property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOccCat(String value) {
        this.occCat = value;
    }

}
