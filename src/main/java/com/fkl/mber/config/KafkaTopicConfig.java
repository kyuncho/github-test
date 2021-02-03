/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/******************************************************************
 * <pre>
 * <b>Description  : 회원용 Kafka 토픽 환경 구성</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.config
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
//@Configuration
public class KafkaTopicConfig {

	@Value(value = "${kafka.producer.bootstrap-servers}")
	private String bootstrapAddress;
	
	@Value(value = "${kafka.topic.name.mber-join}")
	private String joinTopicName;
	
	@Value(value="${kafka.topic.name.mber-withdrawal}")
	private String secessionTopicName;
	
	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}
	
	@Bean
	public NewTopic joinTopic() {
		return new NewTopic(joinTopicName, 3, (short)3);
	}
	
	@Bean
	public NewTopic secessionTopic() {
		return new NewTopic(secessionTopicName, 3, (short)3);
	}
}
