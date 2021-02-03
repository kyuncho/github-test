/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fkl.common.model.CudResult;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberEntity;
import com.fkl.mber.repository.MberMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import lombok.extern.slf4j.Slf4j;


/******************************************************************
 * <pre>
 * <b>Description  : 회원관리 컨트롤러 테스트</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.controller
 * </pre>
 *
 * @author   : jungkyo.yem
 * @version  : 1.0
 * @since<pre>
 * Modification Information
 * 수정일        수정자            수정내용              
 * -----------   ---------------   -------------------------------
 *  2020. 11. 10.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class MberControllerTest {
	

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private MberMapper mberMapper;
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	MberDto mberDto;			// 등록 처리
	MberDto mberDtoValidation;	// Validation Check
	MberDto mberDtoWithdraw;	// 소멸 처리
	MberEntity mberEntity;		// 회원정보저장 객체
	
	@BeforeEach
	void givenMberDto() {
		// 등록 처리
		mberDto = new MberDto().builder()
				.korMberNm("홍길동")
				.loginId("loginID2")
				.loginPasswd("12345678")
				.firstRegId("loginID")
				.firstRegProgmId("createMber")
				.lastChngId("loginID")
				.lastChngProgmId("createMber")
				.build();
		
		mberEntity = MberEntity.of(mberDto);
		
		// Validation Check
		mberDtoValidation = new MberDto().builder()
				.korMberNm(null)
				.loginId(" ")
				.firstRegId("loginID")
				.firstRegProgmId("createMber")
				.lastChngId("loginID")
				.lastChngProgmId("createMber")
				.build();
		
	}
	
	/**
	 *<pre>
	 *<b>회원조회 테스트 </b>
	 *login-id를 path variable로 받아 회원정보 조회
	 *</pre>
	 *
	 * method : getMberTest
	 * @throws Exception void
	 */
	@Test
	public void getMberTest() throws Exception {
		
		mockMvc.perform(get("/api/mber/v1.0.0/mber/test123")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andDo(print());
	}
	
	/**
	 *<pre>
	 *<b>회원정보 목록 조회 테스트 </b>
	 *</pre>
	 *
	 * method : getMbersTest
	 * @throws Exception void
	 */
	@Test
	public void getMberListTest() throws Exception {

		mockMvc.perform(get("/api/mber/v1.0.0/mbers?korMberNm=홍&loginId=test")	//korMberNm=홍&loginId=test
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").exists())
		.andDo(print());
		
	}
	
	/**
	 *<pre>
	 *<b>회원등록 테스트 </b>
	 *입력 항목 Validation 
	 *</pre>
	 *
	 * method : createMberTest
	 * @throws Exception void
	 */
	@Test
	public void createMemberRequestParameterValidationTest() throws Exception {
		
		mockMvc.perform(post("/api/mber/v1.0.0/mber")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mberDtoValidation)))
		.andExpect(status().isBadRequest())
		.andDo(print());
		
	}
	
		
	/**
	 *<pre>
	 *<b>회원등록 테스트 </b>
	 *회원번호(mberNo) 생성확인
	 *</pre>
	 *
	 *
	 * method : createMberTest
	 * @throws Exception void
	 */
	@Test
	@Transactional
	public void createMemberTest() throws Exception {
				
		// 기존 테스트 데이터를 삭제한다.
		mberMapper.deleteMber("loginID2");
		
		mockMvc.perform(post("/api/mber/v1.0.0/mber")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mberDto)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.retKey").isNotEmpty())
		.andDo(print());
		
	}
	
	/**
	 *<pre>
	 *<b>회원등록 중복 테스트 </b>
	 *로그인 ID를 동일하게 하여 두 번 등록시 중복 오류 발생
	 *</pre>
	 *
	 *
	 * method : createMberTest
	 * @throws Exception void
	 */
	@Test
	@Transactional
	public void createMemberDupTest() throws Exception {

		// 정상상태의 회원데이터 생성
		mberEntity.setMberSttusSeCode("01");
		mberMapper.insertMber(mberEntity);
		
		mockMvc.perform(post("/api/mber/v1.0.0/mber")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(mberDto)))
		.andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
		.andDo(print());
	}

	/**
	 *<pre>
	 *<b>회원 탈회 </b>
	 *정상 탈회 처리시 HTTP Status 200 확인
	 *</pre>
	 *
	 * method : withdrawMemberTest
	 * @throws Exception void
	 */
	@Test
	@Transactional
	public void withdrawMemberTest() throws Exception {
		
		// 정상 상태의 회원 데이터 생성
		mberEntity.setMberSttusSeCode("01");
		mberMapper.insertMber(mberEntity);

		//retKey를 mberDtoWithdraw에 설정한다.
		log.debug("=====:::::>>>>> withdrawMemberTest::MberNo {}", mberEntity.getMberNo());
		mberDto.setMberNo(mberEntity.getMberNo());

		mockMvc.perform(put("/api/mber/v1.0.0/mber-wdra")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(mberDto)))
		.andExpect(status().isOk())
		.andDo(print());
	}
	
	@Test
	@Transactional
	public void withdrawMemberDupTest() throws Exception {
		// 탈회 상태의 회원 데이터 생성
		mberEntity.setMberSttusSeCode("03");
		mberMapper.insertMber(mberEntity);
		
		//retKey를 mberDtoWithdraw에 설정한다.
		log.debug("=====:::::>>>>> withdrawMemberDupTest::MberNo {}", mberEntity.getMberNo());
		mberDto.setMberNo(mberEntity.getMberNo());

		mockMvc.perform(put("/api/mber/v1.0.0/mber-wdra")
			.accept(MediaType.APPLICATION_JSON_UTF8)
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(mberDto)))
		.andExpect(status().isBadRequest())
		.andDo(print());
	}

}
