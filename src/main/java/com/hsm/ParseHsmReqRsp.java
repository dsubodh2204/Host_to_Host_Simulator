package com.hsm;

public class ParseHsmReqRsp {

	public static String ProcessedCmdResult = null;
	public static String[] ProcessedCmdResults = null;

	public static String processThalesCommand(String command, String customParam1, String customParam2) {

		ProcessedCmdResult = SendToHsm.getDataFromHsm(command, customParam1, customParam2);
		// .GetDataFromHsm(command,customParam1,customParam2);
		return ProcessedCmdResult;
	}

	public static String[] processThalesDualRspCommand(String command, String customParam1, String customParam2) {

		ProcessedCmdResults = SendToHsm.getDataFromHsms(command, customParam1, customParam2);
		// .GetDataFromHsm(command,customParam1,customParam2);
		return ProcessedCmdResults;
	}

}
