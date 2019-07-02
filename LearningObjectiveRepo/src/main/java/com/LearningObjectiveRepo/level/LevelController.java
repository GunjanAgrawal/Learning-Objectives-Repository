package com.LearningObjectiveRepo.level;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.LearningObjectiveRepo.ExceptionHandling.ResourceNotFoundException;
import com.LearningObjectiveRepo.UserAccounts.Message;
import com.LearningObjectiveRepo.taxonomy.Taxonomy;

@RestController
@CrossOrigin(origins="*",allowedHeaders="*")
@RequestMapping(value = "/api/levels")
public class LevelController {

	@Autowired
	private LevelService levelService;

	/*
	 * Internal class to accept levelName and it's description in a single object in
	 * string format
	 */
	public static class level {
		private String levelName;
		private String levelDescription;

		public String getLevelName() {
			return levelName;
		}

		public void setLevelName(String levelName) {
			this.levelName = levelName;
		}

		public String getLevelDescription() {
			return levelDescription;
		}

		public void setLevelDescription(String levelDescription) {
			this.levelDescription = levelDescription;
		}

		public level() {
			super();
		}

	}

	/*
	 * Individually create level
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer')")
	@RequestMapping(value = "", method = RequestMethod.POST)
	public Message createLevel(@RequestBody level lvl) {
		levelService.createLevel(lvl);
		Message m = new Message();
		m.setMessage("Level submitted successfully");
		return m;

	}

	/*
	 * Get all levels from the database
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer', 'Creator')")
	@RequestMapping(value = "", method = RequestMethod.GET)
	public List<Level> getLevel() {
		List<Level> l = levelService.getLevel();
		return l;

	}

	/*
	 * Create level corresponding to a given taxonomy id
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer')")
	@RequestMapping(value = "/taxonomies/{taxoid}", method = RequestMethod.POST)
	public Message createLevelByTaxonomy(@PathVariable("taxoid") String taxoId ,@RequestBody level lvl) {
		Long tId = Long.parseLong(taxoId);
		Taxonomy t = levelService.createLevelByTaxonomy(tId, lvl);
		if (t != null) {
			Message m = new Message();
			m.setMessage("Level submitted successfully");
			return m;

		} else
			throw new ResourceNotFoundException("Taxo id is not present");
	}

	/*
	 * Get list of levels corresponding to the given taxonomy
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer','Creator')")
	@RequestMapping(value = "taxonomies/{taxoid}", method = RequestMethod.GET)
	public List<Level> getLevelByTaxoId(@PathVariable("taxoid") String taxoId) {
		Long tId = Long.parseLong(taxoId);
		List<Level> lvl = levelService.getLevelByTaxoId(tId);
		if (lvl == null) {
			throw new ResourceNotFoundException("Taxo id not found - " + taxoId);
		} else if (lvl.isEmpty())
			throw new ResourceNotFoundException("List is Empty");
		return lvl;
	}

	/*
	 * Get level from the given level id
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer','Creator')")
	@RequestMapping(value = "/{levelid}", method = RequestMethod.GET)
	public Level getLevelByLevelId(@PathVariable("levelid") String levelId) {
		Long lId = Long.parseLong(levelId);
		Level lvl = levelService.getLevelByLevelId(lId);
		if (lvl == null) {
			throw new ResourceNotFoundException("Level id not found ");
		}
		return lvl;
	}

	/*
	 * Update level corresponding to the given level id
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer')")
	@RequestMapping(value = "/{levelid}", method = RequestMethod.PUT)
	public Message updateLevelByLevelId(@RequestBody Level lvl, @PathVariable("levelid") String levelId) {
		Long lId = Long.parseLong(levelId);
		Boolean b = levelService.updateLevelByLevelId(lvl, lId);
		if (b) {
			Message m = new Message();
			m.setMessage("Level updated successfully");
			return m;

		}
		throw new ResourceNotFoundException("Level having name -  " + lvl.getLevelName() + " already present.");
	}

	/*
	 * Update level corresponding to the given level id and taxonomy id
	 */
	@PreAuthorize("hasAnyRole('Admin','Reviewer')")
	@RequestMapping(value = "/{levelid}/taxonomies/{taxoid}", method = RequestMethod.PUT)
	public Message updateLevelByTaxoId(@PathVariable("levelid") String levelId, @PathVariable("taxoid") String taxoId) {
		Long lId = Long.parseLong(levelId);
		Long tId = Long.parseLong(taxoId);
		Boolean b = levelService.updateLevelBytaxoId(lId, tId);
		if (b) {
			Message m = new Message();
			m.setMessage("Level updated successfully");
			return m;

		}
		throw new ResourceNotFoundException("Udpdation not possible");
	}

	/*
	 * Delete level corresponding to the given level id
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@RequestMapping(value = "/{levelid}", method = RequestMethod.DELETE)
	public Message deleteLevelByLevelId(@PathVariable("levelid") String levelId) {
		Long lId = Long.parseLong(levelId);
		Boolean b = levelService.deleteLevelByLevelId(lId);
		if (b) {
			Message m = new Message();
			m.setMessage("Level deleted successfully");
			return m;

		}
		throw new ResourceNotFoundException("Level Id is not valid");
	}

	/*
	 * Delete relationship between level and taxonomy for the given taxonomy id
	 */
	@PreAuthorize("hasAnyRole('Admin')")
	@RequestMapping(value = "taxonomies/{taxoid}", method = RequestMethod.DELETE)
	public Message deleteLevelTaxoId(@PathVariable("taxoid") String taxoId) {
		Long tId = Long.parseLong(taxoId);
		Boolean b = levelService.deleteLevelByTaxoId(tId);
		if (b) {
			Message m = new Message();
			m.setMessage("Level deleted successfully");
			return m;

		}
		throw new ResourceNotFoundException("Taxonomy Id is not valid");
	}

}
