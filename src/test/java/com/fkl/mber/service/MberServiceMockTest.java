/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fkl.common.exception.BizException;
import com.fkl.common.model.CudResult;
import com.fkl.common.utils.DateTimeUtil;
import com.fkl.mber.exception.MberCreationException;
import com.fkl.mber.exception.MberNotFoundException;
import com.fkl.mber.exception.MberWdraException;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberEntity;
import com.fkl.mber.model.MberSearchDto;
import com.fkl.mber.model.MberWdraDto;
import com.fkl.mber.repository.MberMapper;

@ExtendWith(MockitoExtension.class)	
class MberServiceMockTest {

	@Mock
	private MberMapper mberMapper;
	
//	@Mock
//	private CudResult cudResult;	// 예상 Output

	@InjectMocks
	private MberService service = new MberServiceImpl();
	
	/**
	 *<pre>
	 *<b>회원 조회 테스트 </b>
	 *</pre>
	 *
	 * method : selectMberTest void
	 */
	@Test
	void selectMberTest() {
		// Input parameter
		MberSearchDto param = new MberSearchDto().builder()
				.loginId("loginID1")
				.build();
		
		// Mapper 주입 파라미터
		MberEntity mber = MberEntity.of(param);
		
		// 예상 Output
		MberEntity result = new MberEntity().builder()
				.mberNo("9999999999")
				.korMberNm("테스터")
				.loginId("loginID1")
				.mberSttusSeCode("01")
				.firstScrbDe("20201101")
				.build();
		
		//MberMapper의 회원정보 조회 메소드가 
		//예상 Output을 반환하도록 동작을 지정한다. 
		when(mberMapper.selectMber(mber)).thenReturn(result);
		
		//MberService의 회원정보 조회 메소드가 반환할 예상 데이터를 생성한다.
		MberDto mberDto = MberDto.of(result);
		
		//회원조회 서비스가 예상되는 데이터를 반환하는지 확인한다.
		assertEquals(mberDto, service.selectMber(param));
		
	}
	
	/**
	 *<pre>
	 *<b>회원 조회 테스트 </b>
	 *조회된 회원 정보가 없으면 예외 발생
	 *</pre>
	 *
	 * method : selectMberFailTest void
	 */
	@Test
	void selectMberFailTest() {
		// Input parameter
		MberSearchDto param = new MberSearchDto().builder()
				.loginId("loginID1")
				.build();
		
		// Mapper 주입 파라미터
		MberEntity mber = MberEntity.of(param);
		
		// 예상 Output
		MberEntity result = new MberEntity().builder()
				.mberNo("9999999999")
				.korMberNm("테스터")
				.loginId("loginID1")
				.mberSttusSeCode("01")
				.firstScrbDe("20201101")
				.build();
		
		//MberMapper의 회원정보 조회 메소드가 
		//null을 반환하도록 동작을 지정한다. 
		when(mberMapper.selectMber(mber)).thenReturn(null);
		
		assertThrows(MberNotFoundException.class,
				() -> service.selectMber(param));
	}
	
	/**
	 *<pre>
	 *<b>회원 등록 테스트 </b>
	 *동일한 로그인 아이디를 가진 회원 존재시 예외발생
	 *</pre>
	 *
	 * method : insertMberDupTest void
	 */
	@Test
	void insertMberDupTest() {
		MberDto mberDto = new MberDto().builder()
				.korMberNm("테스터")
				.loginId("loginID1")
				.loginPasswd("1234567")
				.mberSttusSeCode("01")
				.firstScrbDe("20201101")
				.firstRegId("loginID1")
				.firstRegProgmId("insertMberTest")
				.lastChngId("loginID1")
				.lastChngProgmId("insertMberTest")
				.build();

		//MberMapper Mock의 selectMber에 전달할 매개변수
		MberEntity mberEntity = MberEntity.of(mberDto);

		//MberMapper의 로그인 아이디로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//MberEntity를 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mberEntity)).thenReturn(mberEntity);
		
		//회원정보 존재시 BizException을 반환하는지 확인한다.
		assertThrows(BizException.class,
				() -> service.insertMber(mberDto));
	}
	
