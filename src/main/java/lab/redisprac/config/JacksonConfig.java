package lab.redisprac.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;


@Component
public class JacksonConfig {

    @Bean
    public GenericJackson2JsonRedisSerializer createGenericJackson2JsonRedisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder()
                        .allowIfBaseType(Object.class)
                        .build(),
                ObjectMapper.DefaultTyping.NON_FINAL
        );

        return new GenericJackson2JsonRedisSerializer(mapper);
    }
}