/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model;


import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fkl.common.utils.ModelMapperUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************************************
 * <pre>
 * <b>Description  : 회원 데이터 전송 객체</b>
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
 *  2020. 11. 9.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MberDto {

	/** 회원번호 */
	private String mberNo;
	
	/** 한글회원명 */
	@NotNull
	private String korMberNm;
	
	/** 로그인ID */
	@NotBlank(message = "로그인ID는 필수 입력값입니다.")
	private String loginId;
	
	/** 로그인 비밀번호 */
	@NotEmpty(message = "로그인 비밀번호는 필수 입력값입니다.")
	private String loginPasswd;

	/** 회원상태구분코드 */
	private String mberSttusSeCode;

	/** 최초가입일자 */
	private String firstScrbDe;

	/**  탈회일자 */
	private String secsnDe;

	/** 최초등록자ID */
	@NotBlank(message = "최초등록자ID는 필수 입력값입니다.")
	private String firstRegId;

	/** 최초등록프로그램 */
	@NotBlank(message = "최초등록프로그램은 필수 입력값입니다.")
	private String firstRegProgmId;
	
	/** 최종변경자ID */
	@NotBlank(message = "최종변경자ID는 필수 입력값입니다.")
	private String lastChngId;

	/** 최종변경프로그램ID */
	@NotBlank(message = "최종변경프로그램ID는 필수 입력값입니다.")
	private String lastChngProgmId;

	public static MberDto of(MberEntity mberEntity) {
		MberDto dto = ModelMapperUtils.getModelMapper().map(mberEntity, MberDto.class);
		
		return dto;
	}

}
