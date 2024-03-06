package org.visual.component.algebradata;

public interface AlgebraData<T extends AlgebraData<T>> {
  T plus(T other);

  T minus(T other);

  T multiply(double v);

  T dividedBy(double v);
}
