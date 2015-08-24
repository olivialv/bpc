package com.mycompany.trader;



import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Class for creating a request signature hash to validate a user's requests.
 * Create at beginning of session to initiate nonce value to be used in all subsequent signatures.
 * Add values to request headers in the following format:
 * -H "Content-Type: application/json" 
 * -H "Authorization: <user_id>:<hashed_signature>" 
 * -H "x-timestamp:<timestamp_YYYY-MM-DDTHH:MM:SS+00:00>" 
 * -H "nonce:<nonce_value>"
 * 
 * @author adamhardie
 *
 */

public class RequestSigner {

	String nonce = null;

	public RequestSigner() {
	}

	private SecureRandom random = new SecureRandom();

	public String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
	
	public String getNextRequestNonce(){
		nonce = nextSessionId();
                return nonce;
	}
	
	public String getTimestamp(){
		 Calendar cal = Calendar.getInstance();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	     return sdf.format(cal.getTime());
	}
	
	public String getSignature(String sessionId, String resource, String httpMethod, String timestamp, String nonce){
		String signature = sessionId + ":" + resource + "," + httpMethod + "," + timestamp + "," + nonce; 
		return signString(signature);
	}
	
	private static String signString(String request) {
        byte[] digest = DigestUtils.sha256(request);
        return new String(Base64.encodeBase64(digest));
    }
}