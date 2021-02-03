/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fkl.common.exception.BizException;
import com.fkl.common.exception.ErrorCode;
import com.fkl.common.model.CudResult;
import com.fkl.mber.messageq.MberProducer;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberSearchDto;
import com.fkl.mber.model.MberWdraDto;
import com.fkl.mber.model.validator.MberSearchDtoValidator;
import com.fkl.mber.service.MberService;

import lombok.extern.slf4j.Slf4j;

/******************************************************************
 * <pre>
 * <b>Description  : 회원정보 컨트롤러</b>
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
 *  2020. 11. 9.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/
@Slf4j
@RestController
@RequestMapping("/api/mber")
public class MberController {
	
	private final MberService service;
	
	private final MberProducer mberProducer;
	
	private final MberSearchDtoValidator mberSearchValidator;	
	
	@Value("${kafka.topic.name.mber-join}")
    private String mberJoinTopicName;
	
	@Value("${kafka.topic.name.mber-withdrawal}")
    private String mberWithdrawalTopicName;
	
	public MberController(MberService service, MberProducer mberProducer, MberSearchDtoValidator mberSearchValidator) {
		this.service = service;
		this.mberProducer = mberProducer;
		this.mberSearchValidator = mberSearchValidator;
	}
	
	
	/**
	 *<pre>
	 *<b>로그인아이디로 회원정보 단건 조회 </b>
	 *PathVariable을 이용한 파라미터 취득
	 *</pre>
	 *
	 * method : getMber
	 * @param loginId
	 * @return ResponseEntity<List<MberDto>>
	 */
	@GetMapping("/v1.0.0/mber/{login-id}")
	public ResponseEntity<MberDto> getMber(@PathVariable(value = "login-id") String loginId) {

		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> getMber :: input :: {}", loginId);
		}

		MberSearchDto param = new MberSearchDto().builder().loginId(loginId).build();
		MberDto mberDto = this.service.selectMber(param);
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> getMber :: output :: {}", mberDto);
		}
		
		return ResponseEntity.ok(mberDto);
	}

	/**
	 *<pre>
	 *<b>회원정보 목록 조회 </b>
	 *Query String을 이용한 파라미터 취득
	 *</pre>
	 *
	 * method : getMbers
	 * @param mberDto
	 * @return ResponseEntity<List<MberEntity>>
	 */
	@GetMapping("/v1.0.0/mbers")
	public ResponseEntity<List<MberDto>> getMbers(@Valid MberSearchDto mberSearchDto, Errors errors) {

		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> getMbers :: input :: {}", mberSearchDto);
		}

		mberSearchValidator.validate(mberSearchDto, errors);
		if (errors.hasErrors()) {
		//	throw new BizException(errors.getGlobalError().getDefaultMessage(), ErrorCode.INVALID_INPUT_VALUE);
		}
		
		List<MberDto> mberList = this.service.selectMbers(mberSearchDto);
		
		return ResponseEntity.ok(mberList);
	}
	
	/**
	 *<pre>
	 *<b>회원정보 목록 조회(레디스사용) </b>
	 *Query String을 이용한 파라미터 취득
	 *</pre>
	 *
	 * method : getMbers
	 * @param mberDto
	 * @return ResponseEntity<List<MberEntity>>
	 */
	@GetMapping("/v1.0.0/mbersByRedis")
	public ResponseEntity<List<MberDto>> getMbersByRedis(@Valid MberSearchDto mberSearchDto, Errors errors) {

		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> getMbers :: input :: {}", mberSearchDto);
		}

		mberSearchValidator.validate(mberSearchDto, errors);
		if (errors.hasErrors()) {
		//	throw new BizException(errors.getGlobalError().getDefaultMessage(), ErrorCode.INVALID_INPUT_VALUE);
		}
		
		List<MberDto> mberList = this.service.selectMbersByRedis(mberSearchDto);
		
		return ResponseEntity.ok(mberList);
	}
	
	/**
	 *<pre>
	 *<b>회원등록 </b>
	 *Json을 이용한 파라미터 취득
	 *</pre>
	 *
	 * method : createMber
	 * @param mberDto
	 * @return ResponseEntity<CudResult>
	 */
	@PostMapping("/v1.0.0/mber")
	public ResponseEntity<CudResult> createMber(@RequestBody @Valid MberDto mberDto) {
		CudResult cudResult = this.service.insertMber(mberDto);
		
		//최초 가입 포인트 적립처리(kafka이용샘플)
		MberDto mberDto2 = new MberDto();
		mberDto2.setMberNo(cudResult.getRetKey());
		mberDto2.setFirstRegId(mberDto.getFirstRegId());
		mberDto2.setLastChngId(mberDto.getLastChngId());
		mberDto2.setFirstRegProgmId(mberDto.getFirstRegProgmId());
		mberDto2.setLastChngProgmId(mberDto.getLastChngProgmId());
		mberProducer.send(mberJoinTopicName, mberDto2);
		
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> createMber :: output :: {}", cudResult);
		}
		return ResponseEntity.ok(cudResult);
	}

	/**
	 *<pre>
	 *<b>회원탈회 </b>
	 *</pre>
	 *
	 * method : updateMberWdra
	 * @param mberDto
	 * @return ResponseEntity<CudResult>
	 */
	@PutMapping("/v1.0.0/mber-wdra")
	public ResponseEntity<CudResult> updateMberWdra(@RequestBody MberWdraDto mberWdraDto) {
		CudResult cudResult = this.service.updateMberWdra(mberWdraDto);

		//회원탈퇴시 포인트 사용처리(kafka이용샘플)
		MberDto mberDto2 = new MberDto();
		mberDto2.setMberNo(cudResult.getRetKey());
		mberDto2.setFirstRegId(mberWdraDto.getFirstRegId());
		mberDto2.setLastChngId(mberWdraDto.getLastChngId());
		mberDto2.setFirstRegProgmId(mberWdraDto.getFirstRegProgmId());
		mberDto2.setLastChngProgmId(mberWdraDto.getLastChngProgmId());
		mberProducer.send(mberWithdrawalTopicName, mberDto2);
				
		return ResponseEntity.ok(cudResult);
	}

}
