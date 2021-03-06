
package com.zy.sms.entity;

import org.apache.axis.description.ElementDesc;
import org.apache.axis.description.TypeDesc;

public class MediaItems  implements java.io.Serializable {
    private String meta;

    private byte[] data;

    public MediaItems() {
    }

    public MediaItems(
           String meta,
           byte[] data) {
           this.meta = meta;
           this.data = data;
    }


    /**
     * Gets the meta value for this MediaItems.
     *
     * @return meta
     */
    public String getMeta() {
        return meta;
    }


    /**
     * Sets the meta value for this MediaItems.
     *
     * @param meta
     */
    public void setMeta(String meta) {
        this.meta = meta;
    }


    /**
     * Gets the data value for this MediaItems.
     *
     * @return data
     */
    public byte[] getData() {
        return data;
    }


    /**
     * Sets the data value for this MediaItems.
     *
     * @param data
     */
    public void setData(byte[] data) {
        this.data = data;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof MediaItems)) return false;
        MediaItems other = (MediaItems) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.meta==null && other.getMeta()==null) ||
             (this.meta!=null &&
              this.meta.equals(other.getMeta()))) &&
            ((this.data==null && other.getData()==null) ||
             (this.data!=null &&
              java.util.Arrays.equals(this.data, other.getData())));
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
        if (getMeta() != null) {
            _hashCode += getMeta().hashCode();
        }
        if (getData() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getData());
                 i++) {
                Object obj = java.lang.reflect.Array.get(getData(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static TypeDesc typeDesc = new TypeDesc(MediaItems.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.139130.net", "MediaItems"));
        ElementDesc elemField = new ElementDesc();
        elemField.setFieldName("meta");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "meta"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("data");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.139130.net", "data"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "base64Binary"));
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

}
