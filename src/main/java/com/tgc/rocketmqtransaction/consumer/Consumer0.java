package com.tgc.rocketmqtransaction.consumer;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

public class Consumer0 {
	public static void main(String[] args) throws MQClientException {
		// 01默认的消息消费FF者
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup");
		// 02注册
		consumer.setNamesrvAddr("127.0.0.1:9876");
		// 03设置获取原则
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// 04订阅
		consumer.subscribe("TopicTransaction", "*");
		// 05注册监听器
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				try {
					// 06接收消息并打印
					for (MessageExt msg : msgs) {
						String topic = msg.getTopic();
						String msgBody = new String(msg.getBody(), "utf-8");
						String tags = msg.getTags();
						System.out.println("收到消息： topic:" + topic + " ,tags:" + tags + " ,msg: " + msgBody);
					}
				} catch (Exception e) {
					e.printStackTrace();
					// 1s 2s 5s ... 2h
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
				return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
			}
		});
		// 07开启
		consumer.start();
		System.out.println("Consumer Started.");
	}
}