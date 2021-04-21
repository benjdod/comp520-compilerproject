package miniJava.CodeGenerator;

import mJAM.Machine.Reg;

public class UnknownValue extends RuntimeEntity {	
	public UnknownValue(Address address) {
		this.address = address;
		this.size = 1;
	}
	
	public UnknownValue(Reg reg, int offset) {
		this.address = new Address(reg,offset);
		this.size = 1;
	}
}
