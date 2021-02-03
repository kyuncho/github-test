/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.service;

import java.util.List;

import com.fkl.common.model.CudResult;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberSearchDto;
import com.fkl.mber.model.MberWdraDto;

/******************************************************************
 * <pre>
 * <b>Description  : 회원정보관리 서비스</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.service
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

public interface MberService {

	 /**
	 *<pre>
	 *<b>회원목록을 반환한다. </b>
	 *</pre>
	 *
	 * method : selectMbers
	 * @param mber
	 * @return List<Mber>
	 */
	public List<MberDto> selectMbers(MberSearchDto param);
	
	 /**
	 *<pre>
	 *<b>회원목록을 반환한다.(레디스사용) </b>
	 *</pre>
	 *
	 * method : selectMbers
	 * @param mber
	 * @return List<Mber>
	 */
	public List<MberDto> selectMbersByRedis(MberSearchDto param);	
	
	/**
	 *<pre>
	 *<b>지정된 회원정보를 반환한다. </b>
	 *</pre>
	 *
	 * method : getMber
	 * @param mberDto
	 * @return MberEntity
	 */
	public MberDto selectMber(MberSearchDto param);
	
	/**
	 *<pre>
	 *<b>회원정보를 등록한다. </b>
	 *</pre>
	 *
	 * method : insertMber
	 * @param mberDto
	 * @return CudResult
	 */
	public CudResult insertMber(MberDto mberDto);
	
	/**
	 *<pre>
	 *<b>회원정보를 탈회처리한다. </b>
	 *</pre>
	 *
	 * method : updateMberWdra
	 * @param mberDto
	 * @return MberEntity
	 */
	public CudResult updateMberWdra(MberWdraDto mberWdraDto);
}
