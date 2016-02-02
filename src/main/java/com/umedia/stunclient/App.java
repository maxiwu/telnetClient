package com.umedia.stunclient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws UnknownHostException,
			IOException {
		// args ip, port
		String sentence;
		String modifiedSentence;

		ConnectToTcp(args[0], Integer.parseInt(args[1]));
	}

	private static void ConnectToTcp(String ip, int port)
			throws UnknownHostException, IOException {
		System.out.println(String.format("connecting to %s %d", ip, port));
		String sentence = "hello";
		String modifiedSentence;
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));
		Socket clientSocket = new Socket(ip, port);
		// clientSocket.connect(endpoint);
		DataOutputStream outToServer = new DataOutputStream(
				clientSocket.getOutputStream());

		// read from server
		outToServer.writeBytes(sentence + '\r' + '\n');		
		outToServer.flush();		
		
		InputStream instr = clientSocket.getInputStream();

		try
        {
            byte[] buff = new byte[1024];
            int ret_read = 0;

            do
            {
                ret_read = instr.read(buff);                
                if(ret_read > 0)
                {
                	String rev = new String(buff, 0, ret_read);
                    System.out.print(rev);
                    if(rev.endsWith("\n"))
                    {
                    	ret_read = 0;
                    	break;
                    }
                }                
            }
            while (ret_read >= 0);
        }
        catch (IOException e)
        {
            System.err.println("Exception while reading socket:" + e.getMessage());
        }

        try
        {
        	clientSocket.close();
        }
        catch (IOException e)
        {
            System.err.println("Exception while closing telnet:" + e.getMessage());
        }
		//System.out.println("FROM SERVER: " + modifiedSentence);

		clientSocket.close();
		
		System.out.println("finish reading");
		
	}
}
