package com.hand.hap.aurora.attachment.processor.oss;

import com.aliyun.oss.ClientConfiguration;

/**
 * description
 *
 * @author shira 2019/04/11 16:02
 */
public class DefaultClientConfiguration {

	private static  ClientConfiguration ClientConfiguration = new ClientConfiguration();

	private DefaultClientConfiguration() {
		// Set the maximum number of allowed open HTTP connections
		ClientConfiguration.setMaxConnections(100);
		// Set the amount of time to wait (in milliseconds) when initially establishing
		// a connection before giving up and timing out
		ClientConfiguration.setConnectionTimeout(5000);
		// Set the maximum number of retry attempts for failed retryable requests
		ClientConfiguration.setMaxErrorRetry(3);
		// Set the amount of time to wait (in milliseconds) for data to betransfered over
		// an established connection before the connection times out and is closed
		ClientConfiguration.setSocketTimeout(2000);
	}

	public static ClientConfiguration create(){
		return ClientConfiguration;
	}

}
