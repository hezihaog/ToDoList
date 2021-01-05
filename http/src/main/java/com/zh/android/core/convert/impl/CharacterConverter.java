package com.zh.android.core.convert.impl;

import com.zh.android.core.convert.AbstractConverter;
import com.zh.android.core.util.BooleanUtil;
import com.zh.android.core.util.StrUtil;

/**
 * 字符转换器
 * 
 * @author Looly
 *
 */
public class CharacterConverter extends AbstractConverter<Character> {
	private static final long serialVersionUID = 1L;

	@Override
	protected Character convertInternal(Object value) {
		if (char.class == value.getClass()) {
			return Character.valueOf((char) value);
		} else if (value instanceof Boolean) {
			return BooleanUtil.toCharacter((Boolean) value);
		} else if (boolean.class == value.getClass()) {
			return BooleanUtil.toCharacter((boolean) value);
		} else {
			final String valueStr = convertToStr(value);
			if (StrUtil.isNotBlank(valueStr)) {
				return Character.valueOf(valueStr.charAt(0));
			}
		}
		return null;
	}

}
