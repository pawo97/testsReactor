package edu.iis.mto.testreactor.exc2;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

public class WashingMachineTest {

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

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

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

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        assertEquals(laundryStatus.getResult(), Result.FAILURE);

    }

    @Test
    public void checkIfProgramTimeIsTheSame() {
        kilogramsOfLaundry = 5;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(false)
                                                   .build();

        laundryBatch = LaundryBatch.builder()
                                   .withType(Material.COTTON)
                                   .withWeightKg(kilogramsOfLaundry)
                                   .build();

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        assertEquals(laundryStatus.getRunnedProgram()
                                  .getTimeInMinutes(),
                programConfiguration.getProgram()
                                    .getTimeInMinutes());

    }

    @Test
    public void checkIfProgramsAreTheSame() {
        kilogramsOfLaundry = 5;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(false)
                                                   .build();

        laundryBatch = LaundryBatch.builder()
                                   .withType(Material.COTTON)
                                   .withWeightKg(kilogramsOfLaundry)
                                   .build();

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        assertEquals(laundryStatus.getRunnedProgram(), programConfiguration.getProgram());
    }

    @Test(expected = NullPointerException.class)
    public void checkIfProgramsTypeTheSame() {
        kilogramsOfLaundry = 5;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(false)
                                                   .build();

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

        // verify(dirtDetector.detectDirtDegree(laundryBatch), times(1));
    }
}
