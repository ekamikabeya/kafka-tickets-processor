package services;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import models.Sale;
import serializers.SaleDeserializer;

public class SaleProcessor {
		private static Random rand = new Random();
		private static long operationId = 0;
		private static BigDecimal ticketValue = BigDecimal.valueOf(500);
		
		private static Sale getSale() {
			long client = rand.nextLong();
			int quantity = rand.nextInt(10);
			Sale sale = new Sale(
					operationId++,
					client,
					quantity,
					ticketValue.multiply(BigDecimal.valueOf(quantity)));	
			return sale;
		}
		
		public static void main(String[]  args) throws InterruptedException {
			Properties properties = new Properties();
		    properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		    properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, SaleDeserializer.class.getName());
		    properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, SaleDeserializer.class.getName());
		    properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_processing");
		    properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		    properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
		    
		    try(KafkaConsumer<String, Sale> consumer = new KafkaConsumer<String, Sale>(properties)) {
		    	consumer.subscribe(Arrays.asList("venda-ingressos"));

		    	while(true) {
		    		ConsumerRecords<String, Sale> records = consumer.poll(Duration.ofSeconds(1));
		    		for (ConsumerRecord<String, Sale> record : records) {
						//System.out.println(sale.key());
						System.out.println(record.value());
					}
		    		
		    		Thread.sleep(2000);
		    	}
		    	
		    	
		    }
		}

}
