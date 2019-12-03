package com.tgc.rocketmqtransaction.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * https://www.cnblogs.com/itplay/p/10647619.html
 * 
 * @author Administrator
 *
 */

public class Producer {

	public static void main(String[] args) throws MQClientException, InterruptedException {
		// 01 创建一个有事务基因的生产者
		TransactionMQProducer producer = new TransactionMQProducer("ProducerGroup");
		// 02 注册到消息中间件
		producer.setNamesrvAddr("127.0.0.1:9876");
		// 03 启动生产者。
		producer.start();
		
		/**
		 * 04 生产者设置事务监听器，匿名内部类new一个事务监听器， 重写“执行本地事务”和“检查本地事务”两个方法，返回值都为 “本地事务状态”
		 * 注意；可以不用匿名内部类的方法，单独为一个类亦可。
		 */
		producer.setTransactionListener(new TransactionListener() {
			public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
				String tag = msg.getTags();
				
				if (tag.equals("Transaction1")) {
					System.out.println("这里处理业务逻辑,比如操作数据库失败情况下，消息进行回滚");
					// 如果失败，再次给MQ发送消息
					/**
					 * 这里的逻辑是message的标签名为“Transaction1”的消息回滚状态。
					 */
					return LocalTransactionState.ROLLBACK_MESSAGE;	//发送回滚消息
				}
				//除了message的标签名为“Transaction1”的消息回滚，其它的在这里直接返回提交消息的状态。
				return LocalTransactionState.COMMIT_MESSAGE;
			}

			public LocalTransactionState checkLocalTransaction(MessageExt msg) {
				System.out.println("state -- " + new String(msg.getBody()));
				return LocalTransactionState.COMMIT_MESSAGE;
			}
		});
		
		/**
		 * 模拟发送3个message。    在以上代码中tag name = "Transaction1"的“消息将被回滚”（不会发送到消费者）。
		 */
		for (int i = 1; i <= 3; i++) {
			try {
				// 05 准备要发送的message，名字，标签，内容
				Message msg = new Message("TopicTransaction", "Transaction" + i,
						("Hello RocketMQ " + i).getBytes("UTF-8"));
				
				// 06 用发送事务特有的方法发送消息，而不是简单的producer.send(msg);
				SendResult sendResult = producer.sendMessageInTransaction(msg, null);
				System.out.println(sendResult);
			} catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(1000);
			}
		}
		// 07 关闭
		producer.shutdown();
	}
}