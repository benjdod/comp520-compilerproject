package miniJava.CodeGenerator;

import mJAM.Machine.Reg;

public class UnknownValue extends RuntimeEntity {	
	public UnknownValue(Address address) {
		this.address = address;
	}
	
	public UnknownValue(Reg reg, int offset) {
		this.address = new Address(reg,offset);
	}
}
