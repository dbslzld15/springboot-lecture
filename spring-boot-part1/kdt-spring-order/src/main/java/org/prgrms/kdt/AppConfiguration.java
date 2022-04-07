package org.prgrms.kdt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan
@PropertySource("application.properties")
public class AppConfiguration { // 주문에 대한 전반적인 도메인 객체에 대한 생성을 책임짐, IOC라고 부름

}
