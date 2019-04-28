package com.opentext.curator;

@FunctionalInterface
public interface CustomProcessor<T> {

	void process(T t) throws Exception;
}
