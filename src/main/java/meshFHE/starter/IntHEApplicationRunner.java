package meshFHE.starter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import meshFHE.funcLib.IntHE;

@Component
@ConfigurationProperties(prefix = "shaftstop")
public class IntHEApplicationRunner implements ApplicationRunner {
	
	private String keyDir = "";
    private String gmapDir;
    private String defaultGMapFile;
    private String defaultDecryptAccuracy = "0";
	
    public void run(ApplicationArguments var1) throws Exception{
    	IntHE.init(keyDir, gmapDir, defaultGMapFile, defaultDecryptAccuracy);
    }

	public String getKeyDir() {
		return keyDir;
	}

	public void setKeyDir(String keyDir) {
		this.keyDir = keyDir;
	}

	public String getGmapDir() {
		return gmapDir;
	}

	public void setGmapDir(String gmapDir) {
		this.gmapDir = gmapDir;
	}

	public String getDefaultGMapFile() {
		return defaultGMapFile;
	}

	public void setDefaultGMapFile(String defaultGMapFile) {
		this.defaultGMapFile = defaultGMapFile;
	}

	public String getDefaultDecryptAccuracy() {
		return defaultDecryptAccuracy;
	}

	public void setDefaultDecryptAccuracy(String defaultDecryptAccuracy) {
		this.defaultDecryptAccuracy = defaultDecryptAccuracy;
	}
}
