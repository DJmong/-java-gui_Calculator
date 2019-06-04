package Calculator;

import java.awt.Color;

public class Calculator_calculate {
	static String pre = ""; // previous operator
	static String pre_input = "opr";
	static double buf_pre = 0; // previous buf
	static long buf_now = 0; // inserting buf
	static long buf_dot = 0; // dot buf
	static String base = "DEC"; // DEC, HEX
	static boolean dot = false; // dot on/off
	static int dot_c = 0; // dot_count

	protected void calculate(String str) {
		switch (str) {
		//number
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			input_num(str);
			break;
		// hex num
		case "a":
		case "b":
		case "c":
		case "d":
		case "e":
		case "f":
			str=str.toUpperCase();
		case "A":
		case "B":
		case "C":
		case "D":
		case "E":
		case "F":
			input_hexnum(str);
			break;
		case ".":
			if(base == "DEC")
				dot = !dot;
			break;
		// operator
		case "\n":
			str="=";
		case "=":
		case "+":
		case "-":
		case "*":
		case "/":
		case "¡À":
		case "¡¿":
		case "%":
			opr_cal(str);
			break;
		// 16Áø¼ö opr
		case "^":
		case "|":
		case "&":
			opr_hexcal(str);
			break;
		case "<<":
		case ">>":
			opr_bit(str);
			break;
		case "x©÷":
			opr_pow();
			break;
		case "1/x":
			opr_xdiv();
			break;
		case "CLR":
			CLR();
			break;
		case "CE":
			CE();
			break;
		case "¡¾":
			buf_now*=(-1);
			buf_dot*=(-1);
			break;
		case "¡ç":
			Erase();
			break;
		case "HEX":
			base = "HEX";
			pre = "";
			dot=false;
			break;
		case "DEC":
			base = "DEC";
			pre = "";
			break;

		}

	}

	/*
	 * method for insert number
	 */

	private void input_num(String str) {
		double n;
		pre_input = "num";
		n = (double) str.charAt(0) - 48;
		if (base == "HEX") {
			// hex does not calculate dot
			buf_now *= 16;
			buf_now += n;
		} else {
			if (dot == false) {

				buf_now *= 10;
				buf_now += n;
			} else {
				dot_count();
				buf_dot*=10;
				buf_dot+=n;
			}
		}

	}

	private void input_hexnum(String str) {
		if (base == "HEX") {
			double n;
			n = (double) str.charAt(0) - 55;
			buf_now *= 16;
			buf_now += n;
			pre_input = "num";
		}
	}

	/*
	 * Methods for operator
	 */
	private void opr_cal(String str) {
		if (pre_input != "opr") {
			switch (pre) {
			case "+":
				buf_pre += buf_now+dot_cal();
				break;
			case "-":
				buf_pre -= buf_now+dot_cal();
				break;
			case "/":
			case "¡À":
				buf_pre /= buf_now+dot_cal();
				break;
			case "*":
			case "¡¿":
				buf_pre *= buf_now+dot_cal();
				break;
			case "%":
				buf_pre %= buf_now+dot_cal();
				break;
			case "":
				buf_pre = buf_now+dot_cal();
				break;
			}
			if (pre != "=") 
				CE();
			
				
		}
		pre_input = "opr";
		if (str != "=")
			pre = str;
		else
			pre = "";
	}

	private void opr_hexcal(String str) {
		//using Only HEX Mode
		if (base == "HEX") {
			//if previous insert was operator, not calculate just change the operator
			if (pre_input != "opr") {
				long buf1 = (long) buf_pre;
				long buf2 = (long) buf_now;

				switch (pre) {
				case "^":
					buf1 ^= buf2;
					break;
				case "|":
					buf1 |= buf2;
					break;
				case "&":
					buf1 &= buf2;
					break;
				case "":
					buf_pre = buf_now;
					break;
				}
				if (pre != "")
					buf_pre = (double) buf1;
				CE();
				pre = str;
				
			}
			if (str != "=")
				pre = str;
			else
				pre = "";
			pre_input = "opr";
			
		}
	}

	private void opr_bit(String str) {
		if (base == "HEX") {
			if (str == "<<") {
				buf_now = (buf_now << 1);
			} else if (str == ">>") {
				buf_now = (buf_now >> 1);
			} else {
				System.out.println("Error");
			}
		}

	}
	
	private void opr_xdiv(){
		double n;
		int count=0;
		n=buf_now+dot_cal();
		if(n!=0) {
			n=1/n;
			buf_now=(long)n;
			n%=1;
			buf_dot=0;
			while((n%1)!=0) {
				count++;
				n*=10;
				
			}
			buf_dot=(long)n;
			dot_c=count;
				
		}
	}
	
	private void opr_pow() {
		buf_now=(long)Math.pow((double)buf_now, 2);
		buf_dot=(long)Math.pow((double)buf_dot, 2);
		dot_c=(int)Math.pow(dot_c, 2);
	}
	/*
	 * Methods for Erase
	 */
	private void CLR() {
		dot_c = 0;
		dot = false;
		buf_pre = 0;
		buf_now = 0;
		pre = "";
		pre_input = "opr";
		buf_dot = 0;
	}

	private void CE() {
		buf_now = 0;
		buf_dot=0;
		dot_clear();
	}

	private void Erase() {
		if(base == "DEC") {
			if(buf_dot!=0) {
				buf_dot/=10;
				dot_c--;
			}
			else {
				buf_now/=10;
			}
		}
		else if(base =="HEX"){
			buf_now/=16;
		}
		
	}
	/*
	Methods dot_calculate
	 */
	private static void dot_count() {
		dot_c++;
	}

	private void dot_clear() {
		dot_c = 0;
		dot = false;
	}

	private double dot_cal() {
		double n;
		n=buf_dot*Math.pow(0.1, dot_c);
		return n;
	}
	/*
	 * Methods for print
	 */

	protected String print_pre() {

		String result;
		if (base == "HEX") {
			int n = (int) buf_pre;
			result = "0x" + Integer.toHexString(n).toUpperCase() + " " + pre;
			result.toUpperCase();
			return result;
		}

		else if (base == "DEC") {
			double n = buf_pre;
			result = n + " " + pre;
			return result;
		} else {
			return "Error";
		}
	}

	protected String print_now() {

		String result;
		if (base == "HEX") {
			int n = (int) buf_now;
			result = "0x" + Integer.toHexString(n).toUpperCase();
			result.toUpperCase();
			return result;
		}

		else if (base == "DEC") {
			double n = buf_now+dot_cal();
			result = "" + n;
			return result;
		} else {
			return "Error";
		}
	}

	protected String print_type() {
		return base;
	}
	
	protected Color type_color() {
		if(dot==true)
			return Color.yellow;
		else
			return Color.white;
	}
	
	
}
