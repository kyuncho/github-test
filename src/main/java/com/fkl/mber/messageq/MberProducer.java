/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.messageq;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fkl.common.utils.CollectionsUtils;
import com.fkl.mber.model.MberDto;

/******************************************************************
 * <pre>
 * <b>Description  : 회원 이벤트 메시지 전송</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.messageq
 * </pre>
 *
 * @author   : jungkyo.yem
 * @version  : 1.0
 * @since<pre>
 * Modification Information
 * 수정일        수정자            수정내용              
 * -----------   ---------------   -------------------------------
 *  2020. 11. 16.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/
@Service
public class MberProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	public void send(String topic, MberDto mberDto) {
		Map<String, Object> map = CollectionsUtils.domainToMap(mberDto);

	    this.kafkaTemplate.send(topic, map);
	}
}
