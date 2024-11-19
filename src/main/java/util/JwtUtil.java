package util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

@Component
public class JwtUtil {
    private final byte[] secretKey = new byte[32]; // 256-bit key for HS256

    private long expirationTime = 1000 * 60 * 60; // 1 hour

    public JwtUtil() {
        new java.security.SecureRandom().nextBytes(secretKey);
    }

    // Generate JWT token with email as subject
    public String generateToken(String email, boolean isAdmin) {
        try {
            // Set JWT claims
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(email)
                    .issueTime(new Date())
                    .claim("isAdmin", isAdmin)
                    .expirationTime(new Date(System.currentTimeMillis() + expirationTime))
                    .build();

            // Create signed JWT with HS256
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(new MACSigner(secretKey));

            return signedJWT.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean validateToken(String token, String email) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean isValidSignature = signedJWT.verify(new MACVerifier(secretKey));
            String extractedEmail = signedJWT.getJWTClaimsSet().getSubject();
            boolean isNotExpired = !isTokenExpired(signedJWT);

            return isValidSignature && extractedEmail.equals(email) && isNotExpired;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("Error validating JWT token", e);
        }
    }

    public boolean validateAdminToken(String token, boolean isAdmin) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            boolean isValidSignature = signedJWT.verify(new MACVerifier(secretKey));
            boolean extractedRoleAdmin = signedJWT.getJWTClaimsSet().getBooleanClaim("isAdmin");
            boolean isNotExpired = !isTokenExpired(signedJWT);

            return isValidSignature && extractedRoleAdmin && isNotExpired;
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException("Error validating JWT token", e);
        }
    }

    // Extract email from JWT token
    public String extractEmail(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting username from JWT token", e);
        }
    }

    // Check if token is expired
    private boolean isTokenExpired(SignedJWT signedJWT) {
        try {
            Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();
            return expiration.before(new Date());
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting expiration date from JWT token", e);
        }
    }

    // Extract expiration date from token
    public Date extractExpiration(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            return signedJWT.getJWTClaimsSet().getExpirationTime();
        } catch (ParseException e) {
            throw new RuntimeException("Error extracting expiration date from JWT token", e);
        }
    }

    /**
     * Dùng để thay đổi thời gian tồn tại mặc định 1h của token đơn vị là giờ
     *
     * @param expirationTimePerHours
     */
    public void setExpirationTime(long expirationTimePerHours) {
        this.expirationTime = 1000 * 60 * 60 * expirationTime;
    }
}
