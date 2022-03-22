package com.oopsiedaisy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void shouldFail() {
		assertThat(5).isEqualTo(4);
	}

}
