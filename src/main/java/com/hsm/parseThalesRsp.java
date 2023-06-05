package com.hsm;

public class parseThalesRsp {

	public String rsp_code = "";
	public String err_code = "";
	public String key_check_value = "";

	protected String getKey_check_value() {
		return key_check_value;
	}

	protected void setKey_check_value(String key_check_value) {
		this.key_check_value = key_check_value;
	}

	public void setParseThalesRsp(String rsp_code, String err_code, String key_check_value) {

		this.rsp_code = rsp_code;
		this.err_code = err_code;
		this.key_check_value = key_check_value;
	}

	protected String getRsp_code() {
		return rsp_code;
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
}
