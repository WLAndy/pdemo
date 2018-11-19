package com.zy.sms.service;

import javax.xml.rpc.Service;
import javax.xml.rpc.ServiceException;

public interface WebService extends Service {

    public String getWebServiceSoapAddress();

    public WebServiceSoap getWebServiceSoap() throws ServiceException;

    public  WebServiceSoap getWebServiceSoap(java.net.URL portAddress) throws ServiceException;
}
