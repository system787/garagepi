package system787.garagepi.pi4j;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import system787.garagepi.pi4j.exception.StateNotReadyException;

import java.time.Instant;

public class Pi4JController {

    // Pi4j
    private final GpioController mGpio = GpioFactory.getInstance();

    // Input -- SecoLarm Reed Switch
    // If there is input into this pin, the garage door is open
    private GpioPinDigitalInput mSecoLarmInput;
    private PinState mSecoLarmState;

    // Output -- Garage Door Relay
    private GpioPinDigitalOutput mRelayOutput;

    // Synchronization
    private Object delayLock;
    private Instant openRelayInstant;

    // Constructor
    public Pi4JController() {
        this.delayLock = new Object();

        // Input -- SecoLarm
        this.mSecoLarmInput = mGpio.provisionDigitalInputPin(
                RaspiPin.GPIO_02,
                "SecoLarmInput");

        mSecoLarmInput.addListener(new GpioUsageListener() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                handleSecoLarmInput(event);
            }
        });

        // Output -- Relay switch
        this.mRelayOutput = mGpio.provisionDigitalOutputPin(
                RaspiPin.GPIO_04,
                "RelaySwitch",
                PinState.LOW);
    }

    // Methods

    /*
        SecoLarm - Garage door state
    */
    private void handleSecoLarmInput(GpioPinDigitalStateChangeEvent event) {
        mSecoLarmState = event.getState();

        if (mSecoLarmState.isHigh()) {
            // TODO: Send alert that garage door is open
        }
    }

    /**
     * isGarageDoorOpen
     * Manually check if garage door is open
     *
     * @return true if open, false if closed
     */
    public boolean isGarageDoorOpen() {
        return mSecoLarmState.isHigh();
    }

    /*
        Relay - Open/Close garage door
    */

    /**
     * openRelay
     * Sends the command to the relay to trigger the opening/closing of the garage door
     *
     * @return 1 if logic was completed, not necessarily if the garage door opener actually received input
     * @throws StateNotReadyException if called within 30 sec of last attempt
     */
    public synchronized int openRelay() throws StateNotReadyException {

        if (openRelayInstant == null || Instant.now().isAfter(openRelayInstant.plusSeconds(30))) {
            synchronized (delayLock) {
                openRelayInstant = Instant.now();
                mRelayOutput.pulse(200);
                return 1;
            }
        }

        throw new StateNotReadyException("Tried to change relay state within 30 sec of last attempt");
    }


}
