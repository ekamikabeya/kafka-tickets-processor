package serializers;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import models.Sale;

public class SaleDeserializer implements Deserializer<Sale>{

	public SaleDeserializer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Sale deserialize(String topic, byte[] data) {
		try {
			return new ObjectMapper().readValue(data, Sale.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
