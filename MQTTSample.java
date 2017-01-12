import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.io.*;
import java.util.*;

public class MQTTSample{
  public static void main(String[] args)throws IOException {
    String topic        = "Ossie";
    String content      = "Hello CloudMQTT";
    int qos             = 1;
    String broker       = "tcp://m21.cloudmqtt.com:19437";
    String fileName	= "health-debibie-170112-1208.txt";
    String newContent = "";


    //MQTT client id to use for the device. "" will generate a client id automatically
    String clientId     = "ClientId";

	//newContent = readFile(fileName);

    MemoryPersistence persistence = new MemoryPersistence();
    try {	

	
      MqttClient mqttClient = new MqttClient(broker, clientId, persistence);
      mqttClient.setCallback(new MqttCallback() {
        public void messageArrived(String topic, MqttMessage msg)
                  throws Exception {
                      System.out.println("Recived:" + topic);
                      System.out.println("Recived:" + new String(msg.getPayload()));
                }

        public void deliveryComplete(IMqttDeliveryToken arg0) {
                    System.out.println("Delivary complete");
                }

        public void connectionLost(Throwable arg0) {
                    // TODO Auto-generated method stub
                }
      });

      MqttConnectOptions connOpts = new MqttConnectOptions();
      connOpts.setCleanSession(true);
      connOpts.setUserName("qkrozbob");
      connOpts.setPassword(new char[]{'Y', 'W', 'R', 'A', 'B', 'm', '7', 'U', 'S', 'b', 'P', 'a'});
      mqttClient.connect(connOpts);
	newContent = readFile(fileName);
      MqttMessage message = new MqttMessage(newContent.getBytes());

      message.setQos(qos); 
      System.out.println("Publish message: " + message);
      mqttClient.subscribe(topic, qos);
      mqttClient.publish(topic, message);
      mqttClient.disconnect();
      System.exit(0);
    } catch(MqttException me) {
      System.out.println("reason "+me.getReasonCode());
      System.out.println("msg "+me.getMessage());
      System.out.println("loc "+me.getLocalizedMessage());
      System.out.println("cause "+me.getCause());
      System.out.println("excep "+me);
      me.printStackTrace();
    }
  }

public static String readFile(String filename)
{
    String content = null;
    File file = new File(filename); //for ex foo.txt
    FileReader reader = null;
    try {
        reader = new FileReader(file);
        char[] chars = new char[(int) file.length()];
        reader.read(chars);
        content = new String(chars);
        reader.close();
    } catch (IOException e) {
        e.printStackTrace();
    } finally {
	try{
       	 if(reader !=null){reader.close();}
	}catch (IOException e) {
        e.printStackTrace();
	}    
}
    return content;
}
}

	