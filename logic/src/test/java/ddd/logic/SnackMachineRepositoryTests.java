package ddd.logic;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ddd.logic.snackMachine.SlotDto;
import ddd.logic.snackMachine.Snack;
import ddd.logic.snackMachine.SnackMachineDto;
import ddd.logic.snackMachine.SnackMachineRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnackMachineRepositoryTests {
	@Autowired
	private SnackMachineRepository snackMachineRepository;
	@Test
	public void save() {
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
