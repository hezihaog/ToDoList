package com.zh.android.core.convert.impl;

import com.zh.android.core.convert.AbstractConverter;
import com.zh.android.core.util.BooleanUtil;

/**
 * 波尔转换器
 * @author Looly
 *
 */
public class BooleanConverter extends AbstractConverter<Boolean>{
	private static final long serialVersionUID = 1L;

	@Override
	protected Boolean convertInternal(Object value) {
		if(boolean.class == value.getClass()){
			return Boolean.valueOf((boolean)value);
		}
		String valueStr = convertToStr(value);
		return Boolean.valueOf(BooleanUtil.toBoolean(valueStr));
	}

}
