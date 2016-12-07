package calculus.backend.model;

public class Token {
    public static final String TOKEN_TYPE_ALUMNO = "alumno";
    public static final String TOKEN_TYPE_PROFESOR = "profesor";
    protected static final String separator = "#";
    
    
    private String userType;
    private String userEmail;
    private String userPassword;
    private String key;
    
    
    public Token(String key){
        String[] s = key.split(Token.separator);
        this.userType = s[0];
        this.userEmail = s[1];
        this.userPassword = s[2];
        this.key = key;
    }

    public Token(String userType, String userEmail, String userPassword) {
        this.userType = userType;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.key = userType + Token.separator 
                + userEmail + Token.separator 
                + userPassword;
    }
    
    
    /**
     * genera el token a partir de los parametros pasados.
     * @param type
     * @param email
     * @param passwd
     * @return 
     */
    public static final String generateToken(String type, String email, String passwd) {
        return type + Token.separator + email + Token.separator + passwd;
    }
    
    public String getUserPassword() {
        return userPassword;
    }
    
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
    
    
    
    
    

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    
}