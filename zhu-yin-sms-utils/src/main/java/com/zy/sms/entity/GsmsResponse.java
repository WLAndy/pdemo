/**
 * GsmsResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.zy.sms.entity;

public class GsmsResponse  implements java.io.Serializable {
    private int result;

    private String uuid;

    private String message;

    private String attributes;

    public GsmsResponse() {
    }

    public GsmsResponse(
           int result,
           String uuid,
           String message,
           String attributes) {
           this.result = result;
           this.uuid = uuid;
           this.message = message;
           this.attributes = attributes;
    }


    /**
     * Gets the result value for this GsmsResponse.
     *
     * @return result
     */
    public int getResult() {
        return result;
    }


    /**
     * Sets the result value for this GsmsResponse.
     *
     * @param result
     */
    public void setResult(int result) {
        this.result = result;
    }


    /**
     * Gets the uuid value for this GsmsResponse.
     *
     * @return uuid
     */
    public String getUuid() {
        return uuid;
    }


    /**
     * Sets the uuid value for this GsmsResponse.
     *
     * @param uuid
     */
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    /**
     * Gets the message value for this GsmsResponse.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }


    /**
     * Sets the message value for this GsmsResponse.
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * Gets the attributes value for this GsmsResponse.
     *
     * @return attributes
     */
    public String getAttributes() {
        return attributes;
    }


    /**
     * Sets the attributes value for this GsmsResponse.
     *
     * @param attributes
     */
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof GsmsResponse)) return false;
        GsmsResponse other = (GsmsResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            this.result == other.getResult() &&
            ((this.uuid==null && other.getUuid()==null) ||
             (this.uuid!=null &&
              this.uuid.equals(other.getUuid()))) &&
            ((this.message==null && other.getMessage()==null) ||
             (this.message!=null &&
              this.message.equals(other.getMessage()))) &&
            ((this.attributes==null && other.getAttributes()==null) ||
             (this.attributes!=null &&
              this.attributes.equals(other.getAttributes())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getResult();
        if (getUuid() != null) {
            _hashCode += getUuid().hashCode();
        }
        if (getMessage() != null) {
            _hashCode += getMessage().hashCode();
        }
        if (getAttributes() != null) {
            _hashCode += getAttributes().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GsmsResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "GsmsResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("result");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "result"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uuid");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "uuid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributes");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "attributes"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

    @Override
    public String toString() {
        return "GsmsResponse{" +
                "result=" + result +
                ", uuid='" + uuid + '\'' +
                ", message='" + message + '\'' +
                ", attributes='" + attributes + '\'' +
                ", __equalsCalc=" + __equalsCalc +
                ", __hashCodeCalc=" + __hashCodeCalc +
                '}';
    }
}
