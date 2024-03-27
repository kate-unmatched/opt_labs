package org.optima.kit;

@FunctionalInterface
public interface IForEnumerateApplyFunction<T>{
    T call(int elementIndex, T element);
}
