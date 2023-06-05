package com.pin;

import com.hsm.ParseHsmReqRsp;

public class Pin {
	private String Command;
	private ParseThalesPinRsp parobj;

	public Pin() {
		super();
		this.parobj = new ParseThalesPinRsp();

	}

	public String generate_Random_Visa_Pin(String Acc_No, String Pin_len, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.JA + Acc_No + Pin_len;

		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.JB);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();

	}

	public String generate_Visa_Pvv(String PVK, String Pin, String Acc_No, String Pvki, String CustomParam1,
			String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.DG + PVK + Pin + Acc_No + Pvki;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.DH);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();

	}

	public String verify_Pin_Visa_Pvv(String ZPK, String PVK, String PinBlock, String PinBlkFormatCode, String Acc_No,
			String Pvki, String Pvv, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.EC + ZPK + PVK + PinBlock + PinBlkFormatCode + Acc_No + Pvki
				+ Pvv;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.ED);

		return this.parobj.getErr_code() + "|";
	}

	public String verify_Pin_IBM(String ZPK, String PVK, String PinBlock, String PinBlkFormatCode, String Chk_length,
			String Acc_No, String Decimal_Tbl, String Pin_Val_Data, String Offset, String CustomParam1,
			String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.EA + ZPK + PVK + Constants.Maximum_PIN_LENGTH + PinBlock
				+ PinBlkFormatCode + Chk_length + Acc_No + Decimal_Tbl + Pin_Val_Data + Offset;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.EB);

		return this.parobj.getErr_code() + "|";
	}

	public String get_IBM_Pin_Offset(String Acc_No, String PVK, String Pin, String Pin_len, String Decimal_value,
			String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.DE + PVK + Pin + Pin_len + Acc_No + Decimal_value;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.DF);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();
	}

	public String get_IBM_Pin(String PVK, String Offset, String Chk_Len, String Acc_No, String Decimal_Tbl,
			String Pin_Val_Data, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.EE + PVK + Offset + Chk_Len + Acc_No + Decimal_Tbl
				+ Pin_Val_Data;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.EF);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();
	}

	public String pin_Trans_Zpk_To_Zpk(String SrcZpk, String DestZpk, String SrcPinBlk, String SrcPinBlkFormat,
			String DestPinBlkFormat, String Acc_No, String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.CC + SrcZpk + DestZpk + Constants.Maximum_PIN_LENGTH
				+ SrcPinBlk + SrcPinBlkFormat + Acc_No;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.CD);

		return this.parobj.getErr_code() + "|" + this.parobj.getPin();

	}

	private void parseResponseFromHsm(String ThalesRsp, String RspCmdCode) {

		String response_code = ThalesRsp.substring(6, 8);
		String rsp_cmd = ThalesRsp.substring(4, 6);

		if ((response_code.equals("00")) && (rsp_cmd.equalsIgnoreCase(RspCmdCode))) {

			if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)
					&& (RspCmdCode.equalsIgnoreCase(Constants.CD))) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code,
						ThalesRsp.substring(10, 10 + Integer.parseInt(ThalesRsp.substring(8, 10))));
			} else if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, ThalesRsp.substring(8));
			} else if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() == 6)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
			}

		} else {
			this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
		}

	}

	public static class Constants {
		public static final String CC = "CC";
		public static final String CD = "CD";
		public static final String DE = "DE";
		public static final String DF = "DF";
		public static final String DG = "DG";
		public static final String DH = "DH";
		public static final String EA = "EA";
		public static final String EB = "EB";
		public static final String EC = "EC";
		public static final String ED = "ED";
		public static final String EE = "EE";
		public static final String EF = "EF";
		public static final String JA = "JA";
		public static final String JB = "JB";
		public static final String DELIMETER = "*";
		public static final String MESSAGE_HEADER = "5555";
		public static final int Maximum_PIN_LENGTH = 12;
	}

}
