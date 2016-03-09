
public class RSPi {
	final int number_of_pins=3;
	boolean[] pinState;
	boolean isOnline;
	int dbId;
	String id;
	
	public RSPi(String id,int dbId){
		pinState= new boolean[number_of_pins];
		for(int i=0; i<number_of_pins ; i++)
			pinState[i]=false;
		setIsOnline(false);
		setId(id);
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

	public boolean getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(boolean isOnline) {
		this.isOnline = isOnline;
	}
	
	public int getDbId() {
		return dbId;
	}

	public void setDbId(int dbId) {
		this.dbId = dbId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
			
}
