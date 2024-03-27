package org.optima.kit;

@FunctionalInterface
public interface IForEachApplyFunction<T>{
    T call(T element);
}
