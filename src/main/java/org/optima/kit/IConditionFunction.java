package org.optima.kit;

@FunctionalInterface
public interface IConditionFunction<T> {
    boolean call(T element);
}
