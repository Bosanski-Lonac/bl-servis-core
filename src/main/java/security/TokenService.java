package security;

import dto.KorisnikDto;
import dto.TokenResponseDto;
import io.jsonwebtoken.Claims;

public interface TokenService {
	TokenResponseDto createToken(KorisnikDto korisnikDto);
	String generate(Claims claims);
    Claims parseToken(String authorization);
    Long getIdFromToken(String authorization);
    void setSecret(String jwtSecret);
}