	/**
	 *<pre>
	 *<b>회원 등록 테스트 </b>
	 *등록된 회원정보가 없을 때 예외발생
	 *</pre>
	 *
	 * method : insertMberFailTest void
	 */
	@Test
	void insertMberFailTest() {
		MberDto mberDto = new MberDto().builder()
				.korMberNm("테스터")
				.loginId("loginID1")
				.loginPasswd("1234567")
				.mberSttusSeCode("01")
				.firstScrbDe("20201101")
				.firstRegId("loginID1")
				.firstRegProgmId("insertMberTest")
				.lastChngId("loginID1")
				.lastChngProgmId("insertMberTest")
				.build();
		
		//MberMapper Mock의 insertMber에 전달할 매개변수
		MberEntity mberEntity = MberEntity.of(mberDto);

		//MberMapper의 로그인 아이디로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//null을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mberEntity)).thenReturn(null);
		
		//MberMapper의 회원정보 등록 메소드가 처리 갯수로 0을 반환하도록 동작을 지정해준다.
		when(mberMapper.insertMber(mberEntity)).thenReturn(0);
		
		//저장된 정보가 없을 때 MberCreationException을 반환하는지 확인한다.
		assertThrows(MberCreationException.class,
				() -> service.insertMber(mberDto));
	}
	
	/**
	 *<pre>
	 *<b>회원 등록 테스트 </b>
	 *정상 등록 시 반환값 확인
	 *</pre>
	 *
	 * method : insertMberTest void
	 */
	@Test
	void insertMberTest() {

		MberDto mberDto = new MberDto().builder()
				.mberNo("9999999999")	//회원정보저장시 Mapper가 만들어주지만 주입방법을 몰라서 하드코딩
				.korMberNm("테스터")
				.loginId("loginID1")
				.loginPasswd("1234567")
				.mberSttusSeCode("01")
				.firstScrbDe("20201101")
				.firstRegId("loginID1")
				.firstRegProgmId("insertMberTest")
				.lastChngId("loginID1")
				.lastChngProgmId("insertMberTest")
				.build();
		
		//MberMapper Mock의 insertMber에 전달할 매개변수
		MberEntity insertMber = MberEntity.of(mberDto);

		CudResult cudResult = new CudResult("회원등록", 1, 0, CudResult.SUCCESS, "9999999999");

		//MberMapper의 로그인 아이디로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//0을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(insertMber)).thenReturn(null);
		
		//MberMapper Mock의 회원정보 등록 메소드가 처리 갯수로 1을 반환하도록 동작을 지정해준다.
		when(mberMapper.insertMber(insertMber)).thenReturn(1);

		//회원저장 서비스가 예상되는 결과를 반환하는지 확인한다.
		assertEquals(cudResult, service.insertMber(mberDto));		
	}
	
	/**
	 *<pre>
	 *<b>회원 탈회 테스트 </b>
	 *회원정보가 없는 경우 예외발생
	 *</pre>
	 *
	 * method : updateMberWdraNotExistMberTest void
	 */
	@Test 
	void updateMberWdraNotExistMberTest() {
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo("9999999999")
				.secsnDe(DateTimeUtil.getDateNow())
				.firstRegId("loginID1")
				.firstRegProgmId("updateMberWdraDupTest")
				.lastChngId("loginID1")
				.lastChngProgmId("updateMberWdraDupTest")
				.build();
		
		//MberMapper Mock의 selectMber에 전달할 매개변수
		MberEntity mberEntity = MberEntity.of(param);
		
		
		//MberMapper의 회원번호로 저장되어 있는 회원정보를 조회하는 메소드가 
		//null을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mberEntity)).thenReturn(null);
		
		//회원정보 존재시 BizException을 반환하는지 확인한다.
		assertThrows(BizException.class,
				() -> service.updateMberWdra(param));
	}

