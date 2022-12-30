package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.Notifications;
import com.traccapp.demo.payload.request.NotificationRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.FcmSubscriptionRepository;
import com.traccapp.demo.repository.NotificationRepository;
import com.traccapp.demo.utils.HeaderRequestInterceptor;

import freemarker.template.Template;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
    
    @Autowired
    private final FcmSubscriptionRepository fcmSubscriptionRepository;
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final AccountRepository accountRepository;
    @Autowired
    private final JavaMailSender javaMailSender;
    @Autowired
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Value("${fcm.firebaseServerKey}")
    private String firebaseServerKey;

    @Value("${fcm.firebaseApiUrl}")
    private String firebaseApiUrl;
    
    @Value("${spring.mail.username}")
    private String sender;

    @Transactional
    public List<Notifications> getAllNotificationsForAccount(UUID accountId){
        Accounts account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return notificationRepository.findAllByReceiver(account);
    }

    @Transactional
    public List<Notifications> getTopNotificationsForAccount(UUID accountId){
        Accounts account = accountRepository.findById(accountId)
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

        return notificationRepository.findFirst3ByReceiverOrderByCreatedAtDesc(account);
    }

    @Transactional
    public Notifications getNotification(UUID notificationId){
        return notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalStateException("Notification with current id cannot be found: "+notificationId));
    }

    @Transactional
    public Notifications addNotification(NotificationRequest notificationRequest){
        Accounts account = accountRepository.findById(notificationRequest.getReceiverId())
            .orElseThrow(() -> new IllegalStateException("Account with current id cannot be found: "+notificationRequest.getReceiverId()));

        Notifications notification = new Notifications();
        notification.setReceiver(account);
        notification.setCreatedAt(OffsetDateTime.now());
        notification.setTitle(notificationRequest.getTitle());
        notification.setBody(notificationRequest.getBody());
        notification.setData(notificationRequest.getData());

        return notificationRepository.save(notification);
    }

    @Transactional
    public void readNotification(UUID notificationId){
        Notifications notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new IllegalStateException("Notification with current id cannot be found: "+notificationId));

        if(notification.getReadAt() == null){
            notification.setReadAt(OffsetDateTime.now());
            notificationRepository.save(notification);
        }
    }

    @Async
    @Transactional
    public String sendPushNotification(Notifications notificationObject){
        String result="";

        List<String> fcmTokens = fcmSubscriptionRepository.findAllByAccount(notificationObject.getReceiver()).stream()
            .map(subs -> subs.getToken())
            .collect(Collectors.toList());

        for(String fcmToken : fcmTokens){
            JSONObject json = new JSONObject();

            try {
                json.put("to", fcmToken);
                
                JSONObject notification = new JSONObject();
                notification.put("title", notificationObject.getTitle());
                notification.put("body", notificationObject.getBody());
                notification.put("click_action", "/");

                json.put("notification", notification);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
    
            RestTemplate restTemplate = new RestTemplate();
            ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
            interceptors.add(new HeaderRequestInterceptor("Authorization", "key="+firebaseServerKey));
            interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
            restTemplate.setInterceptors(interceptors);
            HttpEntity<String> request = new HttpEntity<>(json.toString());
            result = restTemplate.postForObject(firebaseApiUrl, request, String.class);
        }
        
        return result;
    }

    // @Transactional
    // public String sendEmail(Notifications notificationObject){
    //     try {
    //         SimpleMailMessage mailMessage = new SimpleMailMessage();
    //         mailMessage.setFrom(sender);
    //         mailMessage.setTo(notificationObject.getReceiver().getEmail());
    //         mailMessage.setText(notificationObject.getBody());
    //         mailMessage.setSubject(notificationObject.getTitle());

    //         javaMailSender.send(mailMessage);

    //         return "Mail sent successfully";

    //     } catch (Exception e) {
    //         return "Error sending email: "+e.getMessage();
    //     }

    // }

    @Async
    @Transactional
    public String sendEmail(Notifications notificationObject){
        try {
            Map<String,Object> data = new HashMap<>();
            data.put("notification", notificationObject);

            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);

            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            mailMessage.setFrom(new InternetAddress(sender));
            mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(notificationObject.getReceiver().getEmail()));
            mailMessage.setSubject(notificationObject.getTitle());
            mailMessage.setText(html,"UTF-8","html");

            javaMailSender.send(mailMessage);

            return "Email sent successfully";

        } catch (Exception e) {
            return "Error sending email: "+e.getMessage();
        }

    }

}
