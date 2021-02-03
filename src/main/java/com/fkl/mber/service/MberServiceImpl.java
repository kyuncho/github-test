/*
 * Copyright 2020-2020 한국후지쯔주식회사 All rights reserved.
 * 
 * This software is the confidential and proprietary information 한국후지쯔주식회사.
 * You shall not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into
 * with 한국후지쯔주식회사. 
 */
package com.fkl.mber.service;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fkl.common.exception.BizException;
import com.fkl.common.exception.ErrorCode;
import com.fkl.common.model.CudResult;
import com.fkl.common.utils.Constants;
import com.fkl.common.utils.DateTimeUtil;
import com.fkl.mber.exception.MberCreationException;
import com.fkl.mber.exception.MberNotFoundException;
import com.fkl.mber.exception.MberWdraException;
import com.fkl.mber.model.MberDto;
import com.fkl.mber.model.MberEntity;
import com.fkl.mber.model.MberSearchDto;
import com.fkl.mber.model.MberWdraDto;
import com.fkl.mber.repository.MberMapper;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
@Service
@Transactional
public class MberServiceImpl implements MberService {

	@Resource
	private MberMapper mberMapper;
	
	@Autowired
    RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public List<MberDto> selectMbers(MberSearchDto param) {
		
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMbers :: input :: {}", param);
		}

		//Dto를 Entity로 변환
		MberEntity mber = MberEntity.of(param) ;

		List<MberEntity> entityList = this.mberMapper.selectMbers(mber);
		if (entityList.isEmpty()) throw new MberNotFoundException(ErrorCode.NO_EXISTS_DATA);
		
		//Entity List를 Dto List로 변환
		List<MberDto> dtoList = entityList.stream()
									.map( entity -> MberDto.of(entity))
									.collect(Collectors.toList());

		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMbers :: output :: {}", dtoList);
		}
		
		return dtoList;
	}
	
	@Override
	public List<MberDto> selectMbersByRedis(MberSearchDto param) {
		
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMbers :: input :: {}", param);
		}

		//Dto를 Entity로 변환
		MberEntity mber = MberEntity.of(param) ;

		List<MberEntity> entityList;
		
		ListOperations<String, Object> listOps = redisTemplate.opsForList();
		Long index = listOps.size("selectMbersByRedis");
		if(index >0){
			entityList = (List<MberEntity>) redisTemplate.opsForList().index("selectMbersByRedis", 0);
        }else{
        	entityList = this.mberMapper.selectMbers(mber);
            listOps.leftPush("selectMbersByRedis", entityList);
            redisTemplate.expire("selectMbersByRedis", 60, TimeUnit.SECONDS);
        }
		
		if (entityList.isEmpty()) throw new MberNotFoundException(ErrorCode.NO_EXISTS_DATA);
		
		//Entity List를 Dto List로 변환
		List<MberDto> dtoList = entityList.stream()
									.map( entity -> MberDto.of(entity))
									.collect(Collectors.toList());

		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMbers :: output :: {}", dtoList);
		}
		
		return dtoList;
	}	

	@Override
	public MberDto selectMber(MberSearchDto param) {
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMber :: input :: {}", param);
		}

		//Dto를 Entity로 변환
		MberEntity mber = MberEntity.of(param) ;

		MberEntity mberEntity = this.mberMapper.selectMber(mber);
		if (mberEntity == null) throw new MberNotFoundException(ErrorCode.NO_EXISTS_DATA);
		
		if ( log.isDebugEnabled() ) {
			log.debug("=====:::::>>>>> selectMber :: output :: {}", mberEntity);
		}
		
		return MberDto.of(mberEntity);
	}

	@Override
	public CudResult insertMber(MberDto mberDto) {
		String name = "회원등록";
		int cnt = 0;
		int err = 0;
		String msg = "";
		
		mberDto.setMberSttusSeCode("01");

		//Dto를 Entity로 변환
		MberEntity mber = MberEntity.of(mberDto);
		log.debug(mber.toString());
		
		MberEntity mberEntity = this.mberMapper.selectMber(mber);
		if (mberEntity != null) {
			throw new BizException("로그인 아이디가 중복됩니다.", ErrorCode.INVALID_INPUT_VALUE);
		}
		
		cnt = this.mberMapper.insertMber(mber);
		
		if (cnt == 0) {
			throw new MberCreationException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		msg = CudResult.SUCCESS;
		log.debug("=====:::::>>>>> " + mber.getMberNo());
		return new CudResult(name, cnt, err, msg, mber.getMberNo());
	}

	@Override
	public CudResult updateMberWdra(MberWdraDto mberWdraDto) {
		String name = "회원탈회";
		int cnt = 0;
		int err = 0;
		String msg = "";

		mberWdraDto.setSecsnDe(LocalDate.now()
				.format(Constants.DateTimeFormat.FORMATTER_YYYYMMDD));	// 탈퇴일자

		//Dto를 Entity로 변환
		MberEntity mber = MberEntity.of(mberWdraDto);
		
		MberEntity mberEntity = this.mberMapper.selectMber(mber);
		if (mberEntity == null) {
			throw new MberNotFoundException(ErrorCode.NO_EXISTS_DATA);
		} else {
			if ("03".equals(mberEntity.getMberSttusSeCode()) ) {
				throw new BizException("이미 탈회한 회원입니다.", ErrorCode.INVALID_INPUT_VALUE);
			}
		}
		
		mber.setMberSttusSeCode("03");		// 회원상태코드
		
		if (mber.getSecsnDe().isEmpty()) {
			mber.setSecsnDe(DateTimeUtil.getDateNow());
		}

		cnt = this.mberMapper.updateMberWdra(mber);
		
		
		if (cnt == 0) {
			throw new MberWdraException(ErrorCode.INTERNAL_SERVER_ERROR);
		}
		
		msg = CudResult.SUCCESS;
		return new CudResult(name, cnt, err, msg, mber.getMberNo());
	}


}
