/*******************************************************************************
 * The MIT License
 * 
 * Copyright (C) 2021, CloudBees, Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package io.jenkins.plugins.bitbucketpushandpullrequest.receiver;

import static io.jenkins.plugins.bitbucketpushandpullrequest.common.BitBucketPPRConst.HOOK_URL;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import hudson.Extension;
import hudson.security.csrf.CrumbExclusion;
import io.jenkins.plugins.bitbucketpushandpullrequest.config.BitBucketPPRPluginConfig;

/**
 * Checks that the requested route matches the plugin's exclusion rule for CSRF protection filter.
 *
 * @author cdelmonte
 *
 */
@Extension
public class BitBucketPPRCrumbExclusion extends CrumbExclusion {

  private static final BitBucketPPRPluginConfig globalConfig =
      BitBucketPPRPluginConfig.getInstance();

  public String getHookPath() {
    return globalConfig.isHookUrlSet() ? globalConfig.getHookUrl() : HOOK_URL;
  }

  @Override
  public boolean process(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws IOException, ServletException {

    String path = request.getPathInfo();

    if (StringUtils.isNotBlank(path) && path.equals("/" + getHookPath() + "/")) {
      chain.doFilter(request, response);

      return true;
    }

    return false;
  }
}

