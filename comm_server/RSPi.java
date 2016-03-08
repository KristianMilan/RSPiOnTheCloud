
public class RSPi {
	final int number_of_pins=3;
	boolean[] pinState;
	
	public RSPi(){
		pinState= new boolean[number_of_pins];
		for(int i=0; i<number_of_pins ; i++)
			pinState[i]=false;
	}
	
	public boolean getPinState(int pin_number){
		if(pin_number<number_of_pins){
			return pinState[pin_number];
		}
		return false;
	}
	
	public void setPinState(int pin_number, boolean state){
		if(pin_number<number_of_pins){
			pinState[pin_number]=state;
		}
	}
			
}
