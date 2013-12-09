package it.cnr.isti.hpc.twitter.streaming;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;


public class TwitterDownloadCLI extends AbstractCommandLineInterface{
	
	public static final String[] params = new String[] {INPUT};
	
	public TwitterDownloadCLI(String[] args) {
		super(args, params,"java -cp $jar it.cnr.isti.hpc.twitter.streaming.TwitterDownloadCLI"
				+ " -input fileinput - the name of the directory where we create the files ");
		}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterDownloadCLI cli = new TwitterDownloadCLI(args);
		String folder = cli.getInput();
		TwitterDownload td = new TwitterDownload(folder);
		td.dumpSampleStream();
	}

}
