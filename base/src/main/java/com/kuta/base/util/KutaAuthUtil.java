package com.kuta.base.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

public class KutaAuthUtil {
	//Expiration time
    private static final long EXPIRE_DATE=30*60*1000;
    //Token secret key
    private static final String TOKEN_SECRET = "QCEQIUBFKSABFBZ2021BQFE";
    
    
    /**
     * Generate token
     * @param key1 First data
     * @param key2 Second data
     * @return Authorized token
     */
    public static String token(String key1,String key2){
        String token = "";
        try {
            
            Date date = new Date(System.currentTimeMillis()+EXPIRE_DATE);
            
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            
            Map<String,Object> header = new HashMap<>();
            header.put("typ","JWT");
            header.put("alg","HS256");
            
            token = JWT.create()
                    .withHeader(header)
                    .withClaim("key1",key1)
                    .withClaim("key2",key2)
                    .withExpiresAt(date)
                    .sign(algorithm);
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
        return token;
    }
    
    
    /**
     * Validate token
     * @param token Authorized token
     * @return Validation results
     */
    public static boolean verify(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return  false;
        }
    }
    /**
     * Decode the token to obtain the data object in the token
     * @param token Authorized token
     * @return Data object
     */
    public static DecodedJWT decode(String token) {
    	return JWT.decode(token);
    }
    
}
