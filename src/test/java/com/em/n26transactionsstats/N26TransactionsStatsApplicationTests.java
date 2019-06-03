package com.em.n26transactionsstats;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.em.n26transactionsstats.model.custom.QuantumViseStat;
import com.em.n26transactionsstats.model.domain.StatisticDTO;
import com.em.n26transactionsstats.model.domain.TransactionDTO;
import com.em.n26transactionsstats.util.ConfigReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class N26TransactionsStatsApplicationTests {
	@Autowired
	private MockMvc mvc;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	protected ConfigReader configurations;

	@Test
	public void transaction_created_sucessfully() {
		try {
			TransactionDTO dto1 = getTxs();
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto1))).andExpect(status().isOk());

			mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))

					.andExpect(content().json(getStatisticsAsString(dto1)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void transaction_is_too_old() {

		try {
			TransactionDTO dto1 = getTxs();
			Thread.sleep(configurations.getReportSizeInSeconds() * 1000);
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto1))).andExpect(status().isNoContent());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void multiple_tx_in_same_time_window() {

		try {
			TransactionDTO dto1 = getTxs();
			Thread.sleep(30);
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto1))).andExpect(status().isOk());

			TransactionDTO dto2 = getTxs();
			Thread.sleep(30);
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto2))).andExpect(status().isOk());

			mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().json(getStatisticsAsString(dto1, dto2)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	@DirtiesContext
	public void multiple_tx_in_different_time_window() {

		try {
			TransactionDTO dto1 = getTxs();
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto1))).andExpect(status().isOk());

			Thread.sleep(configurations.getReportSizeInSeconds() * 1000);

			TransactionDTO dto2 = getTxs(30);

			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto2))).andExpect(status().isOk());

			TransactionDTO dto3 = getTxs(40);

			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto3))).andExpect(status().isOk());

			mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().json(getStatisticsAsString(dto3, dto2)));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void only_tx_within_last_reporting_window() {

		try {
			TransactionDTO dto1 = getTxs();
			mvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(dto1))).andExpect(status().isOk());

			Thread.sleep((configurations.getReportSizeInSeconds() * 1000) + 1000);

			mvc.perform(get("/statistics").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
					.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
					.andExpect(content().json(getEmptyStatisticsAsString()));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public TransactionDTO getTxs() {

		return getTxs(10);

	}

	public TransactionDTO getTxs(double amount) {
		TransactionDTO dto1 = new TransactionDTO();
		dto1.setAmount(amount);
		dto1.setTimestamp(ZonedDateTime.now(ZoneOffset.UTC).toInstant().toEpochMilli());
		return dto1;

	}

	public String getStatisticsAsString(TransactionDTO... dto) throws JsonProcessingException {

		QuantumViseStat qunantumViseStat = new QuantumViseStat();
		qunantumViseStat.add(dto[0]);
		StatisticDTO statisticDTO = new StatisticDTO();

		if (dto.length > 1) {
			qunantumViseStat.add(dto[1]);
		}

		statisticDTO.add(qunantumViseStat);

		return objectMapper.writeValueAsString(statisticDTO);
	}

	public String getEmptyStatisticsAsString() throws JsonProcessingException {

		StatisticDTO statisticDTO = new StatisticDTO();

		return objectMapper.writeValueAsString(statisticDTO);
	}
}
