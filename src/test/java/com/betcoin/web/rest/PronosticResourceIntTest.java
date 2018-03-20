package com.betcoin.web.rest;

import com.betcoin.BetcoinApp;

import com.betcoin.domain.Pronostic;
import com.betcoin.repository.PronosticRepository;
import com.betcoin.service.PronosticService;
import com.betcoin.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.betcoin.domain.enumeration.Status;
/**
 * Test class for the PronosticResource REST controller.
 *
 * @see PronosticResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BetcoinApp.class)
public class PronosticResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_RESOURCEID = 1L;
    private static final Long UPDATED_RESOURCEID = 2L;

    private static final Integer DEFAULT_SCORE_1 = 1;
    private static final Integer UPDATED_SCORE_1 = 2;

    private static final Integer DEFAULT_SCORE_2 = 1;
    private static final Integer UPDATED_SCORE_2 = 2;

    private static final Long DEFAULT_WINNER = 1L;
    private static final Long UPDATED_WINNER = 2L;

    private static final Status DEFAULT_STATUS = Status.WON;
    private static final Status UPDATED_STATUS = Status.LOST;

    @Autowired
    private PronosticRepository pronosticRepository;

    @Autowired
    private PronosticService pronosticService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPronosticMockMvc;

    private Pronostic pronostic;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PronosticResource pronosticResource = new PronosticResource(pronosticService);
        this.restPronosticMockMvc = MockMvcBuilders.standaloneSetup(pronosticResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Pronostic createEntity(EntityManager em) {
        Pronostic pronostic = new Pronostic()
            .date(DEFAULT_DATE)
            .resourceid(DEFAULT_RESOURCEID)
            .score1(DEFAULT_SCORE_1)
            .score2(DEFAULT_SCORE_2)
            .winner(DEFAULT_WINNER)
            .status(DEFAULT_STATUS);
        return pronostic;
    }

    @Before
    public void initTest() {
        pronostic = createEntity(em);
    }

    @Test
    @Transactional
    public void createPronostic() throws Exception {
        int databaseSizeBeforeCreate = pronosticRepository.findAll().size();

        // Create the Pronostic
        restPronosticMockMvc.perform(post("/api/pronostics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronostic)))
            .andExpect(status().isCreated());

        // Validate the Pronostic in the database
        List<Pronostic> pronosticList = pronosticRepository.findAll();
        assertThat(pronosticList).hasSize(databaseSizeBeforeCreate + 1);
        Pronostic testPronostic = pronosticList.get(pronosticList.size() - 1);
        assertThat(testPronostic.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPronostic.getResourceid()).isEqualTo(DEFAULT_RESOURCEID);
        assertThat(testPronostic.getScore1()).isEqualTo(DEFAULT_SCORE_1);
        assertThat(testPronostic.getScore2()).isEqualTo(DEFAULT_SCORE_2);
        assertThat(testPronostic.getWinner()).isEqualTo(DEFAULT_WINNER);
        assertThat(testPronostic.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPronosticWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pronosticRepository.findAll().size();

        // Create the Pronostic with an existing ID
        pronostic.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPronosticMockMvc.perform(post("/api/pronostics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronostic)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Pronostic> pronosticList = pronosticRepository.findAll();
        assertThat(pronosticList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPronostics() throws Exception {
        // Initialize the database
        pronosticRepository.saveAndFlush(pronostic);

        // Get all the pronosticList
        restPronosticMockMvc.perform(get("/api/pronostics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pronostic.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].resourceid").value(hasItem(DEFAULT_RESOURCEID.intValue())))
            .andExpect(jsonPath("$.[*].score1").value(hasItem(DEFAULT_SCORE_1)))
            .andExpect(jsonPath("$.[*].score2").value(hasItem(DEFAULT_SCORE_2)))
            .andExpect(jsonPath("$.[*].winner").value(hasItem(DEFAULT_WINNER.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPronostic() throws Exception {
        // Initialize the database
        pronosticRepository.saveAndFlush(pronostic);

        // Get the pronostic
        restPronosticMockMvc.perform(get("/api/pronostics/{id}", pronostic.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pronostic.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.resourceid").value(DEFAULT_RESOURCEID.intValue()))
            .andExpect(jsonPath("$.score1").value(DEFAULT_SCORE_1))
            .andExpect(jsonPath("$.score2").value(DEFAULT_SCORE_2))
            .andExpect(jsonPath("$.winner").value(DEFAULT_WINNER.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPronostic() throws Exception {
        // Get the pronostic
        restPronosticMockMvc.perform(get("/api/pronostics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePronostic() throws Exception {
        // Initialize the database
        pronosticService.save(pronostic);

        int databaseSizeBeforeUpdate = pronosticRepository.findAll().size();

        // Update the pronostic
        Pronostic updatedPronostic = pronosticRepository.findOne(pronostic.getId());
        updatedPronostic
            .date(UPDATED_DATE)
            .resourceid(UPDATED_RESOURCEID)
            .score1(UPDATED_SCORE_1)
            .score2(UPDATED_SCORE_2)
            .winner(UPDATED_WINNER)
            .status(UPDATED_STATUS);

        restPronosticMockMvc.perform(put("/api/pronostics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPronostic)))
            .andExpect(status().isOk());

        // Validate the Pronostic in the database
        List<Pronostic> pronosticList = pronosticRepository.findAll();
        assertThat(pronosticList).hasSize(databaseSizeBeforeUpdate);
        Pronostic testPronostic = pronosticList.get(pronosticList.size() - 1);
        assertThat(testPronostic.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPronostic.getResourceid()).isEqualTo(UPDATED_RESOURCEID);
        assertThat(testPronostic.getScore1()).isEqualTo(UPDATED_SCORE_1);
        assertThat(testPronostic.getScore2()).isEqualTo(UPDATED_SCORE_2);
        assertThat(testPronostic.getWinner()).isEqualTo(UPDATED_WINNER);
        assertThat(testPronostic.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPronostic() throws Exception {
        int databaseSizeBeforeUpdate = pronosticRepository.findAll().size();

        // Create the Pronostic

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPronosticMockMvc.perform(put("/api/pronostics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pronostic)))
            .andExpect(status().isCreated());

        // Validate the Pronostic in the database
        List<Pronostic> pronosticList = pronosticRepository.findAll();
        assertThat(pronosticList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePronostic() throws Exception {
        // Initialize the database
        pronosticService.save(pronostic);

        int databaseSizeBeforeDelete = pronosticRepository.findAll().size();

        // Get the pronostic
        restPronosticMockMvc.perform(delete("/api/pronostics/{id}", pronostic.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pronostic> pronosticList = pronosticRepository.findAll();
        assertThat(pronosticList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pronostic.class);
        Pronostic pronostic1 = new Pronostic();
        pronostic1.setId(1L);
        Pronostic pronostic2 = new Pronostic();
        pronostic2.setId(pronostic1.getId());
        assertThat(pronostic1).isEqualTo(pronostic2);
        pronostic2.setId(2L);
        assertThat(pronostic1).isNotEqualTo(pronostic2);
        pronostic1.setId(null);
        assertThat(pronostic1).isNotEqualTo(pronostic2);
    }
}
