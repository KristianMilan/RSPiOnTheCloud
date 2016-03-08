import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;

public class HardwareCtrl {
	
	final int numOfPinsUsed=3;
	final GpioController gpio;
	final GpioPinDigitalOutput[] pin;
	final boolean[] pinStateInfo;
	
	public HardwareCtrl(){
		 // create gpio controller
		gpio=GpioFactory.getInstance();
		
		pin = new GpioPinDigitalOutput[numOfPinsUsed];
		pinStateInfo = new boolean[numOfPinsUsed];
		// provision gpio pins as an output pins and turn off
		pin[0]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "Pin_0", PinState.LOW);
		pinStateInfo[0]=false;
		pin[1] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Pin_1", PinState.LOW);
		pinStateInfo[1]=false;
		pin[2] = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Pin_2", PinState.LOW);
		pinStateInfo[2]=false;
		
		
		// set shutdown state for these pins
		pin[0].setShutdownOptions(true, PinState.LOW);
		pin[1].setShutdownOptions(true, PinState.LOW);
		pin[2].setShutdownOptions(true, PinState.LOW);
	}
	
	public void setPinState(int pinNo, boolean state){
		if(pinNo<numOfPinsUsed){
			if(state){
				pin[pinNo].high(); //switch on
				pinStateInfo[pinNo]=true;
			}
			else {
				pin[pinNo].low(); //switch off
				pinStateInfo[pinNo]=false;
			}
		}
	}
	
	public boolean getPinStateInfo(int pinNo){
		return pinStateInfo[pinNo];
	}
}
