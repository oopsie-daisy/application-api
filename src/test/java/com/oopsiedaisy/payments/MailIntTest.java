package com.oopsiedaisy.payments;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import com.oopsiedaisy.common.IntegrationTest;
import com.oopsiedaisy.payments.controller.resource.ItemsToBuyResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.test.context.jdbc.Sql;

import javax.mail.internet.MimeMessage;
import java.math.BigDecimal;
import java.util.List;

import static com.oopsiedaisy.payments.domain.PaymentProvider.SWEDBANK;
import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/testdata/flowers/add-flowers.sql"})
class MailIntTest extends IntegrationTest {

    private static final String PAYMENTS_COMPLETE_URL = "/payments/complete";
    private static final String SENDER_IBAN = "LT123456789";
    private static final String EXISTING_ITEM_UUID = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";


    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("duke", "springboot"))
            .withPerMethodLifecycle(false);

    @Test
    void shouldSendEmailOnSuccessfulOrder() throws Exception {
        mockMvc.perform(post(PAYMENTS_COMPLETE_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildPaymentBody(EXISTING_ITEM_UUID))))
                .andExpect(status().isOk());

        // await for mail to be sent
        Thread.sleep(5 * 1000);
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        assertThat(receivedMessage).isNotNull();

    }

    private ItemsToBuyResource buildPaymentBody(String uuid) {
        return ItemsToBuyResource.builder()
                .customerAddress("Test")
                .customerEmail("test@mail.com")
                .customerName("Test")
                .items(List.of(fromString(uuid)))
                .paymentProvider(SWEDBANK)
                .senderIban(SENDER_IBAN)
                .amountToPay(new BigDecimal("20.99"))
                .build();
    }
}
