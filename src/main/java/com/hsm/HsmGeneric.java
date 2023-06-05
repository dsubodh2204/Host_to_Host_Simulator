package com.hsm;

public class HsmGeneric {
	private String Command;
	private parseThalesRsp parobj;

	public HsmGeneric() {
		super();
		this.parobj = new parseThalesRsp();
	}

	public String generate_Key_Check_Value_Thales(String KeyTypCode, String KeyLengthFlag, String Key,
			String CustomParam1, String CustomParam2) {
		this.Command = Constants.MESSAGE_HEADER + Constants.BU + KeyTypCode + KeyLengthFlag + Key;
		parseResponseFromHsm(ParseHsmReqRsp.processThalesCommand(this.Command, CustomParam1, CustomParam2),
				Constants.BV);

		return this.parobj.getRsp_code() + "|" + this.parobj.getKey_check_value();

	}

	private void parseResponseFromHsm(String ThalesRsp, String RspCmdCode) {

		String response_code = ThalesRsp.substring(6, 8);
		String rsp_cmd = ThalesRsp.substring(4, 6);

		if ((response_code.equals("00")) && (rsp_cmd.equalsIgnoreCase(RspCmdCode))) {

			if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() > 10)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, ThalesRsp.substring(8));
			} else if ((ThalesRsp != null) && (!ThalesRsp.isEmpty()) && (ThalesRsp.length() == 6)) {
				this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
			}

		} else {
			this.parobj.setParseThalesRsp(rsp_cmd, response_code, null);
		}

	}

	public static class Constants {
		public static final String BU = "BU";
		public static final String BV = "BV";
		public static final String DELIMETER = "*";
		public static final String MESSAGE_HEADER = "5555";
	}

}
