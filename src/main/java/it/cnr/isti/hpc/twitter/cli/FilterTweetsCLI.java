package it.cnr.isti.hpc.twitter.cli;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.twitter.filter.Filter;
import it.cnr.isti.hpc.twitter.filter.TweetFilterer;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterTweetsCLI extends AbstractCommandLineInterface {

	private static final Logger logger = LoggerFactory
			.getLogger(FilterTweetsCLI.class);

	private static final String USAGE = "java -cp $jar "
			+ FilterTweetsCLI.class
			+ " -input tweets-folder -output filtered-tweets-folder -filter filtername";

	private static String[] params = new String[] { INPUT, OUTPUT, "filter" };

	FilterTweetsCLI(String[] args) {
		super(args, params, USAGE);
	}

	public static void main(String[] args) {

		FilterTweetsCLI cli = new FilterTweetsCLI(args);
		String filterName = cli.getParam("filter");
		TweetFilterer filterer = new TweetFilterer(cli.getOutput());
		if (!filterer.hasFilter(filterName)) {
			logger.error("cannot find filter {}, available filters: ",
					filterName);
			for (Filter f : filterer.getFilters()) {
				System.out.println(f.getName() + " - " + f.getDescription());
			}
			System.exit(-1);
		}
		File inputFolder = new File(cli.getInput());
		if (!inputFolder.exists()) {
			logger.error("cannot input dir {}", inputFolder.getAbsolutePath());
			System.exit(-1);
		}

		while (true) {
			logger.info("applying filter {} to {}", filterName, inputFolder);
			for (File f : inputFolder.listFiles()) {
				if (filterer.canProcess(f.getName())
						&& (!filterer.yetProcessed(filterName, f.getName()))) {
					try {
						logger.info("{} processing file {} ", filterName, f);
						filterer.filter(f, filterName);
					} catch (IOException e) {
						logger.error("filtering file {} ({})",
								f.getAbsolutePath(), e.toString());

					}
				}
			}
			logger.info("done, sleeping");
			try {
				Thread.sleep(1000 * 60 * 60 * 1); // sleep one hour
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
