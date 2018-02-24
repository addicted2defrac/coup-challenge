package com.bcgdv;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class CoupSpringApplication {

  public static void main(final String[] args) {
    SpringApplication.run(CoupSpringApplication.class);
  }

  private static class CoupRequest {
    private int[] scooters;
    @JsonProperty("C")
    private int managerCap;
    @JsonProperty("P")
    private int engineerCap;

    public int[] getScooters() {
      return scooters;
    }

    public void setScooters(final int[] scooters) {
      this.scooters = scooters;
    }

    public int getManagerCap() {
      return managerCap;
    }

    public void setManagerCap(final int managerCap) {
      this.managerCap = managerCap;
    }

    public int getEngineerCap() {
      return engineerCap;
    }

    public void setEngineerCap(final int engineerCap) {
      this.engineerCap = engineerCap;
    }

  }

  private static class CoupResponse {
    @JsonProperty("fleet_engineers")
    private int fleetEngineer;

    private CoupResponse(final int fleetEngineer) {
      this.fleetEngineer = fleetEngineer;
    }

    public int getFleetEngineer() {
      return fleetEngineer;
    }

    public void setFleetEngineer(final int fleetEngineer) {
      this.fleetEngineer = fleetEngineer;
    }
  }

  @Controller
  public class CoupController {

    @RequestMapping(value = "/coup")
    @ResponseBody
    CoupResponse coup(@RequestBody CoupRequest input) {
      final int engineers =
          CoupSolver.LINEAR_STRATEGY.solve(input.scooters, input.managerCap, input.engineerCap);

      return new CoupResponse(engineers);
    }
  }
}
