package com.traccapp.demo.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.traccapp.demo.exception.AbstractGraphQLException;
import com.traccapp.demo.model.Accounts;
import com.traccapp.demo.model.FcmSubscriptions;
import com.traccapp.demo.model.Notifications;
import com.traccapp.demo.payload.request.NotificationRequest;
import com.traccapp.demo.repository.AccountRepository;
import com.traccapp.demo.repository.FcmSubscriptionRepository;
import com.traccapp.demo.repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    
    @Autowired
    private final FcmSubscriptionRepository fcmSubscriptionRepository;
    @Autowired
    private final NotificationRepository notificationRepository;
    @Autowired
    private final AccountRepository accountRepository;

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
            .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+notificationRequest.getReceiverId(),"accountId"));

        Notifications notification = new Notifications();
        notification.setReceiver(account);
        notification.setCreatedAt(OffsetDateTime.now());
        notification.setTitle(notificationRequest.getTitle());
        notification.setBody(notificationRequest.getBody());

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

    // @Transactional
    // public String sendPushNotification(UUID accountId, String title, String body) throws IOException{

    //     String result = "";
    //     URL url = new URL(FIREBASE_API_URL);

    //     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    //     conn.setUseCaches(false);
    //     conn.setDoInput(true);
    //     conn.setDoOutput(true);
    //     conn.setRequestMethod("POST");
    //     conn.setRequestProperty("Authorization", "key="+FIREBASE_SERVER_KEY);
    //     conn.setRequestProperty("Content-Type", "application/json");

    //     Accounts account = accountRepository.findById(accountId)
    //         .orElseThrow(() -> new AbstractGraphQLException("Account with current id cannot be found: "+accountId,"accountId"));

    //     List<FcmSubscriptions> fcmSubscriptions = fcmSubscriptionRepository.findAllByAccount(account);
    //     List<String> fcmTokens = fcmSubscriptions.stream()
    //         .map(fcmSubs -> fcmSubs.getToken())
    //         .collect(Collectors.toList());

    //     String fcmToken = fcmTokens.get(0);

    //     JSONObject json = new JSONObject();

    //     try{
    //         JSONObject info = new JSONObject();
    //         info.put("title", title);
    //         info.put("body", body);

    //         json.put("notification", info);
    //         json.put("to", fcmToken.trim());

    //     } catch (JSONException ex1){
    //         ex1.printStackTrace();
    //     }

    //     try {
    //         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    //         wr.write(json.toString());
    //         wr.flush();

    //         BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

    //         String output;
    //         System.out.println("Output from server...\n");
    //         while((output = br.readLine()) != null){
    //             System.out.println(output);
    //         }
    //         result = "Success";
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         result = "Failure";
    //     }

    //     return result;
    // }
    
    @Autowired
    private final FirebaseMessaging firebaseMessaging;

    @Transactional
    public String sendPushNotification(String fcmToken, String title, String body) throws FirebaseMessagingException{

        Notification notification = Notification.builder()
                                        .setTitle(title)
                                        .setBody(body)
                                        .build();
                    
        Message message = Message.builder()
                            .setToken(fcmToken)
                            .setNotification(notification)
                            .putData("title", title)
                            .build();

        String response = "";
        try {
            response = firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        return response;
    }
}
