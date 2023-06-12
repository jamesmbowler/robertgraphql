package com.example.paymentsv2.service

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import java.util.logging.Logger


@Service
class NotificationService @Autowired constructor(
    val resourceLoader: ResourceLoader
) {

    fun sendNotification(registrationToken:String, orderId: Long): Boolean {

        val notification = Notification.builder()
            .setTitle("Your Order is ready")
            .setBody("Come and pick it up")
            .build()

//        val androidNotification = AndroidNotification.builder()
//            .setSound("default").build()

        val message = Message.builder()
            .putData("orderId", orderId.toString())
            //.putData("time", "2:45")
            .setNotification(notification)
            .setAndroidConfig(
                AndroidConfig.builder()
                .setTtl(3600 * 1000)
                .setNotification(AndroidNotification.builder()
                    .setIcon("stock_ticker_update")
                    .setColor("#f45342")
                    .setChannelId("default_channel_id")
                    .setSound("default")
                    .setPriority(AndroidNotification.Priority.HIGH)
                    .build()).build())
            .setToken(registrationToken)
            //.setApnsConfig(apnsConfig)
            .build()

//        val message = Message.builder()
//            //.putData("score", "850")
//            //.putData("time", "2:45")
//            .setNotification(Notification.builder()
//                .setTitle("\$GOOG up 1.43% on the day")
//                .setBody("\$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.")
//                .build())
//            .setAndroidConfig(AndroidConfig.builder()
//                .setTtl(3600 * 1000)
//                .setNotification(AndroidNotification.builder()
//                    .setIcon("stock_ticker_update")
//                    .setColor("#f45342")
//                    .setChannelId("default_channel_id")
//                    .setSound("default")
//                    .setPriority(AndroidNotification.Priority.HIGH)
//                    .build())
//                .build())
//            .setApnsConfig(ApnsConfig.builder()
//                .setAps(Aps.builder()
//                    .setBadge(42)
//                    .build())
//                .build())
//            //.setTopic("default_channel_id")
//            .setToken(registrationToken)
//            .build();

        val response = FirebaseMessaging.getInstance().send(message)
        println("Successfully sent message: $response")
        return true

    }

    private val LOG: Logger = Logger.getLogger(NotificationService::class.java.name)

    @EventListener
    fun onApplicationEvent(event: ContextRefreshedEvent?) {

        val serviceAccount = resourceLoader.getResource("classpath:firebase.json").inputStream

        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()

        LOG.info("Initializing Firebase... ")
        FirebaseApp.initializeApp(options);
    }
}