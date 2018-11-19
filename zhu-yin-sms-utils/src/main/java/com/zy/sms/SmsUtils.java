package com.zy.sms;

import com.zy.sms.entity.*;
import com.zy.sms.service.WebServiceLocator;
import com.zy.sms.service.WebServiceSoapStub;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SmsUtils {

	public static Logger logger = LoggerFactory.getLogger(SmsUtils.class);


	/**
	 * 单一发送短信
	 * @param smsConf
	 * @param smsEntity
	 * @param mobile
	 * @return
	 * @throws Exception
	 */
	public static int send(SmsConf smsConf, SmsEntity smsEntity, String mobile) throws Exception {
		MTPacks pack = getMTPacks(smsEntity);

		MessageData messageData = new MessageData();
		messageData.setPhone(mobile);
		messageData.setContent(smsEntity.getContent());
		MessageData[] msgs = new MessageData[1];
		msgs[0] = messageData;

		pack.setMsgs(msgs);
		logger.info("pack:"+pack);
		return send(smsConf,getWebServiceSoapStub(smsConf),pack);
	}


	/**
	 * 批量发送短信
	 * @param smsConf
	 * @param smsEntity
	 * @param mobiles
	 * @return
	 * @throws Exception
	 */
	public static int batchSend(SmsConf smsConf,SmsEntity smsEntity, String...mobiles) throws Exception {
		MTPacks pack = getMTPacks(smsEntity);

		List<MessageData> messageDataList = new ArrayList<MessageData>();
		for (String mobile : mobiles){
			MessageData messageData = new MessageData();
			messageData.setPhone(mobile);
			messageData.setContent(smsEntity.getContent());
			messageDataList.add(messageData);
		}

		pack.setMsgs(messageDataList.toArray(new MessageData[messageDataList.size()]));
		logger.info("pack:"+pack);
		return send(smsConf,getWebServiceSoapStub(smsConf),pack);

	}


	/**
	 * 最后发送请求
	 * @param smsConf
	 * @param webServiceSoapStub
	 * @param mtPacks
	 * @return
	 * @throws RemoteException
	 */
	private static int send(SmsConf smsConf,WebServiceSoapStub webServiceSoapStub,MTPacks mtPacks) throws RemoteException {
		GsmsResponse resp = webServiceSoapStub.post(smsConf.getUsername(),smsConf.getPassword(),mtPacks);
		System.out.println(resp.getResult() + "--" + resp.getMessage());
		logger.info("result: {} , message: {}",resp.getResult(),resp.getMessage());
		logger.info("resp: {}",resp);
		return resp.getResult();
	}



	private static WebServiceSoapStub getWebServiceSoapStub(SmsConf smsConf) throws MalformedURLException {
		WebServiceSoapStub webServiceSoapStub = (WebServiceSoapStub) new WebServiceLocator(smsConf.getWebServiceSoapAddress()).getWebServiceSoap(new URL(smsConf.getUrl()));
		webServiceSoapStub._setProperty(MessageContext.HTTP_TRANSPORT_VERSION, HTTPConstants.HEADER_PROTOCOL_V11);
		return webServiceSoapStub;
	}


	private static MTPacks getMTPacks(SmsEntity smsEntity){
		MTPacks pack = new MTPacks();
		pack.setBatchID(UUID.randomUUID().toString());
		pack.setUuid(pack.getBatchID());
		pack.setSendType(0);
		pack.setMsgType(1);
		pack.setBatchName(smsEntity.getBatchName());
		logger.info("pack:"+pack);
		return pack;
	}
}
