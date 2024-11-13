package service.impl;

import service.EmailService;
import util.EmailUtil;

public class EmailServiceImpl implements EmailService {

    private String SUBJECT_EMAIL_WELCOME = "Chúc mừng: bạn đã là thành viên của Entertainment Video";
    private String SUBJECT_EMAIL_LINK = "Thông báo: Video";
    private String SUBJECT_EMAIL_CHANGE = "Cảnh báo: yêu cầu thay đổi mật khẩu";
    private String SUBJECT_EMAIL_RESET = "Cảnh báo: yêu cầu lấy lại mật khẩu";

    private EmailUtil emailUtil = new EmailUtil();

    @Override
    public boolean sendEmailLinkVideo(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_LINK);
        return emailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailWelcome(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_WELCOME);
        return emailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailChangePassword(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_CHANGE);
        return emailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailResetPassword(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_RESET);
        return emailUtil.sendEmail(email);
    }
}
