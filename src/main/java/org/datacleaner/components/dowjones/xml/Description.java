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
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="Description1" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description2" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description3" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Description")
public class Description {

    @XmlAttribute(name = "Description1", required = true)
    protected String description1;
    @XmlAttribute(name = "Description2")
    protected String description2;
    @XmlAttribute(name = "Description3")
    protected String description3;

    /**
     * Gets the value of the description1 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription1() {
        return description1;
    }

    /**
     * Sets the value of the description1 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription1(String value) {
        this.description1 = value;
    }

    /**
     * Gets the value of the description2 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription2() {
        return description2;
    }

    /**
     * Sets the value of the description2 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription2(String value) {
        this.description2 = value;
    }

    /**
     * Gets the value of the description3 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription3() {
        return description3;
    }

    /**
     * Sets the value of the description3 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription3(String value) {
        this.description3 = value;
    }

}
