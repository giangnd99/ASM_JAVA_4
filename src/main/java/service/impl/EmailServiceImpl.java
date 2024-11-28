package service.impl;

import service.EmailService;
import util.EmailUtil;

public class EmailServiceImpl implements EmailService {

    private final String SUBJECT_EMAIL_WELCOME = "Chúc mừng: bạn đã là thành viên của Entertainment Video";
    private final String SUBJECT_EMAIL_LINK = "Thông báo: Chia sẻ Video";
    private final String SUBJECT_EMAIL_CHANGE = "Cảnh báo: yêu cầu thay đổi mật khẩu";
    private final String SUBJECT_EMAIL_RESET = "Cảnh báo: yêu cầu lấy lại mật khẩu";

    @Override
    public boolean sendEmailLinkVideo(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_LINK);
        return EmailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailWelcome(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_WELCOME);
        return EmailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailChangePassword(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_CHANGE);
        return EmailUtil.sendEmail(email);
    }

    @Override
    public boolean sendEmailResetPassword(EmailUtil email) {
        email.setSubject(SUBJECT_EMAIL_RESET);
        return EmailUtil.sendEmail(email);
    }

    @Override
    public String appendTitle(EmailUtil email, String title) {
        return email.getSubject() + " - " +
                title;
    }

    @Override
    public String appendBody(EmailUtil email, String body) {
        StringBuilder bodyPart = new StringBuilder();
        bodyPart.append(email.getMessageBody());
        String[] bodyParts = body.split(" ");
        if (bodyParts.length > 10) {
            for (int i = 0; i < bodyParts.length; i++) {
                bodyPart.append(bodyParts[i]);
                if (i % 10 == 0) {
                    bodyPart.append("\n");
                }
            }
        }
        return bodyPart.toString();
    }
}
