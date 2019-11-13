package ddd.logic.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ddd.logic.atm.Atm;
import ddd.logic.atm.AtmDto;
import ddd.logic.atm.AtmRepository;
import ddd.logic.management.HeadOffice;
import ddd.logic.management.HeadOfficeDto;
import ddd.logic.management.HeadOfficeInstance;
import ddd.logic.management.HeadOfficeRepository;
import ddd.logic.snackMachine.SnackMachine;
import ddd.logic.snackMachine.SnackMachineDto;
import ddd.logic.snackMachine.SnackMachineRepository;

@Controller
@RequestMapping(path = "/headOffice")
public class HeadOfficeController {
	@Autowired
	private SnackMachineRepository snackMachineRepository;
	@Autowired
	private AtmRepository atmRepository;
	@Autowired
	private HeadOfficeRepository headOfficeRepository;
	@Autowired
	private HeadOfficeInstance headOfficeInstance;
	
	@GetMapping
	@ResponseBody
	public HeadOfficeDto getHeadOffice() {
		return headOfficeInstance.getInstance();
	}

	@PutMapping("/{atmId}/loadCash")
	public void loadCashToAtm(@PathVariable("atmId") long atmId) {
		AtmDto atmDto = atmRepository.findById(atmId).orElse(null);
	    HeadOfficeDto headOfficeDto = headOfficeInstance.getInstance();
	    Atm atm = atmDto.convertToAtm();
	    HeadOffice headOffice = headOfficeDto.convertToHeadOffice();
		headOffice.loadCashToAtm(atm);
		atmRepository.save(atm.convertToAtmDto());
		headOfficeRepository.save(headOffice.convertToHeadOfficeDto());
	}

	@PutMapping("/{snackMachineId}/unloadCash")
	public void unloadCash(@PathVariable("snackMachineId") long snackMachineId) {
		SnackMachineDto snackMachineDto = snackMachineRepository.findById(snackMachineId).orElse(null);
		if (snackMachineDto == null) return;
		HeadOfficeDto headOfficeDto = headOfficeInstance.getInstance();
		HeadOffice headOffice = headOfficeDto.convertToHeadOffice();
		SnackMachine snackMachine = snackMachineDto.convertToSnackMachine();
		headOffice.unloadCashFromSnackMachine(snackMachine);
		snackMachineRepository.save(snackMachine.convertToSnackMachineDto());
		headOfficeRepository.save(headOffice.convertToHeadOfficeDto());
	}
}