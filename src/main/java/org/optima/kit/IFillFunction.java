package org.optima.kit;

@FunctionalInterface
public interface IFillFunction<T>{
    T call(int index);
}