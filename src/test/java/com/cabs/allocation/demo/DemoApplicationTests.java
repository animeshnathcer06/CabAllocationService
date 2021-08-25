package com.cabs.allocation.demo;

import com.cabs.allocation.demo.controller.CabAllocationController;
import com.cabs.allocation.demo.controller.CabsController;
import com.cabs.allocation.demo.model.CabView;
import com.cabs.allocation.demo.model.Location;
import com.cabs.allocation.demo.model.SearchOptions;
import com.cabs.allocation.demo.types.CabType;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(classes = {CabAllocationApplication.class,
		CabAllocationController.class,
		CabsController.class})
@DirtiesContext
class DemoApplicationTests {

	@Autowired
	CabAllocationController cabAllocationController;

	@Autowired
	CabsController cabsController;

	@Test
	public void testOnlyNearByLocationsPicked() throws Exception {

		placeCabs();
		SearchOptions options = new SearchOptions();
		options.setLocation(new Location(19.160438, 72.994834));
		options.setCabTypes(Arrays.asList(CabType.NORMAL));

		List<CabView> cabViewList = cabAllocationController.findNearbyCabs(options);
		System.out.println(cabViewList);
		Assert.assertEquals(3, cabViewList.size());
	}

	/*
	One cab is very far (out of range) from the rest of the three cabs
	 */
	private void placeCabs() throws Exception {
		Location cab1 = new Location(19.160242, 72.998192);
		cabsController.placeCabOnMap(cab1);

		Location cab2 = new Location(19.160769, 72.989302);
		cabsController.placeCabOnMap(cab2);

		Location cab3 = new Location(19.148290,72.992720);
		cabsController.placeCabOnMap(cab3);

		Location cab4 = new Location(18.991904, 73.120042);
		cabsController.placeCabOnMap(cab4);
	}


}
