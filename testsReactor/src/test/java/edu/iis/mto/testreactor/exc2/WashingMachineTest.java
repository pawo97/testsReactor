package edu.iis.mto.testreactor.exc2;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
        kilogramsOfLaundry = 3;
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
        kilogramsOfLaundry = 4;
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
    public void checkIfLaundryCantStart() {
        kilogramsOfLaundry = 5;
        washingMachine = new WashingMachine(dirtDetector, engine, waterPump);

        programConfiguration = ProgramConfiguration.builder()
                                                   .withProgram(Program.SHORT)
                                                   .withSpin(false)
                                                   .build();

        when(dirtDetector.detectDirtDegree(laundryBatch)).thenReturn(percentage);

        laundryStatus = washingMachine.start(laundryBatch, programConfiguration);

    }

    @Test
    public void verifyIfWaterPompLaunchOnce() {
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

        verify(waterPump, times(1)).pour(kilogramsOfLaundry);

    }

    @Test
    public void verifyIfEngineLaunchOnce() {
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

        // verify(dirtDetector, times(1)).detectDirtDegree(laundryBatch);
        verify(engine).runWashing(timeInMinutes);

    }
}
