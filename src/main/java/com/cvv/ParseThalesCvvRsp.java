package com.cvv;

public class ParseThalesCvvRsp {

	public String rsp_code = "";
	public String err_code = "";
	public String cvv_code = "";

	protected String getRsp_code() {
		return rsp_code;
	}

	public void setParseThalesRsp(String rsp_code, String err_code, String cvv_code) {

		this.rsp_code = rsp_code;
		this.err_code = err_code;
		this.cvv_code = cvv_code;
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

	protected String getCvv_code() {
		return cvv_code;
	}

	protected void setCvv_code(String cvv_code) {
		this.cvv_code = cvv_code;
	}

}
