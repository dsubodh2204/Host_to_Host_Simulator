package com.cvv;

import com.hsm.ParseHsmReqRsp;

public class Cvv extends ParseHsmReqRsp {
	private ParseThalesCvvRsp parobj;

	public Cvv() {
		super();
		this.parobj = new ParseThalesCvvRsp();

	}

	private String Command;

	// method to get cvv1, this method requires CVK,Card_no,Expiry,SRC
	// (CustomParam1,CustomParam2 are optional)
	// this method returns response code|cvv
	// *here if the respective values is available then it will populate otherwise
	// empty sting will come

	public String getCVV1(String CVK, String Card_no, String Expiry, String SRC, String CustomParam1,
			String CustomParam2) {

		this.Command = Constants.MESSAGE_HEADER + Constants.CW + CVK + Card_no + Constants.DELIMETER + Expiry + SRC;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.CX);
		return this.parobj.getErr_code() + "|" + this.parobj.getCvv_code();

	}

	// method to get cvv1, this method requires CVK,Card_no,Expiry,SRC
	// (CustomParam1,CustomParam2 are optional)

	public String getCVV2(String CVK, String Card_no, String Expiry, String CustomParam1, String CustomParam2) {

		this.Command = Constants.MESSAGE_HEADER + Constants.CW + CVK + Card_no + Constants.DELIMETER + Expiry
				+ Constants.CVV2;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.CX);
		return this.parobj.getErr_code() + "|" + this.parobj.getCvv_code();
	}

	// method to get cvv1, this method requires CVK,Card_no,Expiry,SRC
	// (CustomParam1,CustomParam2 are optional)

	public String getICVV(String CVK, String Card_no, String Expiry, String CustomParam1, String CustomParam2) {

		this.Command = Constants.MESSAGE_HEADER + Constants.CW + CVK + Card_no + Constants.DELIMETER + Expiry
				+ Constants.ICVV;

		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.CX);
		return this.parobj.getErr_code() + "|" + this.parobj.getCvv_code();
	}

	// method to verify cvv, this method requires CVK,Card_no,Expiry
	// (CustomParam1,CustomParam2 are optional)

	public String verifyCVV(String CVK, String Card_no, String Expiry, String CustomParam1, String CustomParam2) {

		this.Command = Constants.MESSAGE_HEADER + Constants.CY + CVK + Card_no + Constants.DELIMETER + Expiry
				+ Constants.ICVV;

		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.CZ);
		return this.parobj.getErr_code() + "|";
	}

	private void parseResponseFromHsm(String ThalesRsp, String RspCmdCode) {

		String response_code = ThalesRsp.substring(6, 8);
		String rsp_cmd = ThalesRsp.substring(4, 6);

		if ((response_code.equals("00")) && (rsp_cmd.equalsIgnoreCase(RspCmdCode))) {

			if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, ThalesRsp.substring(8, 11));
			}
			if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() == 6)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
			}

		} else {
			this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
		}

	}

	public static class Constants {
		public static final String CW = "CW";
		public static final String CX = "CX";
		public static final String CY = "CY";
		public static final String CZ = "CZ";
		public static final String CVV2 = "000";
		public static final String ICVV = "999";
		public static final String DELIMETER = ";";
		public static final String MESSAGE_HEADER = "0000";
	}

}
