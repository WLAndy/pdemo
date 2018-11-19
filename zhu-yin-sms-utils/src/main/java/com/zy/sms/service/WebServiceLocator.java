
package com.zy.sms.service;

import org.apache.axis.AxisFault;
import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.Service;

import javax.xml.namespace.QName;
import javax.xml.rpc.ServiceException;
import java.net.URL;
import java.rmi.Remote;
import java.util.HashSet;
import java.util.Iterator;

public class WebServiceLocator extends Service implements WebService {

    public WebServiceLocator() {
        super();
    }

    public WebServiceLocator(String WebServiceSoap_address) {
        super();
        setWebServiceSoap_address(WebServiceSoap_address);
    }

    public WebServiceLocator(EngineConfiguration config) {
        super(config);
    }

    public WebServiceLocator(String wsdlLoc, QName sName) throws ServiceException {
        super(wsdlLoc, sName);
    }

    private String WebServiceSoap_address;

    public String getWebServiceSoap_address() {
        return WebServiceSoap_address;
    }

    public void setWebServiceSoap_address(String webServiceSoap_address) {
        WebServiceSoap_address = webServiceSoap_address;
    }

    public String getWebServiceSoapAddress() {
        return WebServiceSoap_address;
    }

    private String WebServiceSoapWSDDServiceName = "WebServiceSoap";

    public String getWebServiceSoapWSDDServiceName() {
        return WebServiceSoapWSDDServiceName;
    }

    public void setWebServiceSoapWSDDServiceName(String name) {
        WebServiceSoapWSDDServiceName = name;
    }

    public WebServiceSoap getWebServiceSoap() throws ServiceException {
        URL endpoint;
        try {
            endpoint = new java.net.URL(WebServiceSoap_address);
        } catch (java.net.MalformedURLException e) {
            throw new ServiceException(e);
        }
        return getWebServiceSoap(endpoint);
    }

    public WebServiceSoap getWebServiceSoap(URL portAddress){
        try {
            WebServiceSoapStub webServiceSoapStub = new WebServiceSoapStub(portAddress, this);
            webServiceSoapStub.setPortName(getWebServiceSoapWSDDServiceName());
            return webServiceSoapStub;
        } catch (AxisFault e) {
            return null;
        }
    }

    public void setWebServiceSoapEndpointAddress(String address) {
        WebServiceSoap_address = address;
    }

    public Remote getPort(Class serviceEndpointInterface) throws ServiceException {
        try {
            if (WebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                WebServiceSoapStub _stub = new WebServiceSoapStub(new java.net.URL(WebServiceSoap_address), this);
                _stub.setPortName(getWebServiceSoapWSDDServiceName());
                return _stub;
            }
        } catch (Throwable t) {
            throw new ServiceException(t);
        }
        throw new ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        String inputPortName = portName.getLocalPart();
        if ("WebServiceSoap".equals(inputPortName)) {
            return getWebServiceSoap();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public QName getServiceName() {
        return new QName("http://www.139130.net", "WebService");
    }

    private HashSet ports = null;

    public Iterator getPorts() {
        if (ports == null) {
            ports = new HashSet();
            ports.add(new QName("http://www.139130.net", "WebServiceSoap"));
        }
        return ports.iterator();
    }

    public void setEndpointAddress(String portName, String address) throws ServiceException {

        if ("WebServiceSoap".equals(portName)) {
            setWebServiceSoapEndpointAddress(address);
        } else {
            throw new ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
