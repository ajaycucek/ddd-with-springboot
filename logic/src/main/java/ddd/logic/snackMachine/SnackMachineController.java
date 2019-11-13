package ddd.logic.snackMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import static ddd.logic.sharedKernel.Money.Cent;
import static ddd.logic.sharedKernel.Money.TenCent;
import static ddd.logic.sharedKernel.Money.Quarter;
import static ddd.logic.sharedKernel.Money.Dollar;
import static ddd.logic.sharedKernel.Money.FiveDollar;
import static ddd.logic.sharedKernel.Money.TwentyDollar;
import java.util.ArrayList;
import java.util.List;
import ddd.logic.snackMachine.SnackMachine;
import ddd.logic.snackMachine.SnackMachineRepository;

@Controller
@RequestMapping(path = "/snackmachines")
public class SnackMachineController {
	@Autowired
	SnackMachineRepository snackMachineRepository;

	@GetMapping()
	@ResponseBody
	public List<SnackMachineDto> getSnackMachines() {
        List<SnackMachineDto> list = new ArrayList<>();
        snackMachineRepository.findAll().forEach(list::add);
        return list;
	}
	
	@GetMapping("/{id}")
	@ResponseBody
	public SnackMachineDto getSnackMachine(@PathVariable("id") long id) {
        return snackMachineRepository.findById(id).orElse(null);
	}

	@PutMapping("/{id}/{slotNumber}")
	public void buySnack(@PathVariable("id") long id, @PathVariable("slotNumber") int slotNumber) {
		SnackMachineDto snackMachineDto = snackMachineRepository.findById(id).orElse(null);
		SnackMachine snackMachine = snackMachineDto.convertToSnackMachine();
		
		snackMachine.buySnack(slotNumber); 
		snackMachineRepository.save(snackMachine.convertToSnackMachineDto());
    }
	
	@PutMapping("/{id}/moneyInTransaction/{coinOrNote}")
	public void insertCoinOrNote(@PathVariable("id") long id, @PathVariable("coinOrNote") String coinOrNote) {
		SnackMachineDto snackMachineDto = snackMachineRepository.findById(id).orElse(null);
		SnackMachine snackMachine = snackMachineDto.convertToSnackMachine();
		
		if(coinOrNote.equalsIgnoreCase("Cent")) snackMachine.insertMoney(Cent);
		else if(coinOrNote.equalsIgnoreCase("TenCent")) snackMachine.insertMoney(TenCent);
		else if(coinOrNote.equalsIgnoreCase("Quarter")) snackMachine.insertMoney(Quarter);
		else if(coinOrNote.equalsIgnoreCase("Dollar")) snackMachine.insertMoney(Dollar);
		else if(coinOrNote.equalsIgnoreCase("FiveDollar")) snackMachine.insertMoney(FiveDollar);
		else if(coinOrNote.equalsIgnoreCase("TwentyDollar")) snackMachine.insertMoney(TwentyDollar);
		
		snackMachineRepository.save(snackMachine.convertToSnackMachineDto());
	}

	@PutMapping("/{id}/moneyInTransaction")
	public void returnMoney(@PathVariable("id") long id) {
		SnackMachineDto snackMachineDto = snackMachineRepository.findById(id).orElse(null);
		SnackMachine snackMachine = snackMachineDto.convertToSnackMachine();
		
		snackMachine.returnMoney(); 
		snackMachineRepository.save(snackMachine.convertToSnackMachineDto());
    }
	
	//not in use just to add dummy snackmachine
	@PostMapping("/{id}/dummy")
	public void addSnackMachine() {
		SnackMachineDto snackMachineDto = new SnackMachineDto();
		snackMachineDto.setMoneyInTransaction(0);
		
		List<SlotDto> slotDtoList = new ArrayList<>();
		
		SlotDto slotDto = new SlotDto();
		slotDto.setPosition(1);
		slotDto.setPrice(40);
		slotDto.setQuantity(100);
		slotDto.setSnackDto(Snack.Chocolate.convertToSnackDto());
		slotDtoList.add(slotDto);
		
		slotDto = new SlotDto();
		slotDto.setPosition(2);
		slotDto.setPrice(20);
		slotDto.setQuantity(100);
		slotDto.setSnackDto(Snack.Soda.convertToSnackDto());
		slotDtoList.add(slotDto);
		
		slotDto = new SlotDto();
		slotDto.setPosition(3);
		slotDto.setPrice(10);
		slotDto.setQuantity(100);
		slotDto.setSnackDto(Snack.Gum.convertToSnackDto());
		slotDtoList.add(slotDto);
		
		snackMachineDto.setSlotDtoList(slotDtoList);
		
		snackMachineDto.setOneCentCount(0);
		snackMachineDto.setTenCentCount(0);
		snackMachineDto.setQuarterCount(0);
		snackMachineDto.setOneDollarCount(0);
		snackMachineDto.setFiveDollarCount(0);
		snackMachineDto.setTwentyDollarCount(0);
		
		snackMachineRepository.save(snackMachineDto);
    }
}