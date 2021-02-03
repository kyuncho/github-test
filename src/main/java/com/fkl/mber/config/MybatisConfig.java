/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.config;


import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/******************************************************************
 * <pre>
 * <b>Description  : Mybatis 환경 구성</b>
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
 *  2020. 11. 9.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/

@Configuration
@PropertySource("classpath:config/application.yml")
@MapperScan(basePackages = "com.fkl.mber.repository")
public class MybatisConfig {
	
	/**
	 *<pre>
	 *<b>Hikari DataSource용 환경 정보 빈 생성 </b>
	 *</pre>
	 *
	 * method : hikariConfig
	 * @return HikariConfig
	 */
	@Bean
	@ConfigurationProperties(prefix="spring.datasource.hikari")
	public HikariConfig hikariConfig() {
		return new HikariConfig();
	}
	
	
	/**
	 *<pre>
	 *<b>HikariConfig 빈을 사용한 DataSource 빈 생성 </b>
	 *</pre>
	 *
	 * method : dataSource
	 * @return DataSource
	 */
	@Bean
	public DataSource dataSource() {
		DataSource dataSource = new HikariDataSource(hikariConfig());
		return dataSource;
	}
	
	/**
	 *<pre>
	 *<b>SqlSession 관리용 SqlSessionFactory 빈 생성 </b>
	 *데이터베이스와의 연결과 SQL 실행 기능을 위한 빈을 생성한다.
	 *</pre>
	 *
	 * method : sqlSessionFactory
	 * @param dataSource
	 * @return sqlSessionFactory
	 * @throws Exception SqlSessionFactory
	 */
	@Bean(name="sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(
			@Qualifier("dataSource") final DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml"));
		// mybatis config 추가
		// mapUnderscoreToCamelCase 설정을 적용하여 '_'를 사용하는 컬럼명을 
		//camelcase의 변수명으로 변환한다.
		Resource mybatisConfig = new PathMatchingResourcePatternResolver().getResource("classpath:config/mybatis-config.xml");
		sqlSessionFactory.setConfigLocation(mybatisConfig);
		
		return sqlSessionFactory.getObject();
	}
	
	/**
	 *<pre>
	 *<b>SQL 실행 세션 빈 생성 </b>
	 *SQL 실행 및 트랜잭션 제어를 위한 SqlSession 인터페이스를 구현한 객체를 생성한다. 
	 *</pre>
	 *
	 * method : sqlSession
	 * @param sqlSessionFactory
	 * @return SqlSessionTemplate
	 */
	@Bean
	public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}
	
	/**
	 *<pre>
	 *<b>트랜잭션 처리 빈 생성 </b>
	 *Connection의 트랜잭션 API를 이용해서 트랜잭션을 관리해 주는 트랜잭션매니저를 생성한다.
	 *</pre>
	 *
	 * method : transactionManager
	 * @param dataSource
	 * @return DataSourceTransactionManager
	 */
	@Bean
	public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") final DataSource dataSource) {
		return new DataSourceTransactionManager(dataSource);
	}
}
