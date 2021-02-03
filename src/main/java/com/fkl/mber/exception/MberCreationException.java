/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.exception;

import com.fkl.common.exception.BizException;
import com.fkl.common.exception.ErrorCode;


/******************************************************************
 * <pre>
 * <b>Description  : 저장된 회원이 없을 때 예외처리</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.exception
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
public class MberCreationException extends BizException {

	public MberCreationException(ErrorCode errorCode) {
		super(errorCode);
	}

}
