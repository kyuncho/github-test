/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.model.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import com.fkl.mber.model.MberSearchDto;

@Component
public class MberSearchDtoValidator {

	public void validate(MberSearchDto mberSearchDto, Errors errors) {
		if (StringUtils.isEmpty(mberSearchDto.getMberNo()) &&
				StringUtils.isEmpty(mberSearchDto.getKorMberNm()) &&
				StringUtils.isEmpty(mberSearchDto.getLoginId())) {
			errors.reject("emptyValue", "회원번호, 한글회원이름, 로그인아이디 중 하나는 입력해야합니다.");
		}
		
	}
}
