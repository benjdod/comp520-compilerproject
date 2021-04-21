package miniJava.CodeGenerator;

import java.util.ArrayList;
import mJAM.Machine.*;

public class MethodEntity extends RuntimeEntity {
	public ArrayList<Integer> callers;
	public boolean ismade;
	
	public MethodEntity(Reg reg, int offset) {
		this.address = new Address(reg,offset);
		callers = new ArrayList<Integer>();
		ismade = false;
	}
}
