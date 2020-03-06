package com.amphora.framework.utilities;

import io.cucumber.core.api.Scenario;

/**
 * Contains methods used for logging information in reports
 *
 */
public class Reporter {
	private Scenario logger;

	public Reporter(Scenario scenario) {
		logger = scenario;
	}

	public void logger(String message) {
		logger.write(message);
	}

	public void customTableHeader() {
//		String html="<table style=\"border:1px solid black:\" class=\"data-table\">"
//				+ "<tbody><tr style=\"border:1px solid black:\"><th width=\"580\">Step Verifications</th> <th width=\"70\">Status</th></tr>";
//		logger.embed(html.getBytes(), "");
		logger.write("<table style=\"border:1px solid black:\" class=\"data-table\">"
				+ "<tbody><tr style=\"border:1px solid black:\"><th width=\"580\">Step Verifications</th> <th width=\"70\">Status</th></tr>");
	}

	public void logVerifictions(String message, String status) {
		switch (status) {
		case "PASS":
			logger.write("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='GREEN'><i>Pass</i></font></td></tr>");
			break;
		case "FAIL":
			logger.write("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='RED'><i>Fail</i></font></td></tr>");
			break;
		case "DONE":
			logger.write("<tr><td width=\"680\" style='text-align:left'><i>" + message + "</i></td> <td width=\"80\">"
					+ "<font color='BLUE'><i>Done</i></font></td></tr>");
			break;
		case "BLANK":
			logger.write("<tr><td width=\"680\" style='text-align:left'><b>" + message + "</b></td> <td width=\"80\">"
					+ "</td></tr>");
			break;

		}

	}

	public void customTableFooter() {
		logger.write("</tbody><div><div/></table>");
	}
}
