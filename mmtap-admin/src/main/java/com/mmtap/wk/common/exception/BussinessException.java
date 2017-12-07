package com.mmtap.wk.common.exception;

import com.mmtap.wk.core.exception.GunsException;

/**
 * @author imkzp.com
 * @Description 业务异常的封装
 * @date 2016年11月12日 下午5:05:10
 */
public class BussinessException extends GunsException {

	public BussinessException(BizExceptionEnum bizExceptionEnum) {
		super(bizExceptionEnum.getCode(), bizExceptionEnum.getMessage(), bizExceptionEnum.getUrlPath());
	}
}
