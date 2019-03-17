package com.example.minh98.assistant_v1.main.presenter


import android.content.Context
import android.util.Log

import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

class Mqtt(context: Context) {
    var mqttAndroidClient: MqttAndroidClient

    internal val serverUri = "tcp://m11.cloudmqtt.com:17690"

    internal val clientId = "ESPAndroid"
    internal val subscriptionTopic = "deviceA"

    internal val username = "iotadudi"
    internal val password = "LtyAp9_rGJ3R"

    init {
        mqttAndroidClient = MqttAndroidClient(context, serverUri, clientId)
        mqttAndroidClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                Log.w("mqtt", s)
            }

            override fun connectionLost(throwable: Throwable) {

            }

            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Mqtt", mqttMessage.toString())
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {

            }
        })
        connect()
    }

    fun setCallback(callback: MqttCallbackExtended) {
        mqttAndroidClient.setCallback(callback)
    }

    fun push(status:Boolean,index:Int){
        //status la bat /tat
        //index:
        //1: den 1
        //2: den 2
        //3: ca 2
        val st = if(status){'1'.toByte()}else{'0'.toByte()}
        if(index == 1) {
            mqttAndroidClient.publish("deviceA", MqttMessage(byteArrayOf(st)))
        }
        if(index == 2){
            mqttAndroidClient.publish("deviceB", MqttMessage(byteArrayOf(st)))
        }
        if(index == 3){
            mqttAndroidClient.publish("deviceA", MqttMessage(byteArrayOf(st)))
            mqttAndroidClient.publish("deviceB", MqttMessage(byteArrayOf(st)))
        }
    }

    private fun connect() {
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.isAutomaticReconnect = true
        mqttConnectOptions.isCleanSession = false
        mqttConnectOptions.userName = username
        mqttConnectOptions.password = password.toCharArray()

        try {

            mqttAndroidClient.connect(mqttConnectOptions, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {

                    val disconnectedBufferOptions = DisconnectedBufferOptions()
                    disconnectedBufferOptions.isBufferEnabled = true
                    disconnectedBufferOptions.bufferSize = 100
                    disconnectedBufferOptions.isPersistBuffer = false
                    disconnectedBufferOptions.isDeleteOldestMessages = false
                    mqttAndroidClient.setBufferOpts(disconnectedBufferOptions)
                    subscribeToTopic()
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.w("Mqtt", "Failed to connect to: $serverUri$exception")
                }
            })


        } catch (ex: MqttException) {
            ex.printStackTrace()
        }

    }


    private fun subscribeToTopic() {
        try {
            mqttAndroidClient.subscribe(subscriptionTopic, 0, null, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken) {
                    Log.w("Mqtt", "Subscribed!")
                }

                override fun onFailure(asyncActionToken: IMqttToken, exception: Throwable) {
                    Log.w("Mqtt", "Subscribed fail!")
                }
            })

        } catch (ex: MqttException) {
            System.err.println("Exception whilst subscribing")
            ex.printStackTrace()
        }

    }
}
