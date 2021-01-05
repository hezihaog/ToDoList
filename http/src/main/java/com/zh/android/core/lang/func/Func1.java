package com.zh.android.core.lang.func;

/**
 * 只有一个参数的函数对象<br>
 * 接口灵感来自于<a href="http://actframework.org/">ActFramework</a><br>
 * 一个函数接口代表一个一个函数，用于包装一个函数为对象<br>
 * 在JDK8之前，Java的函数并不能作为参数传递，也不能作为返回值存在，此接口用于将一个函数包装成为一个对象，从而传递对象
 * 
 * @author Looly
 *
 * @param <P> 参数类型
 * @param <R> 返回值类型
 * @since 4.2.2
 */
public interface Func1<P, R> {
	
	/**
	 * 执行函数
	 * 
	 * @param parameter 参数
	 * @return 函数执行结果
	 * @throws Exception 自定义异常
	 */
	R call(P parameter) throws Exception;
}
