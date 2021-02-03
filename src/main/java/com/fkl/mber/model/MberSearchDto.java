/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model;


import com.fkl.common.utils.ModelMapperUtils;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/******************************************************************
 * <pre>
 * <b>Description  : 회원조회</b>
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
 *  2020. 11. 20.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class MberSearchDto {

	/** 회원ID */
	@ApiModelProperty(value="회원번호")
	private String mberNo;
	
	/** 한글회원명 */
	@ApiModelProperty(value="한글회원명")
	private String korMberNm;
	
	/** 로그인아이디 */
	@ApiModelProperty(value="로그인아이디", name="login_id", notes="이곳에 로그인아이디를 넣어주세요", example = "loginId", required = true)
	private String loginId;

	/** 마지막로그인일시 */
	@ApiModelProperty(value="마지막로그인일시")
	private String lastLoginDt;
	
	/** 회원상태구분코드 */
	@ApiModelProperty(value="회원상태구분코드")
	private String mberSttusSeCode;
	
	/** 최초가입일자 */
	@ApiModelProperty(value="최초가입일자")
	private String firstScrbDe;
	
	/** 탈회일자 */
	@ApiModelProperty(value="탈회일자")
	private String secsnDe;
	
	public static MberSearchDto of(MberEntity mberEntity) {
		MberSearchDto dto = ModelMapperUtils.getModelMapper().map(mberEntity, MberSearchDto.class);
		
		return dto;
	}

}
