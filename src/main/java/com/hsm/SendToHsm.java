package com.hsm;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class SendToHsm {

	public static String getDataFromHsm(String HsmCommand, String customParam1, String customParam2) {
		String result = null;
		String output = null;
		try {
			result = hsm_Cmd(HsmCommand, customParam1, customParam2, false);

			if (result != null && !result.isEmpty()) {
				// String response_code = result.substring(6, 8);

				if (result.length() >= 8) {

					output = result;

				}

			}
		} catch (Exception ex) {
			// logger needs to be add here

		}
		return output;
	}

	// here i am not sure it will work or not.
	// please check and share your obeservations i will change accordingly
	// because of this dual response i have made serveal changes to hsm_Cmd method ,
	// so there is possibility that it will get impacted on other HSM commnads

	public static String[] getDataFromHsms(String HsmCommand, String customParam1, String customParam2) {
		String res = null;
		String result[] = null;
		String output[] = null;
		try {

			res = hsm_Cmd(HsmCommand, customParam1, customParam2, true);
			result = res.split(res);

			if (result[0] != null && !result[0].isEmpty()) {
				// String response_code = result.substring(6, 8);

				if (result[0].length() >= 8) {

					output[0] = result[0];

				}

			}
			if (result[1] != null && !result[1].isEmpty()) {
				// String response_code = result.substring(6, 8);

				if (result[0].length() >= 8) {

					output[1] = result[1];

				}

			}
		} catch (Exception ex) {
			// logger needs to be add here

		}
		return output;
	}

	private static String hsm_Cmd(String hsmCommand, String customParam1, String customParam2, boolean isPinMailer)
			throws Exception {
		String __hsmCommand = "";
		String __hsmCommandOutput = "NULL";
		__hsmCommand = new StringBuilder().append(Chr(0)).append(Chr(__hsmCommand.length())).append(__hsmCommand)
				.toString();

		byte[] Req = __hsmCommand.getBytes("UTF-8");

		byte[][] rspBytes = sendDataToHsm(Req, customParam1, customParam2, isPinMailer);

		if (rspBytes[0] != null) {
			String __hsmCommandOutput1 = new String(rspBytes[0], "US-ASCII");

			if (__hsmCommandOutput1 == "") {
				throw new Exception(new StringBuilder().append("HSM gave Blank Output in ").append(hsmCommand)
						.append(" Command").toString());
			}

			__hsmCommandOutput = __hsmCommandOutput1;
		} else {
			__hsmCommandOutput = "NULL";
		}
		if (isPinMailer) {
			String rsp = null;
			if (rspBytes[1] != null) {
				String __hsmCommandOutput2 = new String(rspBytes[0], "US-ASCII");

				if (__hsmCommandOutput2 == "") {
					throw new Exception(new StringBuilder().append("HSM gave Blank Output in ").append(hsmCommand)
							.append(" Command").toString());
				}

				rsp = __hsmCommandOutput2 + "|" + __hsmCommandOutput2;
			} else {
				__hsmCommandOutput = "NULL";
			}
			return rsp;
		}

		return __hsmCommandOutput.trim();

	}

	@SuppressWarnings("null")
	public static byte[][] sendDataToHsm(byte[] request, String customParam1, String customParam2, boolean isPinmailer)
			throws IOException {
		byte[][] response = null;
		byte[][] response1 = null;
		int responseSize = 0;
		int responseSize1 = 0;
		boolean processTran = true;
		// here you need to replace your hsm ip or you can use customParam1 to send it
		// dynamically
		InetAddress addr = InetAddress.getByName("111111.1.1.1");
		// here you need to replace your hsm port
		int port = Integer.parseInt("9999");

		SocketAddress endpoint = new InetSocketAddress(addr, port);
		Socket clientSocket = new Socket();
		try {
			clientSocket.connect(endpoint);
		} catch (Exception e) {
			processTran = false;
			response = null;
			response1 = null;
			// logger needs to be add here
		}

		if (processTran) {
			try {
				long start;
				long end = System.currentTimeMillis();
				if (request != null) {
					clientSocket.getOutputStream().write(request, 0, request.length);
					start = System.currentTimeMillis();
					do {
						responseSize = clientSocket.getInputStream().available();
						if (responseSize > 0) {
							response[0] = new byte[responseSize];
							clientSocket.getInputStream().read(response[0], 0, responseSize);
							break;
						}
					} while ((end - start) / 1000F < 15);
					{
						if (isPinmailer) {
							responseSize1 = clientSocket.getInputStream().available();
							if (responseSize1 > 0) {
								response[1] = new byte[responseSize1];
								clientSocket.getInputStream().read(response1[0], 0, responseSize1);

							}
						}
					}
				} else {
					response = null;
					clientSocket.close();
					return response;
				}
			} catch (Exception xObj) {
				clientSocket.close();
				return null;
			} finally {
				clientSocket.close();
			}

		}

		clientSocket.close();
		return response;
	}

	private static String Chr(int CharCode) {
		String __char = "";
		try {
			__char = String.valueOf((char) CharCode);
		} catch (Exception xObj) {
			__char = "";
		}
		return __char;
	}

}
