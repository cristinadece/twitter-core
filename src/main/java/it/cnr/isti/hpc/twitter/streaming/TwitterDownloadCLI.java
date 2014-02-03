package it.cnr.isti.hpc.twitter.streaming;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;

public class TwitterDownloadCLI extends AbstractCommandLineInterface {

	public static final String[] params = new String[] {};

	public TwitterDownloadCLI(String[] args) {
		super(args, params,
				"java -cp $jar it.cnr.isti.hpc.twitter.streaming.TwitterDownloadCLI");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterDownloadCLI cli = new TwitterDownloadCLI(args);
		TwitterDownload td = new TwitterDownload();
		td.dumpSampleStream();
	}

}
