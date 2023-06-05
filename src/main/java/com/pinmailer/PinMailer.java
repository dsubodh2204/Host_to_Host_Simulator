package com.pinmailer;

import com.hsm.ParseHsmReqRsp;
import com.pin.Pin.Constants;

public class PinMailer {
	private String Command;
	private ParseThalesPinMailerRsp parobj;

	public PinMailer() {
		super();
		this.parobj = new ParseThalesPinMailerRsp();

	}

	public String load_Format_Data(String Data, String Pin_len, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.PA + Data;

		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.PB);

		return this.parobj.getErr_code() + "|";

	}

	public String get_IBM_Pin(String PVK, String Offset, String Chk_Len, String Acc_No, String Decimal_Tbl,
			String Pin_Val_Data, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.EE + PVK + Offset + Chk_Len + Acc_No + Decimal_Tbl
				+ Pin_Val_Data;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.EF);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();
	}

	public String generate_Visa_Pvv(String PVK, String Pin, String Acc_No, String Pvki, String CustomParam1,
			String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.PE + PVK + Pin + Acc_No + Pvki;
		parseResponseFromHsms(ParseHsmReqRsp.processThalesDualRspCommand(this.Command, CustomParam1, CustomParam2),
				Constants.PF);
		return this.parobj.getErr_code() + "|" + this.parobj.getPin();

	}

	// this method to give multiple rsp from hsm like PE command
	// It seems tricky to me, though i have written code as per my understanding
	// roughly, might need changes into this please share your observations
	// here this method will return you two rsp cod PF and PZ and will split rsp by
	// |
	// response will be in this form start with PF rsp code,PIN check value| PZ rsp
	// code eg. 00,12jsdjsjxcnsk|00
	private String parseResponseFromHsms(String[] ThalesRsponse, String RspCmdCode) {

		String ThalesRsp = null;
		String rsp = null;
		String pz_err_code = "";
		if (ThalesRsponse != null && !ThalesRsponse[0].isEmpty()) {
			ThalesRsp = ThalesRsponse[0];
			if ((ThalesRsp.substring(6, 8).equals("00")) && (ThalesRsp.substring(4, 6).equalsIgnoreCase(RspCmdCode))) {

				if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)) {
					this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8),
							ThalesRsp.substring(8));
				} else if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() == 8)) {
					this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8), null);
				}

			} else {
				this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8), null);
			}
		}

		// for time being i have separated both rsp code
		if (ThalesRsponse != null && !ThalesRsponse[1].isEmpty()) {
			ThalesRsp = ThalesRsponse[1];

			if ((ThalesRsp.substring(6, 8).equals("00"))
					&& (ThalesRsp.substring(4, 6).equalsIgnoreCase(Constants.PZ))) {

				pz_err_code = ThalesRsp.substring(6, 8);
				rsp = this.parobj.getErr_code() + "|" + this.parobj.getPin() + "," + pz_err_code;
			}
		}

		return rsp;

	}

	private void parseResponseFromHsm(String ThalesRsp, String RspCmdCode) {

		String response_code = ThalesRsp.substring(6, 8);
		String rsp_cmd = ThalesRsp.substring(4, 6);

		if ((response_code.equals("00")) && (rsp_cmd.equalsIgnoreCase(RspCmdCode))) {

			if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)) {
				this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8),
						ThalesRsp.substring(8));
			} else if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() == 8)) {
				this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8), null);
			}

		} else {
			this.parobj.setParseThalesRsp(ThalesRsp.substring(4, 6), ThalesRsp.substring(6, 8), null);
		}

	}

	public static class Constants {
		public static final String PE = "PE";
		public static final String PF = "PF";
		public static final String PZ = "PZ";
		public static final String EE = "EE";
		public static final String EF = "EF";
		public static final String PA = "PA";
		public static final String PB = "PB";
		public static final String DELIMETER = "*";
		public static final String MESSAGE_HEADER = "5555";
		public static final int Maximum_PIN_LENGTH = 12;
	}

}
