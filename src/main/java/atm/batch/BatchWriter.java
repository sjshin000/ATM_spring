package atm.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by sjshin on 2016-04-12.
 */
@Component
@Scope("step")
public class BatchWriter implements ItemWriter {
	@Override public void write(List list) throws Exception {
		System.out.println("================write ");
	}
}
