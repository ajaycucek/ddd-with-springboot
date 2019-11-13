package ddd.logic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ddd.logic.atm.AtmDto;
import ddd.logic.atm.AtmRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AtmRepositoryTests {
	@Autowired
	private AtmRepository atmRepository;
	@Test
	public void save() {
		AtmDto atmDto = new AtmDto();
		atmDto.setId(0);
		atmDto.setFiveDollarCount(5);
		atmDto.setMoneyCharged(2.5f);
		atmDto.setOneCentCount(100);
		atmDto.setOneDollarCount(1);
		atmDto.setQuarterCount(25);
		atmDto.setTenCentCount(10);
		atmDto.setTwentyDollarCount(20);
		atmRepository.save(atmDto);
	}

}
