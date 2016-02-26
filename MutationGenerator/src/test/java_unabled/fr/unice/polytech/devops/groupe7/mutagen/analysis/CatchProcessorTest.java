package fr.unice.polytech.devops.groupe7.mutagen.analysis;

import fr.unice.polytech.devops.groupe7.mutagen.analysis.CatchProcessor;
import org.junit.Test;
import spoon.Launcher;
import spoon.processing.ProcessingManager;
import spoon.reflect.factory.Factory;
import spoon.support.QueueProcessingManager;

import static org.junit.Assert.assertEquals;

public class CatchProcessorTest {
	@Test
	public void testCatchProcessor() throws Exception {
		final String[] args = {
				"-i", "src/test/resources/src/",
				"-o", "target/spooned/"
		};

		final Launcher launcher = new Launcher();
		launcher.setArgs(args);
		launcher.run();

		final Factory factory = launcher.getFactory();
		final ProcessingManager processingManager = new QueueProcessingManager(factory);
		final CatchProcessor processor = new CatchProcessor();
		processingManager.addProcessor(processor);
		processingManager.process(factory.Class().getAll());

		assertEquals(2, processor.emptyCatchs.size());
	}
}
