package com.github.mengyongfeng.orika.springboot.starter;

import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.impl.UtilityResolver;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConditionalOnProperty(prefix = "orika", name = "enabled", havingValue = "true", matchIfMissing = true)
public class OrikaAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    MappingContextFactory mappingContextFactory() {
        return UtilityResolver.getDefaultMappingContextFactory();
    }

    @Bean
    @ConditionalOnMissingBean
    public MapperFacade mapperFacade(
            List<Mapper> mappers,
            List<Converter> converters,
            MappingContextFactory mappingContextFactory) {
        return new ConfigurableMapper() {

            @Override
            protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
                factoryBuilder.mappingContextFactory(mappingContextFactory);
            }

            @Override
            protected void configure(MapperFactory factory) {
                super.configure(factory);
                if (mappers != null) {
                    mappers.forEach(mapper -> factory.classMap(mapper.getAType(), mapper.getBType())
                            .byDefault().customize(mapper).register());
                }
                if (converters != null) {
                    converters.forEach(converter -> factory.getConverterFactory().registerConverter(converter));
                }
            }
        };
    }
}
