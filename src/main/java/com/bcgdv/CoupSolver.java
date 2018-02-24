package com.bcgdv;

/**
 * @author Tim Richter
 */
public final class CoupSolver {

  /**
   * You are given a int[] scooters, which has as many elements as there are
   * districts in Berlin that Coup operates in. For each i, scooters[i] is the
   * number of scooters in that district (0-based index).
   * During a work day, scooters are maintained (batteries changed, cleaned,
   * checked for damages) by the Fleet Manager (FM) and possibly other Fleet
   * Engineers (FEs). Each FE, as well as the FM, can only maintain scooters in
   * one district. Additionally, there is a limit on how many scooters a single
   * FE may supervise: the FM is able to maintain up to C scooters, and a FE is
   * able to maintain up to P scooters. Each scooter has to be maintained by some FE or the FM.
   */
  interface Strategy {
    /**
     * Strategy on how to solve Coup problem.
     * Find the minimum number of FEs which are required to help the FM so that every scooter in
     * each district of Berlin is maintained. Note that you may choose which district the FM should
     * go to.
     *
     * @param scooters    array of number of scooters in each district
     * @param managerCap  amount of scooters a manager can maintain
     * @param engineerCap amount of scooters each engineer can maintain
     * @return minimum number of engineers required for maintaining given scooters
     * @throws IllegalArgumentException if scooters length not between 1 and 100 or manager capacity not
     *                                  between 1 and 999 or engineer capacity not between 1 and 1000
     */
    int solve(final int[] scooters, final int managerCap, final int engineerCap);
  }

  /**
   * Base strategy provides functionality to validate input parameters
   */
  static abstract class BaseStrategy implements Strategy {
    void validateInput(final int[] scooters, final int managerCap, final int engineerCap) {
      if (scooters.length < 1 || managerCap > 100)
        throw new IllegalArgumentException("Illegal scooter district amount; expected amount between 1 and 100");

      if (managerCap < 1 || managerCap > 999)
        throw new IllegalArgumentException("Illegal manager capacity; expected value between 1 and 999");

      if (engineerCap < 1 || engineerCap > 1000)
        throw new IllegalArgumentException("Illegal engineer capacity; expected value between 1 and 1000");
    }

    void validateScooterAmount(final int[] scooters, final int index) {
      if (index >= scooters.length) return;

      if (scooters[index] < 0 || scooters[index] > 1000)
        throw new IllegalArgumentException("Illegal scooter amount; expected amount between 0 and 1000");
    }

    int requiredEngineers(final int scooters, final int engineerCap) {
      if (scooters <= 0) return 0;

      final int result = scooters / engineerCap;

      return scooters % engineerCap > 0 ? result + 1 : result;
    }
  }

  /**
   * Bruteforce solution uses polynomial recursive algorithm with runtime O(2^N)
   */
  public static final Strategy POLYNOMIAL_STRATEGY = new BaseStrategy() {
    public int solve(final int[] scooters, final int managerCap, final int engineerCap) {
      validateInput(scooters, managerCap, engineerCap);

      return solve(scooters, managerCap, engineerCap, 0, 0);
    }

    private int solve(final int[] scooters,
                      final int managerCap,
                      final int engineerCap,
                      final int engineerCount,
                      final int index) {
      if (index == scooters.length)
        return engineerCount;

      if (scooters[index] <= 0) {
        validateScooterAmount(scooters, index + 1);

        return solve(scooters, managerCap, engineerCap, engineerCount, index + 1);
      }

      int result = Integer.MAX_VALUE;

      if (managerCap > 0) {
        scooters[index] -= managerCap;
        result = solve(scooters, 0, engineerCap, engineerCount, index);
        scooters[index] += managerCap;
      }

      scooters[index] -= engineerCap;
      result = Math.min(solve(scooters, managerCap, engineerCap, engineerCount + 1, index), result);
      scooters[index] += engineerCap;

      return result;
    }
  };

  /**
   * Linear solution runs in O(N)
   */
  public static final Strategy LINEAR_STRATEGY = new BaseStrategy() {
    public int solve(final int[] scooters, final int managerCap, final int engineerCap) {
      validateInput(scooters, managerCap, engineerCap);

      int maxEngineers = 0;

      for (int i = 0; i < scooters.length; ++i) {
        validateScooterAmount(scooters, i);

        maxEngineers += requiredEngineers(scooters[i], engineerCap);
      }

      int result = maxEngineers;

      for (int i = 0; i < scooters.length; ++i) {
        int min = requiredEngineers(scooters[i] - managerCap, engineerCap);
        int max = requiredEngineers(scooters[i], engineerCap);

        result = Math.min(result, maxEngineers - (max - min));
      }

      return result;
    }
  };

  /**
   * Bruteforce solution uses nested loops in runtime O(N^2)
   */
  public static final Strategy NSQUARE_STRATEGY = new BaseStrategy() {
    public int solve(final int[] scooters, final int managerCap, final int engineerCap) {
      validateInput(scooters, managerCap, engineerCap);

      int result = Integer.MAX_VALUE;

      for (int i = 0; i < scooters.length; ++i) {
        validateScooterAmount(scooters, i);

        int engineers = 0;
        scooters[i] -= managerCap;

        for (int j = 0; j < scooters.length; ++j) {
          engineers += requiredEngineers(scooters[j], engineerCap);
        }

        scooters[i] += managerCap;
        result = Math.min(result, engineers);
      }
      return result;
    }
  };
}
