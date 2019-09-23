package com.tgc.rocketmqtransaction.consumer;

import java.util.List;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * PushConsumer包含保证ack的机制代码。
 * 若使用了PullConsumer模式，类似的工作如何ack，如何保证消费等均需要使用方自己实现。
 * 
 * ACK (Acknowledgement，确认）
 * @author Administrator
 *
 */
public class Consumer0 {
	public static void main(String[] args) throws MQClientException {
		// 01默认的消息消费FF者
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ConsumerGroup");	//DefaultMQPushConsumer
		// 02注册
		consumer.setNamesrvAddr("127.0.0.1:9876");
		// 03设置获取原则
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		// 04订阅
		consumer.subscribe("TopicTransaction", "*");
		// 05注册监听器    注入一个消费回调
		consumer.registerMessageListener(new MessageListenerConcurrently() {
			public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
				try {
					// 06接收消息并打印
					for (MessageExt msg : msgs) {
						String topic = msg.getTopic();
						String msgBody = new String(msg.getBody(), "utf-8");
						String tags = msg.getTags();
						System.out.println("收到消息： topic:" + topic + " ,tags:" + tags + " ,msg: " + msgBody);
						doMyJob(msg);//执行真正消费
					}
					/*
					 业务实现消费回调的时候，当且仅当此回调函数返回ConsumeConcurrentlyStatus.CONSUME_SUCCESS，RocketMQ才会认为这批消息（默认是1条）是消费完成的。
					 如果这时候消息消费失败，例如数据库异常，余额不足扣款失败等一切业务认为消息需要重试的场景，只要返回ConsumeConcurrentlyStatus.RECONSUME_LATER，
					 RocketMQ就会认为这批消息消费失败了。
					 */
					return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
				} catch (Exception e) {
					e.printStackTrace();
					// 1s 2s 5s ... 2h
					/*
					 为了保证消息是肯定被至少消费成功一次，RocketMQ会把这批消息重发回Broker（topic不是原topic而是这个消费租的RETRY topic），
					 在延迟的某个时间点（默认是10秒，业务可设置）后，再次投递到这个ConsumerGroup。而如果一直这样重复消费都持续失败到一定次数（默认16次），
					 就会投递到DLQ死信队列。应用可以监控死信队列来做人工干预。
					*/
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
			}
		});
		// 07开启
		consumer.start();
		System.out.println("Consumer Started.");
	}
	
	private static void doMyJob(MessageExt msg){
		
	}
}