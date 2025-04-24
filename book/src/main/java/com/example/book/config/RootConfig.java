package com.example.book.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //spring boot입장에서 부트안에 있는 컨테스트를 돌리면서 참고하는 환경설정 클래스

public class RootConfig {

    @Bean    // 스프링보고 관리하라고 하는 명령어 (new를 갖고있으라하며)
    public ModelMapper getMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
        .setFieldMatchingEnabled(true)
        .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
        .setMatchingStrategy(MatchingStrategies.LOOSE);

        return modelMapper;

    }

}
