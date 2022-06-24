package com.expressbank.service;

import com.expressbank.dto.ResponseDTO;
import com.expressbank.email.EmailSender;
import com.expressbank.email.EmailValidator;
import com.expressbank.model.Credential;
import com.expressbank.model.RegistrationRequest;
import com.expressbank.model.Role;
import com.expressbank.model.User;
import com.expressbank.model.util.RoleUtil;
import com.expressbank.repository.UserRepository;
import com.expressbank.token.ConfirmationToken;
import com.expressbank.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private final UserDetailService userDetailService;

    @Autowired
    private final RoleService roleService;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private final EmailValidator emailValidator;

    private final ConfirmationTokenService confirmationTokenService;
    private EmailSender emailSender;

    public Optional<User> findUser(Credential model){
        System.out.println("Find user model: " + model);
        if (model.getPassword() != null){
            System.out.println("password isn't null");
            if (model.getEmail() != null){
                System.out.println("mail isn't null");

                User user = userRepository.findUserByEmail(model.getEmail()).get();

                if (passwordEncoder.matches(model.getPassword(), user.getPassword())){
                    System.out.println(model.getPassword() + " = " + user.getPassword());
                    return userRepository.findUserByEmail(model.getEmail());
                }
            }
        }
        return Optional.empty();
    }

    public ResponseEntity<ResponseDTO> deleteAccount(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()){
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.BAD_REQUEST,
                    "User not found with id!"), HttpStatus.BAD_REQUEST);
        }

        if (confirmationTokenService.findByUser(user.get()).isPresent()){
            Optional<ConfirmationToken> deletedConfirmToken = confirmationTokenService.findByUser(user.get());
            confirmationTokenService.deleteToken(deletedConfirmToken.get().getId());
        }

        if (roleService.findRolesByUser(user.get()).isPresent()){
            Optional<Role> deletedRole = roleService.findRolesByUser(user.get());
            roleService.deleteRoleByUser(deletedRole.get().getId());
        }

        userRepository.delete(user.get());

        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.ACCEPTED, user.get(),
                "Deleted is id = " + id + "successfully"), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ResponseDTO> signIn(Credential model){
        Optional<User> userOptional = findUser(model);
        if (!userOptional.isPresent()){
            String errorMsg = "User not found!";
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.BAD_REQUEST, errorMsg,
                    "Sign In Successfully!"), HttpStatus.BAD_REQUEST);
        }
        else if (userOptional.isEmpty()){
            String errorMsg = "Email or password incorrect!";
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.BAD_REQUEST, errorMsg,
                    "Sign In Successfully!"), HttpStatus.BAD_REQUEST);

        }else if (!userOptional.get().isAccountVerified()){
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.LOCKED, "Account is dont verified!"),
                    HttpStatus.LOCKED);
        }

        System.out.println(userOptional.get());
        System.out.println(HttpStatus.ACCEPTED);

        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.ACCEPTED, userOptional.get(),
                "Sign In Successfully!"), HttpStatus.ACCEPTED);
    }

    public ResponseEntity<ResponseDTO> register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());

        if (!isValidEmail) {
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.NOT_ACCEPTABLE,
                    "Email not valid!"), HttpStatus.NOT_ACCEPTABLE);
        }

        User user = new User(request.getName(), request.getEmail(), request.getPassword());

        List<Role> roleList = new ArrayList<>();
        roleList.add(roleService.findById(RoleUtil.ROLE_USER.ordinal()+1).get());
        user.setRoles(roleList);

        String token = userDetailService.signUpUser(user);

        String link = "http://localhost:8080/api/auth/confirm?token=" + token;

        emailSender.send(request.getEmail(), buildEmail(request.getName(), link));

        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.ACCEPTED, token,
                "Check your email!"), HttpStatus.ACCEPTED);
    }

    @Transactional
    public ResponseEntity<ResponseDTO> confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found!"));

        if (confirmationToken.getConfirmedAt() != null) {
            return new ResponseEntity<>(ResponseDTO.of(HttpStatus.NOT_ACCEPTABLE,
                    "Email Already Confirmed!"), HttpStatus.NOT_ACCEPTABLE);
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired!");
        }

        confirmationTokenService.setConfirmedAt(token);

        userDetailService.enableAppUser(confirmationToken.getUser().getEmail());

        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.OK, "Confirmed!",
                "Token is valid!"), HttpStatus.OK);
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

    public ResponseEntity<ResponseDTO> getUserByEmail(@Valid String email){
        Optional<User> userOptional = userRepository.findUserByEmail(email);
        if (!userOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ResponseDTO.of(HttpStatus.OK, userOptional.get(), "Success"), HttpStatus.OK);
    }
}
