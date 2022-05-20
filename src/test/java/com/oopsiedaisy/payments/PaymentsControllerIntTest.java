package com.oopsiedaisy.payments;

import com.oopsiedaisy.common.IntegrationTest;
import com.oopsiedaisy.flowers.repository.FlowerRepository;
import com.oopsiedaisy.payments.controller.resource.ItemsToBuyResource;
import com.oopsiedaisy.payments.repository.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.util.List;

import static com.oopsiedaisy.payments.controller.resource.PaymentStatus.COMPLETED;
import static com.oopsiedaisy.payments.controller.resource.PaymentStatus.FAILED;
import static com.oopsiedaisy.payments.domain.PaymentProvider.SWEDBANK;
import static java.util.UUID.fromString;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql({"/testdata/flowers/add-flowers.sql"})
class PaymentsControllerIntTest extends IntegrationTest {

    private static final String PAYMENTS_COMPLETE_URL = "/payments/complete";
    private static final String SENDER_IBAN = "LT123456789";
    private static final String NON_EXISTING_ITEM_UUID = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3466";
    private static final String EXISTING_ITEM_UUID = "f8c3de3d-1fea-4d7c-a8b0-29f63c4c3454";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private FlowerRepository flowerRepository;

    @Test
    void shouldNotSellItemsWhenTheyDoNotExist() throws Exception {
        mockMvc.perform(post(PAYMENTS_COMPLETE_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildPaymentBody(NON_EXISTING_ITEM_UUID))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Item(s) you are trying to buy do not exist")));

        assertThat(paymentRepository.findBySenderIban(SENDER_IBAN).getStatus()).isEqualTo(FAILED);

    }

    @Test
    void shouldNotRemoveItemsFromDatabaseWhenPaymentFailed() throws Exception {
        mockMvc.perform(post(PAYMENTS_COMPLETE_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildPaymentBody(NON_EXISTING_ITEM_UUID))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("Item(s) you are trying to buy do not exist")));

        assertThat(flowerRepository.getAll().size()).isEqualTo(3);
    }

    @Test
    void shouldRemoveSoldItemsFromDatabase() throws Exception {
        mockMvc.perform(post(PAYMENTS_COMPLETE_URL)
                        .accept(APPLICATION_JSON)
                        .contentType(APPLICATION_JSON)
                        .content(toJson(buildPaymentBody(EXISTING_ITEM_UUID))))
                .andExpect(status().isOk());

        assertThat(flowerRepository.getByUuids(List.of(fromString(EXISTING_ITEM_UUID))).size()).isEqualTo(0);
    }

    @Test
    void shouldSellExistingItems() throws Exception {
        mockMvc.perform(post(PAYMENTS_COMPLETE_URL)
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .content(toJson(buildPaymentBody(EXISTING_ITEM_UUID))))
            .andExpect(status().isOk());

        assertThat(paymentRepository.findBySenderIban(SENDER_IBAN).getStatus()).isEqualTo(COMPLETED);
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
