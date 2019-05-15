package edu.iis.mto.testreactor.exc2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

public class WashingMachineTest {

    @Before
    public void itCompiles() {
        assertThat(true, Matchers.equalTo(true));
    }

    DirtDetector dirtDetector = mock(DirtDetector.class);
    Engine engine = mock(Engine.class);
    WaterPump waterPump = mock(WaterPump.class);
    LaundryBatch laundryBatch;
    LaundryStatus laundryStatus;
    Percentage percentage = new Percentage(20);
    ProgramConfiguration programConfiguration;
    WashingMachine washingMachine;
    int timeInMinutes = 20;
    int kilogramsOfLaundry;

    @Test
    public void checkIfLaundryIsSuccess() {
        kilogramsOfLaundry = 5;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(true)
                                                   .build();

        laundryBatch = LaundryBatch.builder()
                                   .withType(Material.COTTON)
                                   .withWeightKg(kilogramsOfLaundry)
                                   .build();

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        assertEquals(laundryStatus.getResult(), Result.SUCCESS);
    }

    @Test
    public void checkIfLaundryIsTooHeavy() {
        kilogramsOfLaundry = 20;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(true)
                                                   .build();

        laundryBatch = LaundryBatch.builder()
                                   .withType(Material.COTTON)
                                   .withWeightKg(kilogramsOfLaundry)
                                   .build();

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        assertEquals(laundryStatus.getResult(), Result.FAILURE);
        // assertEquals(laundryStatus.getRunnedProgram(), programConfiguration);
    }

}
