package be.technifutur.java.timairport.service;

import be.technifutur.java.timairport.jwt.JWTHolderDTO;
import be.technifutur.java.timairport.model.form.LoginForm;
import be.technifutur.java.timairport.model.form.RegistrationForm;

public interface AuthService {

    void register( RegistrationForm form );

    JWTHolderDTO login(LoginForm form );

}
