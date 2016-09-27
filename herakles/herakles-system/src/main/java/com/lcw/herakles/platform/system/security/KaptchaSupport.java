package com.lcw.herakles.platform.system.security;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import com.google.code.kaptcha.util.Config;
import com.lcw.herakles.platform.system.security.KaptchaSupport;


/**
* Class Name: KaptchaSupport
* Description: TODO
* @author chenwulou
*
*/
public class KaptchaSupport extends KaptchaExtend {

    private static final Logger LOG = LoggerFactory.getLogger(KaptchaSupport.class);

    private Producer kaptchaProducer = null;

    private String sessionKeyValue = null;

    private String sessionKeyDateValue = null;


    public KaptchaSupport() {
        // Switch off disk based caching.
        ImageIO.setUseCache(false);
        initialize(new Config(createDefaultConfigProps()));
    }

    public KaptchaSupport(Properties configProps) {
        this();
        Properties props = createDefaultConfigProps();
        props.putAll(configProps);
        Config config = new Config(props);
        initialize(config);
    }

    private void initialize(Config config) {
        this.kaptchaProducer = config.getProducerImpl();
        this.sessionKeyValue = config.getSessionKey();
        this.sessionKeyDateValue = config.getSessionDate();
    }

    private Properties createDefaultConfigProps() {
        Properties props = new Properties();
        props.put("kaptcha.border", "no");
        props.put("kaptcha.textproducer.font.color", "black");
        props.put("kaptcha.textproducer.char.space", "5");
        return props;
    }

    /**
     * map it to the /url/captcha.jpg
     * 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set standard HTTP/1.1 no-cache headers.
        resp.setHeader("Cache-Control", "no-store, no-cache");

        // return a jpeg
        resp.setContentType("image/jpeg");

        // create the text for the image
        String capText = this.kaptchaProducer.createText();

        // create the image with the text
        BufferedImage bi = this.kaptchaProducer.createImage(capText);

        ServletOutputStream out = resp.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);

        LOG.debug("captcha \"{}\" written to response", capText);

        // fixes issue #69: set the attributes after we write the image in case
        // the image writing fails.

        // store the text in the session
        req.getSession().setAttribute(this.sessionKeyValue, capText);

        // store the date in the session so that it can be compared
        // against to make sure someone hasn't taken too long to enter
        // their kaptcha
        req.getSession().setAttribute(this.sessionKeyDateValue, new Date());

        LOG.debug("captcha \"{}\" written to session, session id={}", capText, req.getSession().getId());
    }

    public boolean validateCaptcha(String captcha, HttpSession session) {
        String currentCaptcha = (String) session.getAttribute(sessionKeyValue);
        if (currentCaptcha == null) {
            LOG.warn("No captcha found in session - {}, session id = {}", captcha, session.getId());
        }
        boolean valid = captcha.equalsIgnoreCase(currentCaptcha);
        if (currentCaptcha != null && !valid) {
            LOG.debug("invalid captcha received: {}, expected: {}", captcha, currentCaptcha);
        }
        LOG.debug("captcha \"{}\" removed", currentCaptcha);
        session.removeAttribute(sessionKeyValue);
        return valid;
    }
}
