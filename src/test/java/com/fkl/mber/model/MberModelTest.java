/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

/******************************************************************
 * <pre>
 * <b>Description  : Model test</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.model
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

public class MberModelTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
	/**
	 *<pre>
	 *<b>ModelMapper 테스트 </b>
	 *</pre>
	 *
	 * method : mappingDtoToEntity void
	 */
	@Test
	public void mappingDtoToEntity() {
		ModelMapper mapper = new ModelMapper();
		MberDto mberDto = MberDto.builder()
							.korMberNm("홍길동")
							.loginId("test1234")
							.loginPasswd("그냥")
							.build();
		MberEntity mber = mapper.map(mberDto, MberEntity.class);
		
		assertEquals(mber.getKorMberNm(), mberDto.getKorMberNm());
		assertEquals(mber.getLoginId(), mberDto.getLoginId());
		assertEquals(mber.getLoginPasswd(), mberDto.getLoginPasswd());
		
	}
	
	private MberDto mberDto;
	
	@BeforeEach
	void givenMberDto() {
		mberDto = MberDto.builder()
				.korMberNm(null)	//@NotNull
				.loginId(" ")		//@NotBlank
				.loginPasswd("")	//@NotEmpty
				.firstRegId("loginID")
				.firstRegProgmId("createMber")
				.lastChngId("loginID")
				.lastChngProgmId("createMber")
				.build();
	}
	
	@Test
	public void 회원_DTO_NotNull_체크() {
		
		//given
//		MberDto mberDto = MberDto.builder()
//				.korMberNm(null)	//@NotNull
//				.loginId(" ")		//@NotBlank
//				.loginPasswd("")	//@NotEmpty
//				.firstRegId("loginID")
//				.firstRegProgmId("createMber")
//				.lastChngId("loginID")
//				.lastChngProgmId("createMber")
//				.build();
	 //when
	 Set<ConstraintViolation<MberDto>> violations = validator.validate(mberDto);
	  //then
	 assertEquals(3, violations.size() );
	}
	
}
