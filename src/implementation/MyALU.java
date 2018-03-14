
package implementation;

import utilitytypes.EnumOpcode;

/**
 * The code that implements the ALU has been separates out into a static
 * method in its own class.  However, this is just a design choice, and you
 * are not required to do this.
 * 
 * @author 
 */
public class MyALU {
    static int execute(EnumOpcode opcode, int input1, int input2, int oper0) {
        int result = 0;
        
        switch(opcode)
        {
        case ADD:
			result = (input1 + input2);
			break;
		case SUB:
			result = (input1 - input2);
			break;
		case MOVC:
			result = (input1);
			break;
		case MULS:
			result = (input1*input2);
			break;
		case AND:
			result = (input1 & input2);
			break;
		case OR:
			result = (input1 | input2);
			break;
		case XOR:
			result = (input1 ^ input2);
			break;
		case LOAD:
			if(opcode == null){
				result = (input1 + input2);
			} 
			else
			{								
				result = (input1+ oper0);
			}
		
		case SHL:
			result = input1 << input2;
			break;
		case LSR:
			result = (input1 >> input2);
              break;
		case HALT:
			break;
	
        }
        
        return result;
    }   
    }


