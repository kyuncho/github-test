/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author User
 *
 */
/******************************************************************
 * <pre>
 * <b>Description  : 회원탈회 파라미터 전송 객체</b>
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
 *  2020. 11. 28.  jungkyo.yem 최초생성                 
 * </pre>
 * @see <b>Copyright (C) by 한국후지쯔주식회사 All right reserved.</b>
 *******************************************************************/ 
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MberWdraDto {

	/** 회원번호 */
	@NotBlank(message = "회원번호는 필수 입력값입니다.")
	private String mberNo;

	/** 회원상태구분코드 */
	@ApiModelProperty(value="회원상태구분코드")
	private String mberSttusSeCode;

	/** 탈회일자 */
	@ApiModelProperty(value="탈회일자")
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
}
