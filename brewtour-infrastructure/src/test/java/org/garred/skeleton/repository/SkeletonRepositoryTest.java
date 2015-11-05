package org.garred.skeleton.repository;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.sql.DataSource;

import org.garred.skeleton.api.SkelAggregate;
import org.garred.skeleton.api.SkelId;
import org.garred.skeleton.repository.ObjectDoesNotExistException;
import org.garred.skeleton.repository.SkeletonRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/application-config.xml")
public class SkeletonRepositoryTest {

	private static final List<String> ITEM_LIST = asList("item 1", "item 2", "item 3");

	@Autowired
	private SkeletonRepository skeletonRepo;
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(DataSource datasource) {
		this.jdbcTemplate = new JdbcTemplate(datasource);
	}

	@Before
	public void setup() {
		this.jdbcTemplate.update("DELETE FROM brewtour.skeleton");
	}

	@Test
	public void testSave() {
		final SkelId id = new SkelId("someKey");
		this.skeletonRepo.save(id, new SkelAggregate(id, ITEM_LIST));
		final SkelAggregate result = this.skeletonRepo.get(id);
		assertEquals(ITEM_LIST, result.values);
	}

	@Test
	public void testExists() {
		final SkelId id = new SkelId("someKey");
		this.skeletonRepo.save(id, new SkelAggregate(id, ITEM_LIST));
		final boolean result = this.skeletonRepo.exists(id);
		assertTrue(result);
	}

	@Test
	public void testExists_false() {
		final SkelId id = new SkelId("someKey");
		final boolean result = this.skeletonRepo.exists(id);
		assertFalse(result);
	}

	@Test
	public void testUpdate() {
		final SkelId id = new SkelId("someKey");
		this.skeletonRepo.save(id, new SkelAggregate(id, ITEM_LIST));

		final List<String> updatedList = asList("updated item 1", "updated item 2");
		this.skeletonRepo.update(id, new SkelAggregate(id, updatedList));
		final SkelAggregate result = this.skeletonRepo.get(id);
		assertEquals(updatedList, result.values);
	}

	@Test(expected = ObjectDoesNotExistException.class)
	public void testUpdate_doesNotExist() {
		final SkelId id = new SkelId("someKey");
		final List<String> updatedList = asList("updated item 1", "updated item 2");
		this.skeletonRepo.update(id, new SkelAggregate(id, updatedList));
		final SkelAggregate result = this.skeletonRepo.get(id);
		assertEquals(updatedList, result.values);
	}

	@Test
	public void testDelete() {
		final SkelId id = new SkelId("someKey");
		this.skeletonRepo.save(id, new SkelAggregate(id, ITEM_LIST));
		this.skeletonRepo.delete(id);
		try {
			this.skeletonRepo.get(id);
			fail();
		} catch(@SuppressWarnings("unused") final DataIntegrityViolationException e) {}
	}

}
