package service;

import util.EmailUtil;

public interface EmailService {

    boolean sendEmailLinkVideo(EmailUtil email);

    boolean sendEmailWelcome(EmailUtil email);

    boolean sendEmailChangePassword(EmailUtil email);

    boolean sendEmailResetPassword(EmailUtil email);
}
