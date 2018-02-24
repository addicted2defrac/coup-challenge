package com.bcgdv;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Tim Richter
 */
public final class CoupSolverTest {
  @Test
  public void testLinearStrategy() {
    testStrategyWithValidInput(CoupSolver.LINEAR_STRATEGY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLinearStrategyInput() {
    testStrategyWithInvalidInput(CoupSolver.LINEAR_STRATEGY);
  }

  @Test
  public void testNSquareStrategy() {
    testStrategyWithValidInput(CoupSolver.NSQUARE_STRATEGY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNSquareStrategyInput() {
    testStrategyWithInvalidInput(CoupSolver.NSQUARE_STRATEGY);
  }

  @Test
  public void testPolynomialStrategy() {
    testStrategyWithValidInput(CoupSolver.POLYNOMIAL_STRATEGY);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPolynomialStrategyInput() {
    testStrategyWithInvalidInput(CoupSolver.POLYNOMIAL_STRATEGY);
  }

  private void testStrategyWithValidInput(final CoupSolver.Strategy strategy) {
    Assert.assertEquals(strategy.solve(new int[]{12}, 12, 5), 0);
    Assert.assertEquals(strategy.solve(new int[]{0}, 12, 5), 0);
    Assert.assertEquals(strategy.solve(new int[]{15, 10}, 12, 5), 3);
    Assert.assertEquals(strategy.solve(new int[]{11, 15, 13}, 9, 5), 7);
    Assert.assertEquals(strategy.solve(new int[]{11, 15, 9}, 9, 15), 2);
  }

  private void testStrategyWithInvalidInput(final CoupSolver.Strategy strategy) {
    //manager capacity not between 1 and 999
    strategy.solve(new int[]{10, 10}, 0, 10);
    strategy.solve(new int[]{10, 10}, 1000, 10);
    strategy.solve(new int[]{10, 10}, -100, 10);

    // engineer capacity not between 1 and 999
    strategy.solve(new int[]{10, 10}, 10, 0);
    strategy.solve(new int[]{10, 10}, 10, 1001);
    strategy.solve(new int[]{10, 10}, -100, 1001);

    // scooter amount in district not between 0 and 1000
    strategy.solve(new int[]{-10}, 10, 10);
    strategy.solve(new int[]{1001}, 10, 10);

    // district amount not between 1 and 100
    strategy.solve(new int[]{}, 10, 10);

    final int[] scooters = new int[101];
    Arrays.fill(scooters, 0);

    strategy.solve(scooters, 10, 10);
  }
}
