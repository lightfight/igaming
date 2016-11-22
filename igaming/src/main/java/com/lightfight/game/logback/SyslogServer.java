package com.lightfight.game.logback;

import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Implements a basic syslog server receiving data on port 514 and logging it to a file called syslog.log
 */

public class SyslogServer {

	@SuppressWarnings({ "resource", "deprecation" })
	public static void main(String args[]) throws Exception {
		// Create the buffer to store the data as it comes in
		byte[] log_buffer = new byte[2048];

		int received_messages = 0;

		// Open the file for writing the log messages
		File out_file = new File("syslog.log");

		// Create the output stream so we can dump the data
		FileOutputStream syslog_file = new FileOutputStream(out_file);

		// Create a DatagramPacket to receive the incoming log data
		DatagramPacket packet = new DatagramPacket(log_buffer, log_buffer.length);

		// Create a socket that listens on the net
//		DatagramSocket socket = new DatagramSocket(514);
		DatagramSocket socket = new DatagramSocket(554);

		while (received_messages < 5) {
			// Wait until some data arrives. Aren’t threads great?
			socket.receive(packet);

			// Increment the message count
			received_messages++;

			// Build a string of the packet data
			String packet_string = new String(log_buffer, 0, 0, packet.getLength());

			// Put the packet data after a bit of header so we can see where it
			// comes
			// from
//			String out_string = "<syslog from " + packet.getAddress().getHostName() + ">\n" + packet_string;
			String out_string = packet_string;

			// Print the message to the standard out window
			System.out.println(out_string);

			// Convert the message to an array of bytes so it can be sent to the
			// file
			int msg_len = out_string.length();

			byte[] out_buffer = new byte[msg_len];

			out_string.getBytes(0, out_string.length(), out_buffer, 0);

			// Write the name of the host where the data came from to the file
			syslog_file.write(out_buffer, 0, out_string.length());
		}
		socket.close();
	}
}
