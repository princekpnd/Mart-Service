package com.shop.shopservice.controller;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shop.shopservice.Idao.ILookUp;
import com.shop.shopservice.Idao.ILookUpType;
import com.shop.shopservice.constants.ServiceConstants;
import com.shop.shopservice.entity.LookUp;
import com.shop.shopservice.entity.Slot;
import com.shop.shopservice.entity.SlotTime;
import com.shop.shopservice.service.IDeliveryService;
import com.shop.shopservice.service.ISlotTimeService;

@RestController
@RequestMapping("/api/slottime")
public class SlotTimeController {

	private final Logger log = LoggerFactory.getLogger(SlotTimeController.class);

	@Autowired
	private ISlotTimeService slotTimeService;

	@Autowired
	private ILookUp lookupService;

	@Autowired
	private ILookUpType lookUpType;

	@GetMapping("getall")
	public ResponseEntity<List<SlotTime>> getAll() {
		List<SlotTime> listSlot = slotTimeService.getAll();
		return new ResponseEntity<List<SlotTime>>(listSlot, HttpStatus.OK);
	}
	
	@GetMapping("getbytime/{slotTime}")
	public ResponseEntity<List<SlotTime>> getBySlotTime(@PathVariable("slotTime") int slotTime){
		List<SlotTime> slotList = slotTimeService.getBySlotTime(slotTime);
		return new ResponseEntity<List<SlotTime>>(HttpStatus.OK);
	}

	@SuppressWarnings({})
	@PostMapping("/create")
	ResponseEntity<Map<String, String>> createBank(@Valid @RequestBody Map<String, String> json,
			HttpServletRequest request) throws URISyntaxException {
		log.info("Request to create user: {}", json.get(ServiceConstants.SLOT_TIME));
		Map<String, String> response = new HashMap<String, String>();

		SlotTime slotTime = new SlotTime();
		slotTime.setActive(Boolean.TRUE);
		slotTime.setCreatedOn(new Date());
		slotTime.setDeleted(Boolean.FALSE);
		slotTime.setSlotCount(Integer.parseInt(json.get(ServiceConstants.SLOT_COUNT)));
//		slotTime.setSlotDate(new Date());
		slotTime.setSlotTime(Integer.parseInt(json.get(ServiceConstants.SLOT_TIME)));
		slotTime.setTotalCount(1);

		boolean create = slotTimeService.createSlotTime(slotTime);
		if (create) {
			response.put("status", Boolean.TRUE.toString());
			response.put("description", "Slot time created");
		} else {
			response.put("status", Boolean.FALSE.toString());
			response.put("description", "Slot time not created");
		}

		return ResponseEntity.ok().body(response);
	}

	@GetMapping("getall/{date}")
	public ResponseEntity<?> getAll(@PathVariable("date") String date) {
		LocalDate date1 = LocalDate.parse(date);
		LocalDateTime time = LocalDateTime.now();
		LocalDate today = LocalDate.now();
		List<SlotTime> listSlot = slotTimeService.getAll();
		List<SlotTime> listSlot1 = new ArrayList<SlotTime>();
		SlotTime slt = new SlotTime();

		for (SlotTime slotTime : listSlot) {
			LocalDate date2 = slotTime.getSlotDate();
			int date3 = date2.compareTo(date1);
			if (date3 == 0 && slotTime.getSlotTime() == 1) {
				slt = slotTime;
				listSlot1.add(slotTime);
			}
		}

		if (listSlot1.size() > 0) {
			slt.setSlotCount(slt.getSlotCount() + 1);
			slotTimeService.updateSlotTime(slt);
		} else {
			SlotTime slotTime = new SlotTime();
			slotTime.setActive(Boolean.TRUE);
			slotTime.setCreatedOn(new Date());
			slotTime.setDeleted(Boolean.FALSE);
			slotTime.setSlotCount(1);
			slotTime.setSlotDate(date1);
			slotTime.setSlotTime(1);
			slotTime.setTotalCount(10);

			boolean create = slotTimeService.createSlotTime(slotTime);
		}

		int res = date1.compareTo(today);

		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("allslot/{date}")
	public ResponseEntity<List<LookUp>> getAllSlot(@PathVariable("date") String date) {
		LocalDate date1 = LocalDate.parse(date);
		LocalDate today = LocalDate.now();
		int now = LocalTime.now().getHour();
		int diffToday = date1.compareTo(today);

		List<SlotTime> listSlot = slotTimeService.getAll();
		List<SlotTime> listSlot1 = new ArrayList<SlotTime>();
		List<LookUp> lookupList = lookupService
				.findLookUpByLookUpType(lookUpType.findLookUpTypeByName("SLOT_TYPE").getLookUpTypeId());
		SlotTime slt = new SlotTime();

		if (diffToday == 0) {
			for (int i = 0; i < lookupList.size(); i++) {
				LookUp lookup = lookupList.get(i);
				if (now >= 9 && now < 18) {
					if (now >= Integer.parseInt(lookup.getLookUpName())) {
						lookupList.get(i).setActive(false);
					} else {
						for (SlotTime slotTime : listSlot) {
							LocalDate date2 = slotTime.getSlotDate();
							int date3 = date2.compareTo(date1);
							if ((date3 == 0 && slotTime.getSlotTime()  == lookup.getLookUpId())) {
								if (slotTime.getSlotCount() >= slotTime.getTotalCount()) {
									lookupList.get(i).setActive(false);
								}
							}
						}
					}
				} else if (now < 9) {
					if (Integer.parseInt(lookupList.get(i).getLookUpName()) == 24) {
						lookupList.get(i).setActive(false);
					} else {
						for (SlotTime slotTime : listSlot) {
							LocalDate date2 = slotTime.getSlotDate();
							int date3 = date2.compareTo(date1);
							if ((date3 == 0 && slotTime.getSlotTime() == lookup.getLookUpId())) {
								if (slotTime.getSlotCount() >= slotTime.getTotalCount()) {
									lookupList.get(i).setActive(false);
								}
							}
						}
					}
				} else if (now >= 18) {
					lookupList.get(i).setActive(false);
				}
			}
		} else if (diffToday > 0) {
			for (int i = 0; i < lookupList.size(); i++) {
				LookUp lookup = lookupList.get(i);
				for (SlotTime slotTime : listSlot) {
					LocalDate date2 = slotTime.getSlotDate();
					int date3 = date2.compareTo(date1);
					if ((date3 == 0 && slotTime.getSlotTime() == lookup.getLookUpId())
							|| Integer.parseInt(lookup.getLookUpName()) == 24) {
						if (slotTime.getSlotCount() >= slotTime.getTotalCount()) {
							lookupList.get(i).setActive(false);
						}
						slt = slotTime;
						listSlot1.add(slotTime);
					}
				}
			}
		} else {
			for (int i = 0; i < lookupList.size(); i++) {
				lookupList.get(i).setActive(false);
			}
		}

		return new ResponseEntity<List<LookUp>>(lookupList, HttpStatus.OK);
	}
}
