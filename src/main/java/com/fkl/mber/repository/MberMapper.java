/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberEntity;

/******************************************************************
 * <pre>
 * <b>Description  : 회원 정보 Mapper 인터페이스</b>
 * <b>Project Name : mber</b>
 * package  : com.fkl.mber.repository
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
@Mapper
@Repository
public interface MberMapper {

	/**
	 *<pre>
	 *<b>회원 목록을 조회한다. </b>
	 *</pre>
	 *
	 * method : selectMber
	 * @param mber
	 * @return List<Mber>
	 */
	public List<MberEntity> selectMbers(MberEntity mber);

	/**
	 *<pre>
	 *<b>회원 단건을 조회한다. </b>
	 *</pre>
	 *
	 * method : selectMber
	 * @param mber
	 * @return MberDto
	 */
	public MberEntity selectMber(MberEntity mber);
	
	/**
	 *<pre>
	 *<b>로그인 아이디로 저장되어 있는 회원정보의 개수를 조회한다. </b>
	 *</pre>
	 *
	 * method : selectMberWithLoginId
	 * @param mber
	 * @return int
	 */
	public int countMberWithLoginId(String loginId);
	
	/**
	 *<pre>
	 *<b>회원번호로 저장되어 있는 회원정보의 개수를 조회한다. </b>
	 *</pre>
	 *
	 * method : countMberWithMberNo
	 * @param mber
	 * @return int
	 */
	public int countMberWithMberNo(String mberNo);
	
	/**
	 *<pre>
	 *<b>회원정보를 저장한다. </b>
	 *</pre>
	 *
	 * method : insertMber
	 * @param mber
	 * @return int
	 */
	public int insertMber(MberEntity mber);
	
	/**
	 *<pre>
	 *<b>회원정보를 수정한다. </b>
	 *</pre>
	 *
	 * method : updateMber
	 * @param mber
	 * @return int
	 */
	public int updateMberWdra(MberEntity mber);
	
	public int deleteMber(String loginId); 
}
