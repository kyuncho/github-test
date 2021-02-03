/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkl.common.exception.BizException;
import com.fkl.common.model.CudResult;
import com.fkl.common.utils.DateTimeUtil;
import com.fkl.mber.exception.MberNotFoundException;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberEntity;
import com.fkl.mber.model.MberSearchDto;
import com.fkl.mber.model.MberWdraDto;
import com.fkl.mber.repository.MberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MberServiceTest {

	@Autowired
	private MberService service;
	
	@Autowired
	private MberMapper mapper;
		
	/**
	 *<pre>
	 *<b>회원 조회 </b>
	 *조회된 회원이 없으면 예외를 반환
	 *</pre>
	 *
	 * method : selectMbersFailTest
	 * @throws Exception void
	 */
	@Test
	void selectMbersFailTest() {
		
		// 테스트 데이터가 있으면 삭제한다.
		mapper.deleteMber("test99999");
		
		// 조회 매개변수를 만든다.
		MberSearchDto param = new MberSearchDto().builder()
				.korMberNm("홍길동")
				.loginId("test99999")
				.build();

		// 데이터가 조회되지 않은 경우 예외를 던지는지 검증한다.
		assertThrows(MberNotFoundException.class,
				() -> service.selectMbers(param)
				);
	}
	
	
	/**
	 *<pre>
	 *<b>회원 조회 </b>
	 *정상 조회시 리스트에 객체가 있는지 확인
	 *</pre>
	 *
	 * method : selectMbersTest
	 * @throws Exception 
	 * @throws Exception void
	 */
	@Test
	@Transactional
	void selectMbersTest() throws Exception {
		// 테스트 데이터를 만든다.
		MberEntity mberEntity = new MberEntity().builder()
				.korMberNm("테스트")
				.loginId("test99999")
				.loginPasswd("12345678")
				.mberSttusSeCode("01")
				.firstScrbDe(DateTimeUtil.getDateNow())
				.build();
		mberEntity.setFirstRegId("test99999");
		mberEntity.setFirstRegProgmId("selectMbersTest");
		mberEntity.setLastChngId("test99999");
		mberEntity.setLastChngProgmId("selectMbersTest");
		
		mapper.insertMber(mberEntity);
		
		// 조회 매개변수를 만든다.
		MberSearchDto param = new MberSearchDto().builder()
				.korMberNm("테스트")
				.loginId("test99999")
				.build();
		
		List<MberDto> returnedMber = service.selectMbers(param);  //public List<MberDto> selectMbers(MberDto mberDto);
		
		// 반환한 리스트에 객체가 있는지 확인한다.
		Assertions.assertTrue(returnedMber.size() > 0);
		
		ObjectMapper objMapper = new ObjectMapper();
		String jsonString = objMapper.writeValueAsString(returnedMber);
		log.debug("=====:::::>>>>> " + jsonString);
		
	}
	
	/**
	 *<pre>
	 *<b>회원등록</b>
	 *중복 등록시 예외를 반환
	 *</pre>
	 *
	 * method : insertMberDupTest void
	 */
	@Test
	void insertMberDupTest() {
		// 테스트 데이터를 만든다.
		MberEntity mberEntity = new MberEntity().builder()
				.korMberNm("테스트")
				.loginId("test99999")
				.loginPasswd("12345678")
				.mberSttusSeCode("01")
				.firstScrbDe(DateTimeUtil.getDateNow())
				.build();
		mberEntity.setFirstRegId("test99999");
		mberEntity.setFirstRegProgmId("selectMbersTest");
		mberEntity.setLastChngId("test99999");
		mberEntity.setLastChngProgmId("selectMbersTest");
		
		mapper.insertMber(mberEntity);
		
		// 조회 매개변수를 만든다.
		MberDto mberDto = MberDto.of(mberEntity);

		// 중복 등록의 경우 예외를 던지는지 검증한다.
		assertThrows(BizException.class, 
				() -> service.insertMber(mberDto)
				);
		
	}
	
	/**
	 *<pre>
	 *<b>회원등록 </b>
	 *정상 등록시 처리 건수와 반환 key를 확인
	 *</pre>
	 *
	 * method : insertMberTest void
	 */
	@Test
	void insertMberTest() {
		
		// 테스트 데이터가 있으면 삭제한다.
		mapper.deleteMber("test99999");
		
		// 저장 매개변수를 만든다.
		MberDto mberDto = new MberDto().builder()
				.korMberNm("테스트")
				.loginId("test99999")
				.loginPasswd("12345678")
				.firstScrbDe(DateTimeUtil.getDateNow())
				.firstRegId("test99999")
				.firstRegProgmId("selectMbersTest")
				.lastChngId("test99999")
				.lastChngProgmId("selectMbersTest")
				.build();

		// 반환 결과를 확인한다.
		CudResult cudResult = service.insertMber(mberDto);
		Assertions.assertTrue(cudResult.getSuccessCnt() == 1);
		Assertions.assertNotNull(cudResult.getRetKey());

	}
	
	/**
	 *<pre>
	 *<b>회원탈회 </b>
	 *회원정보가 없는 경우 예외를 반환
	 *</pre>
	 *
	 * method : updateMberWdraFailTest void
	 */
	@Test
	void updateMberWdraNoMemberTest() {
		
		// 탈회 매개변수를 만든다.
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo("xxxxxxxxxx")
				.build();
		
		assertThrows(MberNotFoundException.class, 
				() -> service.updateMberWdra(param)
				);
	}
	
	/**
	 *<pre>
	 *<b>회원탈회 </b>
	 *탈회한 회원정보로 탈회 처리시 예외를 반환
	 *</pre>
	 *
	 * method : updateMberWdraDupTest void
	 */
	@Test
	@Transactional
	void updateMberWdraDupTest() {
		// 테스트 데이터 생성
		MberEntity mber = new MberEntity().builder()
			.korMberNm("테스트")
			.loginId("test99999")
			.loginPasswd("12345678")
			.mberSttusSeCode("03")		// 탈회한 회원으로 데이터 생성
			.build();
		mber.setFirstRegId("test99999");
		mber.setFirstRegProgmId("updateMberWdraTest");
		mber.setLastChngId("test99999");
		mber.setLastChngProgmId("updateMberWdraTest");
		mapper.insertMber(mber);

		log.debug("=====:::::>>>>> created MberNo : {}", mber.getMberNo());

		// mapper에서 반환한 mberNo를 설정
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo(mber.getMberNo())
				.secsnDe(DateTimeUtil.getDateNow())
				.lastChngId("test99999")
				.lastChngProgmId("updateMberWdraTest")
				.build();
		

		assertThrows(BizException.class, 
				() -> service.updateMberWdra(param));
	}

	/**
	 *<pre>
	 *<b>회원탈회 </b>
	 *정상처리시 반환값 확인
	 *</pre>
	 *
	 * method : updateMberWdraTest void
	 */
	@Test
	@Transactional
	void updateMberWdraTest() {
		// 테스트 데이터 생성
		MberEntity mber = new MberEntity().builder()
			.korMberNm("테스트")
			.loginId("test99999")
			.loginPasswd("12345678")
			.mberSttusSeCode("01")		// 가입 회원으로 데이터 생성
			.build();
		mber.setFirstRegId("test99999");
		mber.setFirstRegProgmId("updateMberWdraTest");
		mber.setLastChngId("test99999");
		mber.setLastChngProgmId("updateMberWdraTest");
		mapper.insertMber(mber);

		log.debug("=====:::::>>>>> created MberNo : {}", mber.getMberNo());

		// mapper에서 반환한 mberNo를 설정
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo(mber.getMberNo())
				.secsnDe(DateTimeUtil.getDateNow())
				.lastChngId("test99999")
				.lastChngProgmId("updateMberWdraTest")
				.build();
		
		CudResult cudResult = service.updateMberWdra(param);
		Assertions.assertTrue(cudResult.getSuccessCnt() == 1);
		Assertions.assertNotNull(cudResult.getRetKey());
		
	}
}