	/**
	 *<pre>
	 *<b>회원 탈회 테스트 </b>
	 *이미 탈회처리한 회원인 경우 예외발생
	 *</pre>
	 *
	 * method : updateMberWdraDupTest void
	 */
	@Test 
	void updateMberWdraDupTest() {
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo("9999999999")
				.secsnDe(DateTimeUtil.getDateNow())
				.firstRegId("loginID1")
				.firstRegProgmId("updateMberWdraDupTest")
				.lastChngId("loginID1")
				.lastChngProgmId("updateMberWdraDupTest")
				.build();
		
		//MberMapper Mock의 selectMber에 전달할 매개변수
		MberEntity mber = MberEntity.of(param);
		
		//MberMapper Mock의 selectMber가 반환할 결과
		MberEntity mberEntity = MberEntity.of(param);
		mberEntity.setMberSttusSeCode("03");
		
		//MberMapper의 회원번호로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//0을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mber)).thenReturn(mberEntity);
		
		//회원정보 존재시 BizException을 반환하는지 확인한다.
		assertThrows(BizException.class,
				() -> service.updateMberWdra(param));
	}
	
	/**
	 *<pre>
	 *<b>회원 탈회 테스트 </b>
	 *탈회처리한 회원이 없을 때 예외 발생
	 *</pre>
	 *
	 * method : updateMberWdraFailTest void
	 */
	@Test 
	void updateMberWdraFailTest() {
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo("9999999999")
				.secsnDe(DateTimeUtil.getDateNow())
				.firstRegId("loginID1")
				.firstRegProgmId("updateMberWdraDupTest")
				.lastChngId("loginID1")
				.lastChngProgmId("updateMberWdraDupTest")
				.build();

		//MberMapper Mock의 selectMber에 전달할 매개변수
		MberEntity mber = MberEntity.of(param);

		//MberMapper Mock의 updateMberWdra에 전달할 매개변수 
		MberEntity updateMber = MberEntity.of(param);
		updateMber.setMberSttusSeCode("03");

		//MberMapper의 회원번호로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//0을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mber)).thenReturn(mber);
		
		//MberMapper의 회원정보 등록 메소드가 처리 갯수로 0을 반환하도록 동작을 지정해준다.
		when(mberMapper.updateMberWdra(updateMber)).thenReturn(0);
		
		//저장된 정보가 없을 때 MberCreationException을 반환하는지 확인한다.
		assertThrows(MberWdraException.class,
				() -> service.updateMberWdra(param));
	}
	
	/**
	 *<pre>
	 *<b>회원탈회 </b>
	 *</pre>
	 *
	 * method : updateMberWdraTest void
	 */
	@Test
	void updateMberWdraTest() {
		MberWdraDto param = new MberWdraDto().builder()
				.mberNo("9999999999")
				.secsnDe(DateTimeUtil.getDateNow())
				.firstRegId("loginID1")
				.firstRegProgmId("updateMberWdraDupTest")
				.lastChngId("loginID1")
				.lastChngProgmId("updateMberWdraDupTest")
				.build();

		//MberMapper Mock의 selectMber에 전달할 매개변수
		MberEntity mber = MberEntity.of(param);

		//MberMapper Mock의 updateMberWdra에 전달할 매개변수 
		MberEntity updateMber = MberEntity.of(param);
		updateMber.setMberSttusSeCode("03");

		CudResult cudResult = new CudResult("회원탈회", 1, 0, CudResult.SUCCESS, "9999999999");

		//MberMapper의 회원번호로 저장되어 있는 회원정보의 개수를 조회하는 메소드가 
		//0을 반환하도록 동작을 지정해준다.
		when(mberMapper.selectMber(mber)).thenReturn(mber);
		
		//MberMapper의 회원정보 등록 메소드가 처리 갯수로 0을 반환하도록 동작을 지정해준다.
		when(mberMapper.updateMberWdra(updateMber)).thenReturn(1);
		
		//회원탈회 서비스가 예상되는 결과를 반환하는지 확인한다.
		assertEquals(cudResult, service.updateMberWdra(param));
	}
	
}
