package com.pin;

public class ParseThalesPinRsp {

	public String rsp_code = "";
	public String err_code = "";
	public String pin = "";

	protected String getRsp_code() {
		return rsp_code;
	}

	public void setParseThalesRsp(String rsp_code, String err_code, String pin) {

		this.rsp_code = rsp_code;
		this.err_code = err_code;
		this.pin = pin;
	}

	protected void setRsp_code(String rsp_code) {
		this.rsp_code = rsp_code;
	}

	protected String getErr_code() {
		return err_code;
	}

	protected void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	protected String getPin() {
		return pin;
	}

	protected void setPin(String pin) {
		this.pin = pin;
	}

}
