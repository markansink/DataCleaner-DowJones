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
 *       &lt;sequence>
 *         &lt;element ref="{}VesselCallSign" minOccurs="0"/>
 *         &lt;element ref="{}VesselType" minOccurs="0"/>
 *         &lt;element ref="{}VesselTonnage" minOccurs="0"/>
 *         &lt;element ref="{}VesselGRT" minOccurs="0"/>
 *         &lt;element ref="{}VesselOwner" minOccurs="0"/>
 *         &lt;element ref="{}VesselFlag" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "vesselCallSign",
        "vesselType",
        "vesselTonnage",
        "vesselGRT",
        "vesselOwner",
        "vesselFlag"
})
@XmlRootElement(name = "VesselDetails")
public class VesselDetails {

    @XmlElement(name = "VesselCallSign")
    protected String vesselCallSign;
    @XmlElement(name = "VesselType")
    protected String vesselType;
    @XmlElement(name = "VesselTonnage")
    protected String vesselTonnage;
    @XmlElement(name = "VesselGRT")
    protected String vesselGRT;
    @XmlElement(name = "VesselOwner")
    protected String vesselOwner;
    @XmlElement(name = "VesselFlag")
    protected String vesselFlag;

    /**
     * Gets the value of the vesselCallSign property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselCallSign() {
        return vesselCallSign;
    }

    /**
     * Sets the value of the vesselCallSign property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselCallSign(String value) {
        this.vesselCallSign = value;
    }

    /**
     * Gets the value of the vesselType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselType() {
        return vesselType;
    }

    /**
     * Sets the value of the vesselType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselType(String value) {
        this.vesselType = value;
    }

    /**
     * Gets the value of the vesselTonnage property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselTonnage() {
        return vesselTonnage;
    }

    /**
     * Sets the value of the vesselTonnage property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselTonnage(String value) {
        this.vesselTonnage = value;
    }

    /**
     * Gets the value of the vesselGRT property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselGRT() {
        return vesselGRT;
    }

    /**
     * Sets the value of the vesselGRT property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselGRT(String value) {
        this.vesselGRT = value;
    }

    /**
     * Gets the value of the vesselOwner property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselOwner() {
        return vesselOwner;
    }

    /**
     * Sets the value of the vesselOwner property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselOwner(String value) {
        this.vesselOwner = value;
    }

    /**
     * Gets the value of the vesselFlag property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getVesselFlag() {
        return vesselFlag;
    }

    /**
     * Sets the value of the vesselFlag property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setVesselFlag(String value) {
        this.vesselFlag = value;
    }

}
