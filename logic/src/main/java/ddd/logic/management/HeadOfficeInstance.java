package ddd.logic.management;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class HeadOfficeInstance {
	@Autowired
	private HeadOfficeRepository headOfficeRepository;
	private static long HeadOfficeId = 1;
	private HeadOfficeDto headOfficeDto;

	public HeadOfficeDto getInstance() {
		return headOfficeRepository.findById(HeadOfficeId).orElse(null);
	}
}