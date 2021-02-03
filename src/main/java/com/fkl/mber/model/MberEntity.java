/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fkl.common.utils.Constants;
import com.fkl.common.utils.ModelMapperUtils;
import com.fkl.mber.model.common.CommonModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************************************
 * <pre>
 * <b>Description  : 회원 엔티티</b>
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
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MberEntity extends CommonModel implements Serializable{
	

	/** 회원번호 */
	private String mberNo;
	
	/** 한글회원명 */
	private String korMberNm;
	
	/** 로그인ID */
	private String loginId;
	
	/** 로그인 비밀번호 */
	private String loginPasswd;
	
	/** 마지막로그인일시 */	
	@JsonFormat(timezone = DEFAULT_TIME_ZONE, shape = JsonFormat.Shape.STRING, pattern = Constants.DateTimeFormat.DATE_TIME)
	@DateTimeFormat(pattern = Constants.DateTimeFormat.DATE_TIME)
	private OffsetDateTime lastLoginDt;
	
	/** 회원상태구분코드 */
	private String mberSttusSeCode;
	
	/** 최초가입일자 */
	private String firstScrbDe;

	/**  탈회일자 */
	private String secsnDe;

	public static <T> MberEntity of(T dto) {
		MberEntity entity = ModelMapperUtils.getModelMapper().map(dto, MberEntity.class);
		
		return entity;
	}

}
