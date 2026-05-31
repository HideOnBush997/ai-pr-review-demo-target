package com.example.reviewdemo.notifications;

public class MarketingCampaignService {
    private final NotificationPreferenceService preferenceService;
    private final MailSender mailSender;

    public MarketingCampaignService(NotificationPreferenceService preferenceService, MailSender mailSender) {
        this.preferenceService = preferenceService;
        this.mailSender = mailSender;
    }

    public boolean sendCampaign(String userId, String subject, String body) {
        if (!preferenceService.canSendMarketingEmail(userId)) {
            return false;
        }
        mailSender.send(userId, subject, body);
        return true;
    }
}
