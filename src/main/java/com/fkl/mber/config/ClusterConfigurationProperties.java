/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.redis.cluster")
class ClusterConfigurationProperties {
	/*
	 * * spring.redis.cluster.nodes[0] = 127.0.0.1:7379 *
	 * spring.redis.cluster.nodes[1] = 127.0.0.1:7380 * ...
	 */ List<String> nodes;

	/**
	 * * Get initial collection of known cluster nodes in format {@code host:port}.
	 * * * @return
	 */
	public List<String> getNodes() {
		return nodes;
	}

	public void setNodes(List<String> nodes) {
		this.nodes = nodes;
	}
}
