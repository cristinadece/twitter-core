package it.cnr.isti.hpc.twitter.cli;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.cnr.isti.hpc.cli.AbstractCommandLineInterface;
import it.cnr.isti.hpc.twitter.userdownload.UserDownload;


public class UserDownloadCLI extends AbstractCommandLineInterface{
	
	private static final Logger logger = LoggerFactory.getLogger(UserDownloadCLI.class);
	
	private static final String USAGE = "java -cp $jar "+ UserDownloadCLI.class + " -input username -output dumpfile.json";
	
	private static String[] params = new String[] { INPUT, OUTPUT };	

	public UserDownloadCLI(String[] args){
		super(args, params, USAGE);
	}
	
	
	public static void main(String[] args) {

		UserDownloadCLI udcli= new UserDownloadCLI(args);
		
		String input = udcli.getInput();
		String output = udcli.getOutput();
		
		logger.info("input = {} ",input);
		logger.info("input = {} ",output);
		
		UserDownload ud = new UserDownload();
		ud.dumpUser(input, output);
	}
		
		
}

