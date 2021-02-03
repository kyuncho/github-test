/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model.common;

import java.time.OffsetDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fkl.common.utils.Constants;
import com.fkl.mber.model.MberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************************************
 * <pre>
 * <b>Description  : Model 공통 항목</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.model.common
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
public class CommonModel {

	public static final String DEFAULT_TIME_ZONE = "GMT+9";
	
	/** 최초등록자ID */
	private String firstRegId;
	
	
	/** 최초등록일시 */
	@JsonFormat(timezone = DEFAULT_TIME_ZONE, shape = JsonFormat.Shape.STRING, pattern = Constants.DateTimeFormat.DATE_TIME)
	@DateTimeFormat(pattern = Constants.DateTimeFormat.DATE_TIME)
	private OffsetDateTime firstRegDt;
	
	
	/** 최초등록프로그램 */
	private String firstRegProgmId;
	
	
	/** 최종변경자ID */
	private String lastChngId;
	
	
	/** 최종변경일시 */
	@JsonFormat(timezone = DEFAULT_TIME_ZONE, shape = JsonFormat.Shape.STRING, pattern = Constants.DateTimeFormat.DATE_TIME)
	@DateTimeFormat(pattern = Constants.DateTimeFormat.DATE_TIME)
	private OffsetDateTime lastChngDt;
	
	
	/** 최종변경프로그램ID */
	private String lastChngProgmId;
}
