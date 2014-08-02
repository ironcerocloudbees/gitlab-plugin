package com.dabsquared.gitlabjenkins;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.gitlab.api.GitlabAPI;

public class GitLab {
  private static final Logger LOGGER = Logger.getLogger(GitLab.class.getName());
  private GitlabAPI api;

  public GitlabAPI get() throws IOException {
    if (api == null) {
      connect();
    }

    return api;
  }
  
  public static boolean checkConnection (String token, String url, boolean ignoreCertificateErrors) {
	  try {
		  GitlabAPI testApi = GitlabAPI.connect(url, token);
		  testApi.ignoreCertificateErrors(ignoreCertificateErrors);
		  testApi.getProjects();
		  return true;
	  } catch (IOException ex) {
		  // TODO improve error handling
		  LOGGER.log(Level.WARNING, ex.getMessage());
		  return false;
	  }
  }

  private void connect() throws IOException {
    String token = GitLabPushTrigger.getDesc().getGitlabApiToken();
    String url = GitLabPushTrigger.getDesc().getGitlabHostUrl();
    LOGGER.log(Level.FINE, "Connecting to Gitlab server ({0})", url);
    api = GitlabAPI.connect(url, token);
  }
}