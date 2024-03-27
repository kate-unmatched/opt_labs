package org.optima.kit;

@FunctionalInterface
public interface IReduceFunction<T>{
    T call(T accumulator, T value);
}
